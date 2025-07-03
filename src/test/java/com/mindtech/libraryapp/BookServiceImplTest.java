package com.mindtech.libraryapp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.mindtech.libraryapp.dto.ResponseDto;
import com.mindtech.libraryapp.dto.request.BookCreateRequest;
import com.mindtech.libraryapp.dto.response.BookDto;
import com.mindtech.libraryapp.model.Author;
import com.mindtech.libraryapp.model.Book;
import com.mindtech.libraryapp.model.Publisher;
import com.mindtech.libraryapp.repository.AuthorRepository;
import com.mindtech.libraryapp.repository.BookRepository;
import com.mindtech.libraryapp.repository.PublisherRepository;
import com.mindtech.libraryapp.service.impl.AuthorServiceImpl;
import com.mindtech.libraryapp.service.impl.BookServiceImpl;

@ExtendWith(MockitoExtension.class)
public class BookServiceImplTest {
    @Mock
    private AuthorRepository authorRepository;
    @Mock
    private BookRepository bookRepository;
    @Mock
    private PublisherRepository publisherRepository;

    @InjectMocks
    private BookServiceImpl bookService;

    @Test
    public void testCreateBook() {
        BookCreateRequest request = new BookCreateRequest("Clean Code", 45.0, "9780132350884", LocalDate.of(2023, 1, 1), "Prentice Hall", "Robert C. Martin");

        Publisher publisher = Publisher.builder().publisherName("Prentice Hall").build();
        Author author = Author.builder().authorNameSurname("Robert C. Martin").books(new ArrayList<>()).build();
        Book book = Book.builder().title("Clean Code").price(45.0).ISBN13("9780132350884").publicationDate(LocalDate.of(2023, 1, 1)).publisher(publisher).author(author).build();
        //mock işlemleri(saving)
        when(publisherRepository.findByPublisherName("Prentice Hall")).thenReturn(Optional.of(publisher));
        when(authorRepository.findByAuthorNameSurname("Robert C. Martin")).thenReturn(Optional.of(author));
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        // Metot çağrılıyor burada
        ResponseDto<BookDto> result = bookService.createBook(request);

        // Doğrulama işlemleri
        assertNotNull(result);
        assertEquals("Clean Code", result.getData().getTitle());
        verify(bookRepository, times(1)).save(any(Book.class));
    }
}
