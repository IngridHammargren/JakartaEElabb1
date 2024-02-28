package se.iths.jakartaeelabb1.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import se.iths.jakartaeelabb1.entity.Book;

public record BookDto(
        @NotBlank(message = "Title cannot be blank")
        String title,
        @NotBlank(message = "Author cannot be blank")
        String author,
        Long id,
        @NotNull(message = "Year cannot be null")
        int year) {

    public static BookDto map(Book bookEntity) {
        return new BookDto(bookEntity.getTitle(), bookEntity.getAuthor(), bookEntity.getId(), bookEntity.getYear());
    }

    public static Book map(BookDto bookDTO) {
        var book = new Book();
        book.setTitle(bookDTO.title);
        book.setAuthor(bookDTO.author);
        book.setYear(bookDTO.year);
        return book;
    }
}
