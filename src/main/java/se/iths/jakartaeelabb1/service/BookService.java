package se.iths.jakartaeelabb1.service;

import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;
import se.iths.jakartaeelabb1.dto.BookDto;
import se.iths.jakartaeelabb1.dto.Books;
import se.iths.jakartaeelabb1.entity.Book;
import se.iths.jakartaeelabb1.repository.BookRepository;


public class BookService {
    BookRepository bookRepository;

    public BookService() {
    }

    @Inject
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }


    public Books all() {
        return new Books(
                bookRepository.all().stream().map(BookDto::map).toList());
    }

    public BookDto one(long id){
        var book = bookRepository.findById(id);
        if( book == null)
            throw new NotFoundException("Invalid id " + id);
        return BookDto.map(book);
    }

    public Book add(BookDto bookDto) {
        var b = bookRepository.add(BookDto.map(bookDto));
        return b;
    }
}
