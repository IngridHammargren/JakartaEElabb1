package se.iths.jakartaeelabb1.dtoTest;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;
import se.iths.jakartaeelabb1.dto.BookDto;
import static org.junit.jupiter.api.Assertions.*;

class BookDtoTest {

    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    void testValidBookDto() {
        BookDto book = new BookDto("The Hobbit", "J.R.R Tolkien", 123L, 1937);

        var violations = validator.validate(book);

        assertEquals(0, violations.size());
    }

    @Test
    void testInvalidBookDto() {
        BookDto book = new BookDto("Mythos", "Stephen Fry", 124L, 350);

        var violations = validator.validate(book);

        assertEquals(1, violations.size());
        assertEquals("Year has to be set to 860 or higher", violations.iterator().next().getMessage());
    }
}