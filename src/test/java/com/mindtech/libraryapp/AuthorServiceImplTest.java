package com.mindtech.libraryapp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.mindtech.libraryapp.dto.ResponseDto;
import com.mindtech.libraryapp.dto.response.AuthorDto;
import com.mindtech.libraryapp.model.Author;
import com.mindtech.libraryapp.model.Book;
import com.mindtech.libraryapp.model.Publisher;
import com.mindtech.libraryapp.repository.AuthorRepository;
import com.mindtech.libraryapp.repository.BookRepository;
import com.mindtech.libraryapp.repository.PublisherRepository;
import com.mindtech.libraryapp.service.impl.AuthorServiceImpl;


@ExtendWith(MockitoExtension.class)
public class AuthorServiceImplTest {
    
    @Mock
    private AuthorRepository authorRepository;
    @InjectMocks
    private AuthorServiceImpl authorService;

    @Test
    public void testGetAllAuthors() {
        // Veri hazırlama
        Publisher publisher = Publisher.builder().publisherName("Test Yayinevi").build();
        Book book = Book.builder().title("Book 1").ISBN13("123").price(10.0).publicationDate(LocalDate.of(2024,1,1)).publisher(publisher).build();
        Author author = Author.builder().authorNameSurname("Ali Yazar").books(List.of(book)).build();
        book.setAuthor(author);

        when(authorRepository.findAll()).thenReturn(List.of(author));

        // Servisi çağırıyoruz
        ResponseDto<List<AuthorDto>> result = authorService.getAllAuthors();

        // Doğrulama
        assertEquals(1, result.getData().size());
        assertEquals("Ali Yazar", result.getData().get(0).getAuthorNameSurname());
        verify(authorRepository, times(1)).findAll();
    }
}
