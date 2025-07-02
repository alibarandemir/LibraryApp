package com.mindtech.libraryapp.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.mindtech.libraryapp.dto.ResponseDto;
import com.mindtech.libraryapp.dto.request.BookCreateRequest;
import com.mindtech.libraryapp.dto.response.BookDto;
import com.mindtech.libraryapp.exception.ErrorCodes;
import com.mindtech.libraryapp.model.Author;
import com.mindtech.libraryapp.model.Book;
import com.mindtech.libraryapp.model.Publisher;
import com.mindtech.libraryapp.repository.AuthorRepository;
import com.mindtech.libraryapp.repository.BookRepository;
import com.mindtech.libraryapp.repository.PublisherRepository;
import com.mindtech.libraryapp.service.BookService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final PublisherRepository publisherRepository;

    @Override
    @Transactional
    public ResponseDto<BookDto> createBook(BookCreateRequest request) {
        try {
            // 1. Yayıncıyı bul veya oluştur
            Publisher publisher = publisherRepository.findByPublisherName(request.getPublisherName())
                    .orElseGet(() -> {
                        Publisher newPublisher = Publisher.builder()
                                .publisherName(request.getPublisherName())
                                .build();
                        return publisherRepository.save(newPublisher);
                    });

            Author author = authorRepository.findByAuthorNameSurname(request.getAuthorNameSurname())
                    .orElseGet(() -> {
                        Author newAuthor = Author.builder()
                                .authorNameSurname(request.getAuthorNameSurname())
                                .build();
                        return authorRepository.save(newAuthor);
                    });

            // 2. Kitabı oluştur
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
            

            // 4. DTO'ya dönüştür
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

            // 2. İlişkili yayıncı ve yazar bilgilerini al
            Publisher publisher = book.getPublisher();
            Author author = book.getAuthor();

            // 3. DTO dönüşümü
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
            // 1. Güncellenecek kitabı bul
            Book book = bookRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Güncellenecek kitap bulunamadı"));

            // 2. Publisher'ı kontrol et → yoksa oluştur
            Publisher publisher = publisherRepository.findByPublisherName(book.getPublisher().getPublisherName())
                    .orElseGet(() -> {
                        Publisher newPublisher = Publisher.builder()
                                .publisherName(request.getPublisherName())
                                .build();
                        return publisherRepository.save(newPublisher);
                    });
            publisher.setPublisherName(request.getPublisherName());
            publisherRepository.save(publisher);

            // 3. Author'ı kontrol et → yoksa oluştur
            Author author = authorRepository.findByAuthorNameSurname(book.getAuthor().getAuthorNameSurname())
                    .orElseGet(() -> {
                        Author newAuthor = Author.builder()
                                .authorNameSurname(request.getAuthorNameSurname())
                                .build();
                        return authorRepository.save(newAuthor);
                    });
            author.setAuthorNameSurname(request.getAuthorNameSurname());
            authorRepository.save(author);

            // 4. Kitabın alanlarını güncelle
            book.setTitle(request.getTitle());
            book.setPrice(request.getPrice());
            book.setISBN13(request.getISBN13());
            book.setPublicationDate(request.getPublicationDate());
            book.setPublisher(publisher);
            book.setAuthor(author);

            bookRepository.save(book); // güncellenmiş kitabı kaydet

            // 5. DTO'ya dönüştür
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
            List<Book> books = bookRepository.findBooksAfter2023();

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
}
