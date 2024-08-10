package MidtermExam.Group2.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PdfGenerationExceptionTest {
    @Test
    void testPdfGenerationExceptionMessage() {
        // Given
        String message = "PDF generation failed";

        // When
        PdfGenerationException exception = new PdfGenerationException(message);

        // Then
        assertEquals(message, exception.getMessage(), "Exception message should match");
    }

    @Test
    void testPdfGenerationExceptionMessageAndCause() {
        // Given
        String message = "PDF generation failed";
        Throwable cause = new RuntimeException("Underlying cause");

        // When
        PdfGenerationException exception = new PdfGenerationException(message, cause);

        // Then
        assertEquals(message, exception.getMessage(), "Exception message should match");
        assertEquals(cause, exception.getCause(), "Exception cause should match");
    }

    @Test
    void testPdfGenerationExceptionNoArgsConstructor() {
        // No no-args constructor exists, but we ensure it's not available
        assertThrows(NoSuchMethodException.class, () -> {
            PdfGenerationException.class.getConstructor();
        }, "No-args constructor should not exist");
    }
}
