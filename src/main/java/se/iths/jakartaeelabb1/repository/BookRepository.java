package se.iths.jakartaeelabb1.repository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
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
}
