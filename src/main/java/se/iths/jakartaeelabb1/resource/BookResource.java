package se.iths.jakartaeelabb1.resource;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import se.iths.jakartaeelabb1.dto.BookDto;
import se.iths.jakartaeelabb1.dto.Books;
import se.iths.jakartaeelabb1.entity.Book;
import se.iths.jakartaeelabb1.service.BookService;

import java.net.URI;


@Path("books")
public class BookResource {
    private BookService bookService;

    public BookResource() {
    }

    @Inject
    public BookResource(BookService bookService) {
        this.bookService = bookService;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Books all() {
        return bookService.all();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public BookDto one(@PathParam("id") long id){
        return bookService.one(id);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    //@Produces(MediaType.APPLICATION_JSON)
    public Response create(@Valid BookDto bookDto){
        var b = bookService.add(bookDto);
        return Response.created(URI.create("http://localhost:8080/api/books/" + b.getId())).build();
    }

    @DELETE
    @Path("{id}")
    public Response delete(@PathParam("id") long id) {
        bookService.delete(id);
        return Response.ok().entity("Book with ID " + id + " deleted successfully.").build();
    }

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Book update(@PathParam("id") long id, BookDto bookDto)  {
        return bookService.update(id, bookDto);
    }
}