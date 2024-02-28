package se.iths.jakartaeelabb1.dto;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class BookDtoTest {

    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();


    @Test
    void testValidBookDto() {
        BookDto book = new BookDto("The Hobbit", "J.R.R Tolkien", 123L, 1937);

        var violations = validator.validate(book);

        assertEquals(0, violations.size());


    }
  
}