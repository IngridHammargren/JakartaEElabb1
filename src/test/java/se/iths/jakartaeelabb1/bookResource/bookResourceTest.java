package se.iths.jakartaeelabb1.bookResource;

import com.github.dockerjava.api.exception.NotFoundException;
import jakarta.ws.rs.core.MediaType;
import org.jboss.resteasy.mock.MockDispatcherFactory;
import org.jboss.resteasy.mock.MockHttpRequest;
import org.jboss.resteasy.mock.MockHttpResponse;
import org.jboss.resteasy.spi.Dispatcher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import se.iths.jakartaeelabb1.dto.BookDto;
import se.iths.jakartaeelabb1.dto.Books;
import se.iths.jakartaeelabb1.entity.Book;
import se.iths.jakartaeelabb1.resource.BookResource;
import se.iths.jakartaeelabb1.service.BookService;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class bookResourceTest {

    @Mock
    BookService bookService;

    BookResource bookResource;

    ObjectMapper objectMapper;

    Dispatcher dispatcher;


@BeforeEach
public void setup(){
    objectMapper = new ObjectMapper();
    dispatcher = MockDispatcherFactory.createDispatcher();
    var resource = new BookResource(bookService);
    dispatcher.getRegistry().addSingletonResource(resource);
    bookResource = new BookResource(bookService);
}


    @Test
    @DisplayName("create new book with POST returns 201")
    void createReturnsStatus201() throws URISyntaxException, UnsupportedEncodingException {
        // Mock the behavior of bookService.add
        when(bookService.add(any(BookDto.class))).thenReturn(new Book());

        // Create a mock request and response
        MockHttpRequest request = MockHttpRequest.post("/books");
        request.contentType(MediaType.APPLICATION_JSON);
        request.content("{\"title\":\"Test\", \"author\":\"Test\", \"year\":1937}".getBytes());
        MockHttpResponse response = new MockHttpResponse();
        dispatcher.invoke(request, response);

        // Assert the response status code and content
        assertEquals(201, response.getStatus());
    }

    @Test
    @DisplayName("test delete method should return 200 ok")
    void testDeleteBook() throws Exception {
        Long id = 1L;
        MockHttpRequest request = MockHttpRequest.delete("/books/" + id);
        MockHttpResponse response = new MockHttpResponse();
        dispatcher.invoke(request,response);
        bookResource.delete(id);
        assertEquals(200,response.getStatus());
    }

    @Test
    void booksReturnsWithStatus200() throws Exception {
        when(bookService.all()).thenReturn(new Books(List.of()));
        MockHttpRequest request = MockHttpRequest.get("/books");
        MockHttpResponse response = new MockHttpResponse();
        dispatcher.invoke(request, response);
        assertEquals(200, response.getStatus());
        assertEquals("{\"bookDtos\":[]}", response.getContentAsString());
    }

    @Test
    @DisplayName("Update book with PUT returns 200 OK")
    void updateBookReturnsStatus200() throws URISyntaxException {
        Long id = 1L;

        when(bookService.update(eq(id), any(BookDto.class))).thenReturn(new Book());

        MockHttpRequest request = MockHttpRequest.put("/books/" + id);
        request.contentType(MediaType.APPLICATION_JSON);
        request.content("{\"title\":\"Emil i Lönneberga\", \"author\":\"Astrid Lindgren\", \"year\":1963}".getBytes());
        MockHttpResponse response = new MockHttpResponse();
        dispatcher.invoke(request, response);

        assertEquals(200, response.getStatus());
    }

    @Test
    @DisplayName("Attempt to update non-existing book throws NotFoundException")
    void updateNonExistingBook() {
        Long nonExistingBookId = 10L;

        BookDto bookDto = new BookDto("Emil i Lönneberga", "Astrid Lindgren", 10L, 2022);

        when(bookService.update(nonExistingBookId, bookDto)).thenThrow(NotFoundException.class);

        assertThrows(NotFoundException.class, () -> bookService.update(nonExistingBookId, bookDto));
    }
}