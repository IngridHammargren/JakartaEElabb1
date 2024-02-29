package se.iths.jakartaeelabb1.bookResource;


import jakarta.ws.rs.core.MediaType;
import net.bytebuddy.agent.builder.AgentBuilder;
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
import se.iths.jakartaeelabb1.entity.Book;
import se.iths.jakartaeelabb1.dto.BookDto;
import se.iths.jakartaeelabb1.resource.BookResource;
import se.iths.jakartaeelabb1.service.BookService;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class bookResource {

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
    @DisplayName("Test delete book returns 200 ok")
    void testDeleteBook() throws Exception {
        long bookId = 123L;
        MockHttpRequest request = MockHttpRequest.delete("/books/" + bookId);
        MockHttpResponse response = new MockHttpResponse();
        dispatcher.invoke(request,response);
        bookResource.delete(bookId);
        assertEquals(200, response.getStatus());
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

}