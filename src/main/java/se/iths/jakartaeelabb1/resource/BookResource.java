package se.iths.jakartaeelabb1.resource;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;

@Path("/hello-world")
public class BookResource {
    @GET
    @Produces("text/plain")
    public String hello() {
        return "Hello, World!";
    }


}