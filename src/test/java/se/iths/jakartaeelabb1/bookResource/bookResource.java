package se.iths.jakartaeelabb1.bookResource;

import net.bytebuddy.agent.builder.AgentBuilder;
import org.jboss.resteasy.mock.MockDispatcherFactory;
import org.jboss.resteasy.spi.Dispatcher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import se.iths.jakartaeelabb1.resource.BookResource;
import se.iths.jakartaeelabb1.service.BookService;

@ExtendWith(MockitoExtension.class)
class bookResource {

    @Mock
    BookService bookService;

    ObjectMapper objectMapper;

    Dispatcher dispatcher;


@BeforeEach
public void setup(){
    objectMapper = new ObjectMapper();
    dispatcher = MockDispatcherFactory.createDispatcher();
    var resource = new BookResource(bookService);
    dispatcher.getRegistry().addSingletonResource(resource);
}
}