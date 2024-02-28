package se.iths.jakartaeelabb1.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;
import se.iths.jakartaeelabb1.dto.BookDto;
import se.iths.jakartaeelabb1.dto.Books;
import se.iths.jakartaeelabb1.entity.Book;
import se.iths.jakartaeelabb1.repository.BookRepository;

import java.util.logging.Logger;

@Dependent
public class BookService {
    private static final Logger logger = Logger.getLogger(BookService.class.getName());
    BookRepository bookRepository;

    public BookService() {
    }

    @Inject
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }


    public Books all() {
        logger.info("Retrieving all books");
        return new Books(
                bookRepository.all().stream().map(BookDto::map).toList());
    }

    public BookDto one(long id){
        logger.info("Retrieving book with ID: " + id);
        var book = bookRepository.findById(id);
        if( book == null)
            throw new NotFoundException("Invalid id " + id);
        return BookDto.map(book);
    }

    public Book add(BookDto bookDto) {
        logger.info("Adding a new book");
        var b = bookRepository.add(BookDto.map(bookDto));
        return b;
    }
    public void delete(long id) {
        bookRepository.deleteById(id);
    }
}
