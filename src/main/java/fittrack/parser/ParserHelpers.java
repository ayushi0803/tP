package fittrack.parser;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import static fittrack.messages.Messages.DATE_OUT_OF_RANGE;
import static fittrack.messages.Messages.INVALID_DATETIME_MESSAGE;

public class ParserHelpers {

    /**
     * Parses user input indicating the deadline of an object.
     * Throws an exception if user-input String is inappropriate or ill-formatted.
     *
     * @param inputDeadline A string input by the user. Intended format is DD/MM/YYYY or DD/MM/YYYY HH:mm.
     * @return A {@code LocalDateTime} object indicating reminder deadline
     * @throws IllegalArgumentException Thrown if an incorrectly formatted deadline is provided.
     */
    static LocalDateTime parseDeadline(String inputDeadline) throws IllegalArgumentException {

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        // Define acceptable date range (e.g., between years 1900 and 2100)
        LocalDate minDate = LocalDate.of(1900, 1, 1);
        LocalDate maxDate = LocalDate.of(2100, 12, 31);

        // Try to parse with date and time (dd/MM/yyyy HH:mm)
        try {
            LocalDateTime parsedDateTime = LocalDateTime.parse(inputDeadline, dateTimeFormatter);

            // Check if the parsed date-time is within bounds
            if (parsedDateTime.toLocalDate().isBefore(minDate) || parsedDateTime.toLocalDate().isAfter(maxDate)) {
                throw new IllegalArgumentException(DATE_OUT_OF_RANGE);
            }
            return parsedDateTime;

        } catch (DateTimeParseException e) {
            // If parsing with date and time fails, attempt date-only parsing (dd/mm/yyyy)
            try {
                LocalDate parsedDate = LocalDate.parse(inputDeadline, dateFormatter);

                // Check if the parsed date is within bounds
                if (parsedDate.isBefore(minDate) || parsedDate.isAfter(maxDate)) {
                    throw new IllegalArgumentException(DATE_OUT_OF_RANGE);
                }
                return parsedDate.atStartOfDay();

            } catch (DateTimeParseException ex) {
                // Throw a descriptive exception if both formats fail
                throw new IllegalArgumentException(INVALID_DATETIME_MESSAGE);
            }
        }
    }

}
