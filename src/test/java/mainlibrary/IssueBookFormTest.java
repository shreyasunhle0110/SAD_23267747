package mainlibrary;

import org.junit.jupiter.api.*;
import static org.assertj.core.api.Assertions.*;

class IssueBookFormTest {

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "1; DROP TABLE Users;--"})
    void testValidateInput_InvalidInputs(String input) {
        assertThatThrownBy(() -> IssueBookForm.validateInput(input))
            .isInstanceOf(ValidationException.class)
            .hasMessageContaining("Invalid characters");
    }

    @Test
    void testValidateInput_ValidInput() {
        assertThatCode(() -> IssueBookForm.validateInput("ValidInput123"))
            .doesNotThrowAnyException();
    }

    @Test
    void testDateHandling_InvalidDate() {
        assertThatThrownBy(() -> IssueBookForm.validateDate("2023-02-30"))
            .isInstanceOf(ValidationException.class)
            .hasMessageContaining("Invalid date");
    }

    @Test
    void testDateHandling_ValidDate() {
        assertThatCode(() -> IssueBookForm.validateDate("2023-10-15"))
            .doesNotThrowAnyException();
    }
}
