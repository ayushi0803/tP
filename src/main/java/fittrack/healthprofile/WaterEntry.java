package fittrack.healthprofile;

import fittrack.storage.Saveable;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import static fittrack.storage.Storage.DATA_DELIMITER;
import static fittrack.storage.Storage.DATA_DELIMITER_REGEX;

/**
 * Represents a record of water intake with a specific amount and timestamp.
 */
public class WaterEntry extends Saveable {
    private final int amount;                // Amount of water intake in milliliters
    private final LocalDateTime dateTime;    // Date and time of the water intake

    /**
     * Constructs a WaterEntry with the specified amount and timestamp.
     *
     * @param inputAmount The amount of water intake in milliliters.
     * @param inputDateTime The date and time of the water intake.
     */
    public WaterEntry(int inputAmount, LocalDateTime inputDateTime) {
        this.amount = inputAmount;
        this.dateTime = inputDateTime;  // Automatically set the timestamp to current time
    }
    /**
     * Gets the amount of water intake.
     *
     * @return The amount in milliliters.
     */
    public int getAmount() {
        return amount;
    }

    /**
     * Gets the timestamp of the water entry.
     *
     * @return LocalDateTime object representing the date and time of the entry.
     */
    public LocalDateTime getDateTime() {
        return dateTime;  // Return the timestamp of the water entry
    }

    /**
     * Gets the date portion of the timestamp.
     *
     * @return The date of the water entry as a LocalDate.
     */
    public Object getLocalDate() {
        return dateTime.toLocalDate();
    }

    /**
     * Formats the timestamp without milliseconds for display purposes.
     *
     * @return Formatted date-time string in "dd/MM/yyyy HH:mm" format.
     */
    public String getFormattedDateTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return dateTime.format(formatter);  // Format date-time without milliseconds
    }

    /**
     * Converts the WaterEntry to a string for display.
     *
     * @return String representing the amount and formatted timestamp.
     */
    @Override
    public String toString() {
        return amount + " ml, added on " + getFormattedDateTime();
    }

    /**
     * Converts the WaterEntry to a save string for storage.
     *
     * @return Formatted save string in "Water|amount|dateTime" format.
     */
    @Override
    public String toSaveString() {
        return "Water" + DATA_DELIMITER + amount + DATA_DELIMITER + getFormattedDateTime();
    }

    /**
     * Recreates a WaterEntry from a save string.
     *
     * @param saveString The save string to parse.
     * @return A new WaterEntry if parsing is successful; null if parsing fails.
     * @throws IllegalArgumentException if the save string format is invalid.
     */
    public static WaterEntry fromSaveString(String saveString) {
        String[] stringData = saveString.split(DATA_DELIMITER_REGEX);

        // Validate format of save string
        if (stringData.length < 3 || !stringData[0].equals("Water")) {
            throw new IllegalArgumentException("Invalid save string format for Water Entry");
        }

        int waterAmount = Integer.parseInt(stringData[1]);  // Parse the amount of water

        try {
            // Parse date and time with specified format
            LocalDateTime waterEntryTime = LocalDateTime.parse(stringData[2],
                    DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
            return new WaterEntry(waterAmount, waterEntryTime);
        } catch (DateTimeParseException e) {
            System.out.println("Invalid save string format for Water Entry");
        }

        return null;
    }

}
