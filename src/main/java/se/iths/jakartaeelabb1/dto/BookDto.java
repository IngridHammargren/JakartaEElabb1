package se.iths.jakartaeelabb1.dto;

import se.iths.jakartaeelabb1.entity.Book;

public record BookDto(String title, String author, Long id, int year) {

    public static BookDto map(Book bookEntity) {
        return new BookDto(bookEntity.getTitle(), bookEntity.getAuthor(), bookEntity.getId(), bookEntity.getYear());
    }

    public static Book map(BookDto bookDTO) {
        var book = new Book();
        book.setTitle(bookDTO.title());
        book.setAuthor(bookDTO.author());
        return book;
    }
}
