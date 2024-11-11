package fittrack.fitnessgoal;
import static fittrack.logger.FitTrackLogger.LOGGER;
import static fittrack.storage.Storage.DATA_DELIMITER;
import static fittrack.storage.Storage.DATA_DELIMITER_REGEX;

import fittrack.storage.Saveable;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.logging.Level;

public class Goal extends Saveable {

    private final String description;
    private final LocalDateTime deadline;

    // Constructor
    public Goal(String description, LocalDateTime deadline) {
        this.description = description;
        this.deadline = deadline;
    }

    // Getter for the goal description
    public String getDescription() {
        return description;
    }

    // Getter for the goal deadline
    public LocalDateTime getDeadline() {
        return deadline;
    }

    // Optionally, you might want to override the toString() method for easy printing
    public String toString() {
        return "Goal: " + description + (deadline != null ? ", Deadline: " + deadline : "");
    }

    /**
     * Saves the `Goal` object's data in a formatted string representation for storage.
     * The format includes the goal type, description, and optional deadline.
     * Format: {@code "Goal" | Description | Deadline}.
     * <p>
     * The deadline format is expected to be "dd/MM/yyyy HH:mm" or "dd/MM/yyyy".
     *
     * @return A string in the format {@code "Goal" | Description | Deadline}.
     */
    @Override
    public String toSaveString() {
        return "Goal" + DATA_DELIMITER + description + DATA_DELIMITER + (deadline != null ?
                deadline.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")) : "");
    }

    /**
     * Creates a `Goal` object from a formatted string loaded from the save file.
     * The format should match: {@code "Goal" | Description | Deadline}.
     * <p>
     * This method parses the goal description and optional deadline to reconstruct
     * the `Goal` object. If the deadline is provided, it must match the "dd/MM/yyyy HH:mm" or "dd/MM/yyyy" format.
     *
     * @param saveString A formatted string representation of a `Goal`.
     * @return A new `Goal` instance populated with the parsed data, or `null` if the deadline format is invalid.
     * @throws IllegalArgumentException If the format of `saveString` is invalid or does not match the expected format.
     */
    public static Goal fromSaveString(String saveString) {

        // Split the string by the " | " delimiter
        String[] stringData = saveString.split(" \\| ");
        String[] parts = saveString.split(";");
        System.out.println("Parts length: " + parts.length); // Debug log

        if (parts.length < 2) {
            System.out.println("Save string received: " + saveString); // Add this for debugging
        }

        String goalDescription = stringData[1];
        LocalDateTime goalDeadline = null; // Default to null if no deadline is provided


        // Check if the deadline is present and parse it if available
        if (stringData.length > 2) {
            try {
                goalDeadline = LocalDateTime.parse(stringData[2], DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
            } catch (DateTimeParseException e) {
                // Log or handle the error more explicitly
                LOGGER.log(
                    Level.WARNING, "Invalid deadline format for goal: " + goalDescription + " | " + saveString, e);
            }
        }

        // Return the Goal object, with null deadline if not present
        return new Goal(goalDescription, goalDeadline);
    }


}
