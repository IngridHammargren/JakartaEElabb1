package se.iths.jakartaeelabb1.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;
import se.iths.jakartaeelabb1.dto.BookDto;
import se.iths.jakartaeelabb1.dto.Books;
import se.iths.jakartaeelabb1.entity.Book;
import se.iths.jakartaeelabb1.repository.BookRepository;

import java.util.List;

@ApplicationScoped
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
            throw new NotFoundException(id + " not found");
        return BookDto.map(book);
    }

    public Book add(BookDto bookDto) {
        List<Book> existingBooks = bookRepository.findByTitleAndAuthor(bookDto.title(),bookDto.author());
        if (!existingBooks.isEmpty())
            throw new IllegalArgumentException(bookDto.title() + " already exists");
        return bookRepository.add(BookDto.map(bookDto));
    }
    public void delete(long id) {
        var book = bookRepository.findById(id);
        if( book == null)
            throw new NotFoundException(id + " not found");
        else bookRepository.deleteById(id);
    }

    public Book update (long id, BookDto bookDto) {
        var b = bookRepository.update(id, bookDto);
        if (b == null)
            throw new NotFoundException(bookDto.title() + " does not exist");
        return b;
    }
}
