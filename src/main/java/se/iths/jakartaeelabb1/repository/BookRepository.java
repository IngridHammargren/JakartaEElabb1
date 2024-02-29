package se.iths.jakartaeelabb1.repository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import se.iths.jakartaeelabb1.dto.BookDto;
import se.iths.jakartaeelabb1.entity.Book;

import java.util.List;

@ApplicationScoped
public class BookRepository {
    @PersistenceContext(unitName = "mysql")
    EntityManager entityManager;

    public List<Book> all() {
        return entityManager
                .createQuery("select b from Book b", Book.class)
                .getResultList();
    }

    public List<Book> findByTitleAndAuthor(String title, String author) {
        return entityManager.createQuery(
                        "select b from Book b where b.title = :title and b.author = :author", Book.class)
                .setParameter("title", title)
                .setParameter("author", author)
                .getResultList();
    }

    @Transactional
    public Book add(Book book) {
        entityManager.persist(book);
        return book;
    }

    public Book findById(long id) {
        return entityManager.find(Book.class, id);
    }

    @Transactional
    public void deleteById(long id) {
        Book book = entityManager.find(Book.class, id);
        if (book != null) entityManager.remove(book);
    }


    @Transactional
    public Book update(long id, BookDto bookDto) {
        Book book = entityManager.find(Book.class, id);
        book.setTitle(bookDto.title());
        book.setAuthor(bookDto.author());
        book.setYear(bookDto.year());
        entityManager.flush();
        return book;

    }
}
