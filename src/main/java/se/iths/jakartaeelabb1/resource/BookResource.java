package se.iths.jakartaeelabb1.resource;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import se.iths.jakartaeelabb1.dto.BookDto;
import se.iths.jakartaeelabb1.dto.Books;
import se.iths.jakartaeelabb1.entity.Book;
import se.iths.jakartaeelabb1.repository.BookRepository;

import java.net.URI;
import java.time.LocalDateTime;

@Path("books")
public class BookResource {
    private BookRepository bookRepository;

    public BookResource() {
    }

    @Inject
    public BookResource(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Books all() {
        return new Books(
                bookRepository.all().stream().map(BookDto::map).toList());
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public BookDto one(@PathParam("id") long id){
        var book = bookRepository.findById(id);
        if( book == null)
            throw new NotFoundException("Invalid id " + id);
        return BookDto.map(book);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    //@Produces(MediaType.APPLICATION_JSON)
    public Response create(BookDto bookDto){
        //Save to database
        var p = bookRepository.add(BookDto.map(bookDto));

        return Response.created(
                        //Ask Jakarta application server for hostname and url path
                        URI.create("http://localhost:8080/api/books/" + p.getId()))
                .build();
    }

}