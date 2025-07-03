package com.mindtech.libraryapp.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;

import com.mindtech.libraryapp.client.GoogleBooksClient;
import com.mindtech.libraryapp.dto.ResponseDto;
import com.mindtech.libraryapp.dto.request.BookCreateRequest;
import com.mindtech.libraryapp.dto.response.BookDto;
import com.mindtech.libraryapp.dto.response.GoogleBookResponse;
import com.mindtech.libraryapp.dto.response.GoogleBookResponse.GoogleBookItem;
import com.mindtech.libraryapp.dto.response.GoogleBookResponse.VolumeInfo;
import com.mindtech.libraryapp.dto.response.GoogleBookResponse.IndustryIdentifier;
import com.mindtech.libraryapp.exception.ErrorCodes;
import com.mindtech.libraryapp.model.Author;
import com.mindtech.libraryapp.model.Book;
import com.mindtech.libraryapp.model.Publisher;
import com.mindtech.libraryapp.repository.AuthorRepository;
import com.mindtech.libraryapp.repository.BookRepository;
import com.mindtech.libraryapp.repository.PublisherRepository;
import com.mindtech.libraryapp.service.BookService;

import feign.FeignException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {

    // Injection
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final PublisherRepository publisherRepository;
    // Feign Client
    private final GoogleBooksClient googleBooksClient;

    @Override
    @Transactional // Birden fazla tabloda işlem olacağı için kullandım
    public ResponseDto<BookDto> createBook(BookCreateRequest request) {
        try {
            // Publisher bulunur yoksa oluşturulur
            Publisher publisher = publisherRepository.findByPublisherName(request.getPublisherName())
                    .orElseGet(() -> {
                        Publisher newPublisher = Publisher.builder()
                                .publisherName(request.getPublisherName())
                                .build();
                        return publisherRepository.save(newPublisher);
                    });
            // Author bulunur yoksa oluşturulur
            Author author = authorRepository.findByAuthorNameSurname(request.getAuthorNameSurname())
                    .orElseGet(() -> {
                        Author newAuthor = Author.builder()
                                .authorNameSurname(request.getAuthorNameSurname())
                                .build();
                        return authorRepository.save(newAuthor);
                    });

            Book book = Book.builder()
                    .title(request.getTitle())
                    .price(request.getPrice())
                    .ISBN13(request.getISBN13())
                    .publicationDate(request.getPublicationDate())
                    .publisher(publisher)
                    .author(author)
                    .build();
            book = bookRepository.save(book);

            // Yazara kitabı ekle
            author.getBooks().add(book);
            authorRepository.save(author);
            BookDto dto = BookDto.builder()
                    .title(book.getTitle())
                    .price(book.getPrice())
                    .ISBN13(book.getISBN13())
                    .publicationDate(book.getPublicationDate())
                    .publisherName(publisher.getPublisherName())
                    .authorNameSurname(author.getAuthorNameSurname())
                    .build();

            return ResponseDto.successResponse(dto, "Kitap başarıyla oluşturuldu");
        } catch (Exception e) {
            throw ErrorCodes.INTERNAL_SERVER_ERROR.exception(e.getMessage());
        }
    }

    @Override
    public ResponseDto<BookDto> getBookById(Long id) {
        try {
            Book book = bookRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Kitap bulunamadı"));

            Publisher publisher = book.getPublisher();
            Author author = book.getAuthor();

            BookDto dto = BookDto.builder()
                    .title(book.getTitle())
                    .price(book.getPrice())
                    .ISBN13(book.getISBN13())
                    .publicationDate(book.getPublicationDate())
                    .publisherName(publisher.getPublisherName())
                    .authorNameSurname(author.getAuthorNameSurname())
                    .build();

            return ResponseDto.successResponse(dto, "Kitap başarıyla getirildi");
        } catch (Exception e) {
            return ResponseDto.errorResponse("Kitap getirilirken bir hata oluştu: " + e.getMessage());
        }
    }

    @Override
    public ResponseDto<List<BookDto>> getAllBooks() {
        try {
            List<Book> books = bookRepository.findAll();
            // Stream ile MODEL-DTO dönüşümü
            List<BookDto> bookDtos = books.stream().map(book -> {
                Publisher publisher = book.getPublisher();
                Author author = book.getAuthor();
                return BookDto.builder()
                        .title(book.getTitle())
                        .price(book.getPrice())
                        .ISBN13(book.getISBN13())
                        .publicationDate(book.getPublicationDate())
                        .publisherName(publisher != null ? publisher.getPublisherName() : null)
                        .authorNameSurname(author != null ? author.getAuthorNameSurname() : null)
                        .build();
            }).toList();
            return ResponseDto.successResponse(bookDtos, "Tüm kitaplar başarıyla getirildi");
        } catch (Exception e) {
            return ResponseDto.errorResponse("Kitaplar listelenirken bir hata oluştu: " + e.getMessage());
        }
    }

    @Transactional
    @Override
    public ResponseDto<BookDto> updateBook(Long id, BookCreateRequest request) {
        try {
            Book book = bookRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Güncellenecek kitap bulunamadı"));

            // Publisher bulunur yoksa oluşturulur
            Publisher publisher = publisherRepository.findByPublisherName(book.getPublisher().getPublisherName())
                    .orElseGet(() -> {
                        Publisher newPublisher = Publisher.builder()
                                .publisherName(request.getPublisherName())
                                .build();
                        return publisherRepository.save(newPublisher);
                    });
            publisher.setPublisherName(request.getPublisherName());
            publisherRepository.save(publisher);
            Author author = authorRepository.findByAuthorNameSurname(book.getAuthor().getAuthorNameSurname())
                    .orElseGet(() -> {
                        Author newAuthor = Author.builder()
                                .authorNameSurname(request.getAuthorNameSurname())
                                .build();
                        return authorRepository.save(newAuthor);
                    });
            author.setAuthorNameSurname(request.getAuthorNameSurname());
            authorRepository.save(author);

            book.setTitle(request.getTitle());
            book.setPrice(request.getPrice());
            book.setISBN13(request.getISBN13());
            book.setPublicationDate(request.getPublicationDate());
            book.setPublisher(publisher);
            book.setAuthor(author);

            bookRepository.save(book);
            BookDto dto = BookDto.builder()
                    .title(book.getTitle())
                    .price(book.getPrice())
                    .ISBN13(book.getISBN13())
                    .publicationDate(book.getPublicationDate())
                    .publisherName(publisher.getPublisherName())
                    .authorNameSurname(author.getAuthorNameSurname())
                    .build();

            return ResponseDto.successResponse(dto, "Kitap başarıyla güncellendi");
        } catch (Exception e) {
            return ResponseDto.errorResponse("Kitap güncellenirken bir hata oluştu: " + e.getMessage());
        }
    }

    @Override
    public ResponseDto<Void> deleteBook(Long id) {
        try {
            // Kitap dbde var mı yok mu kontrolü
            Book book = bookRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Silinecek kitap bulunamadı"));
            bookRepository.delete(book);
            return ResponseDto.successResponse(null, "Kitap başarıyla silindi");
        } catch (Exception e) {
            return ResponseDto.errorResponse("Kitap silinirken bir hata oluştu: " + e.getMessage());
        }
    }

    @Override
    public ResponseDto<List<BookDto>> getBooksStartingWithA() {
        try {
            List<Book> books = bookRepository.findAll();

            // Stream yapısı ile filtreleme
            List<BookDto> bookDtos = books.stream()
                    .filter(book -> book.getTitle().startsWith("A"))
                    // Model-DTO dönüşümü
                    .map(book -> {
                        Publisher publisher = book.getPublisher();
                        Author author = book.getAuthor();

                        return BookDto.builder()
                                .title(book.getTitle())
                                .price(book.getPrice())
                                .ISBN13(book.getISBN13())
                                .publicationDate(book.getPublicationDate())
                                .publisherName(publisher != null ? publisher.getPublisherName() : null)
                                .authorNameSurname(author != null ? author.getAuthorNameSurname() : null)
                                .build();
                    })
                    .toList();

            return ResponseDto.successResponse(bookDtos, "A ile başlayan kitaplar başarıyla getirildi");
        } catch (Exception e) {
            return ResponseDto.errorResponse("Kitaplar listelenirken bir hata oluştu: " + e.getMessage());
        }
    }

    @Override
    public ResponseDto<List<BookDto>> getBooksAfter2023() {
        try {
            // Query yapısı çağrılıyor burada
            List<Book> books = bookRepository.findBooksAfter2023();
            // Mapleme ile DTO-Model dönüşümü yapılıyor
            List<BookDto> bookDtos = books.stream().map(book -> {
                Publisher publisher = book.getPublisher();
                Author author = book.getAuthor();

                return BookDto.builder()
                        .title(book.getTitle())
                        .price(book.getPrice())
                        .ISBN13(book.getISBN13())
                        .publicationDate(book.getPublicationDate())
                        .publisherName(publisher != null ? publisher.getPublisherName() : null)
                        .authorNameSurname(author != null ? author.getAuthorNameSurname() : null)
                        .build();
            }).toList();

            return ResponseDto.successResponse(bookDtos, "2023'ten sonraki kitaplar başarıyla getirildi");
        } catch (Exception e) {
            return ResponseDto.errorResponse("Kitaplar listelenirken bir hata oluştu: " + e.getMessage());
        }
    }

    @Override
    public ResponseDto<List<BookDto>> searchBooks(String query) {
        try {
            GoogleBookResponse response = googleBooksClient.searchBooks(query);
            
            //Boş liste döndürüyoruz veri yoksa 
            if (response.getItems() == null) {
                return ResponseDto.successResponse(List.of(), "No books found");
            }

            List<BookDto> bookDtos = response.getItems().stream().map(item -> {
                VolumeInfo info = item.getVolumeInfo();

                String isbn13 = info.getIndustryIdentifiers() != null
                        ? info.getIndustryIdentifiers().stream()
                                .filter(id -> "ISBN_13".equals(id.getType()))
                                .map(IndustryIdentifier::getIdentifier)
                                .findFirst()
                                .orElse(null)
                        : null;

                LocalDate publicationDate = null;
                //Yayın tarihi api cevabında sadece year geliyor ben ise LocalDate şeklinde tutuyorum
                //Bu yüzden Year-month-day şekline dönüştürüyorum
                try {
                    if (info.getPublishedDate() != null && info.getPublishedDate().length() >= 4) {
                        publicationDate = LocalDate.parse(info.getPublishedDate().substring(0, 4) + "-01-01");
                    }
                } catch (Exception e) {
                    publicationDate = null;
                }

                return BookDto.builder()
                        .title(info.getTitle())
                        .authorNameSurname(info.getAuthors() != null ? String.join(", ", info.getAuthors()) : "Unknown")
                        .publisherName(info.getPublisher())
                        .ISBN13(isbn13)
                        .publicationDate(publicationDate)
                        .price(1431.8) // Sabit değer
                        .build();
            }).collect(Collectors.toList());

            return ResponseDto.successResponse(bookDtos, "Books successfully retrieved");

        }catch (FeignException.NotFound e) {
            throw ErrorCodes.BOOK_NOT_FOUND.exception(e.getMessage());
        } 
        catch (Exception e) {
            throw ErrorCodes.INTERNAL_SERVER_ERROR.exception(e.getMessage());
        } 
    }
}
