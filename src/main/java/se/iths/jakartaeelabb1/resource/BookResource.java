package se.iths.jakartaeelabb1.resource;

import jakarta.inject.Inject;
import jakarta.json.bind.JsonbException;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import se.iths.jakartaeelabb1.dto.BookDto;
import se.iths.jakartaeelabb1.dto.Books;
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
    public Response create(@Valid BookDto bookDto) {
        try {
            // Your existing code for adding a book
            var b = bookService.add(bookDto);
            return Response.created(URI.create("http://localhost:8080/api/books/" + b.getId())).build();
        } catch (JsonbException e) {
            // Handle JSON deserialization error here
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Invalid JSON data. Please check your request body.")
                    .build();
        }
    }

    @DELETE
    @Path("{id}")
    public Response delete(@PathParam("id") long id) {
        bookService.delete(id);
        return Response.ok().entity("Book with ID " + id + " deleted successfully.").build();
    }
}