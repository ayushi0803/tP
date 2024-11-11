package fittrack.parser;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static fittrack.parser.ParserHelpers.parseDeadline;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import static fittrack.messages.Messages.DATE_OUT_OF_RANGE;
import static fittrack.messages.Messages.INVALID_DATETIME_MESSAGE;

public class ParserHelpersTest {

    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor)); // Redirects System.out
    }

    @Test
    void testValidDateTime() {
        // Test valid date and time input within bounds
        String input = "25/12/2023 15:30";
        LocalDateTime expected = LocalDateTime.of(2023, 12, 25, 15, 30);
        assertEquals(expected, parseDeadline(input));
    }

    @Test
    void testValidDateOnly() {
        // Test valid date input without time
        String input = "01/01/2025";
        LocalDateTime expected = LocalDate.of(2025, 1, 1).atStartOfDay();
        assertEquals(expected, parseDeadline(input));
    }


    @Test
    void testInvalidDateFormat() {
        String[] invalidInputs = {
            "2023-12-25",     // YYYY-MM-DD
            "2023.12.25",     // YYYY.MM.DD
            "2023/12/25",     // YYYY/MM/DD
            "25-12-2023",     // DD-MM-YYYY
            "25.12.2023"      // DD.MM.YYYY
        };

        for (String input : invalidInputs) {
            Exception exception = assertThrows(IllegalArgumentException.class, () -> parseDeadline(input));
            assertEquals(INVALID_DATETIME_MESSAGE, exception.getMessage());
        }
    }


    @Test
    void testInvalidDate() {
        String[] invalidInputs = {
            "32/12/2023",      // Day entry is invalid (too large)
            "00/12/2023",      // Day entry is invalid (Zero)
            "-30/12/2023",     // Day entry is invalid (negative)
            "30/13/2023",      // Month entry is invalid (too large)
            "30/00/2023",      // Month entry is invalid (Zero)
            "30/-12/2023",     // Month entry is invalid (negative)
            "30/12/0000",      // Year entry is invalid (Zero)
            "30/12/-2023"      // Year entry is invalid (negative)
        };

        for (String input : invalidInputs) {
            Exception exception = assertThrows(IllegalArgumentException.class, () -> parseDeadline(input));
            assertEquals(INVALID_DATETIME_MESSAGE, exception.getMessage());
        }
    }

    @Test
    void testInvalidDateTime() {
        String[] invalidInputs = {
            "30/12/2023 99:30",       // Hour entry is invalid (too large)
            "-30/12/2023 -12:30",     // Hour entry is invalid (negative)
            "30/13/2023 12:99",       // Minute entry is invalid (too large)
            "30/00/2023 12:-30",      // Minute entry is invalid (negative)
        };

        for (String input : invalidInputs) {
            Exception exception = assertThrows(IllegalArgumentException.class, () -> parseDeadline(input));
            assertEquals(INVALID_DATETIME_MESSAGE, exception.getMessage());
        }
    }


    @Test
    void testNonNumericDateTime() {
        // Test invalid input including non-numeric characters
        String input = "XX/XX/XXXX";
        Exception exception = assertThrows(IllegalArgumentException.class, () -> parseDeadline(input));
        assertEquals(INVALID_DATETIME_MESSAGE, exception.getMessage());

    }

    @Test
    void testDateOutOfRangeLow() {
        // Test date below the minimum bound (01/01/1900)
        String input = "31/12/1899";
        Exception exception = assertThrows(IllegalArgumentException.class, () -> parseDeadline(input));
        assertEquals(DATE_OUT_OF_RANGE, exception.getMessage());
    }

    @Test
    void testDateOutOfRangeHigh() {
        // Test date above the maximum bound (31/12/2100)
        String input = "01/01/2101";
        Exception exception = assertThrows(IllegalArgumentException.class, () -> parseDeadline(input));
        assertEquals(DATE_OUT_OF_RANGE, exception.getMessage());
    }

    @Test
    void testDateTimeOutOfRangeLow() {
        // Test date below the minimum bound (01/01/1900)
        String input = "31/12/1899 12:50";
        Exception exception = assertThrows(IllegalArgumentException.class, () -> parseDeadline(input));
        assertEquals(DATE_OUT_OF_RANGE, exception.getMessage());
    }

    @Test
    void testDateTimeOutOfRangeHigh() {
        // Test date above the maximum bound (31/12/2100)
        String input = "01/01/2101 12:50";
        Exception exception = assertThrows(IllegalArgumentException.class, () -> parseDeadline(input));
        assertEquals(DATE_OUT_OF_RANGE, exception.getMessage());
    }

    @Test
    void testInvalidTimeFormat() {
        // Test invalid time format
        String input = "25/12/2023 15:30:45";  // Incorrect format with seconds included
        Exception exception = assertThrows(IllegalArgumentException.class, () -> parseDeadline(input));
        assertEquals(INVALID_DATETIME_MESSAGE, exception.getMessage());
    }

    @Test
    void testEmptyInput() {
        // Test empty input string
        String input = "";
        Exception exception = assertThrows(IllegalArgumentException.class, () -> parseDeadline(input));
        assertEquals(INVALID_DATETIME_MESSAGE, exception.getMessage());
    }
}
