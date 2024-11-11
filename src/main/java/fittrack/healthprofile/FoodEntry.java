package fittrack.healthprofile;

import fittrack.storage.Saveable;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;

import static fittrack.storage.Storage.DATA_DELIMITER;
import static fittrack.storage.Storage.DATA_DELIMITER_REGEX;

public class FoodEntry extends Saveable {
    private String foodName;        // Name of the food item
    private int calories;           // Calorie content of the food item
    private LocalDateTime dateTime; // Timestamp of when the food item was added

    /**
     * Constructs a FoodEntry with the specified food name, calorie count, and timestamp.
     *
     * @param inputFoodName Name of the food item.
     * @param inputCalories Calorie content of the food item.
     * @param inputDateTime Timestamp of when the food item was added.
     */

    public FoodEntry(String inputFoodName, int inputCalories, LocalDateTime inputDateTime) {
        this.foodName = inputFoodName ;
        this.calories = inputCalories;
        this.dateTime = inputDateTime;  }

    // Returns the name of the food item
    public String getFoodName() {
        return foodName;
    }

    public int getCalories() {
        return calories;
    }

    /**
     * Formats the timestamp of the food entry to exclude milliseconds.
     *
     * @return Formatted date-time string in "dd/MM/yyyy HH:mm" format.
     */
    public String getFormattedDateTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return dateTime.format(formatter);  // Format date-time without milliseconds
    }

    /**
     * Returns the date-time part of the timestamp, truncated to milliseconds.
     *
     * @return LocalDateTime representing the date and time of the food entry, truncated to milliseconds.
     */
    public LocalDateTime getTruncatedDateTime() {
        return dateTime.truncatedTo(ChronoUnit.MILLIS);
    }

    /**
     * Provides a string representation of the food entry, including its name, calories,
     * and formatted timestamp.
     *
     * @return String representation of the food entry.
     */
    @Override
    public String toString() {
        return foodName + " - " + calories + " calories, added on " + getFormattedDateTime();
    }

    /**
     * Formats the food entry as a string suitable for saving, with fields separated by "|".
     *
     * @return Formatted save string for the food entry.
     */
    @Override
    public String toSaveString() {
        return "Food" + DATA_DELIMITER + foodName + DATA_DELIMITER + calories + DATA_DELIMITER + getFormattedDateTime();
    }

    /**
     * Recreates a FoodEntry from a saved string representation.
     *
     * @param saveString The save string representing a FoodEntry, typically from storage.
     * @return A FoodEntry object parsed from the save string.
     * @throws IllegalArgumentException if the save string format is invalid.
     */
    public static FoodEntry fromSaveString(String saveString) {
        String[] stringData = saveString.split(DATA_DELIMITER_REGEX);

        // Validate the save string format
        if (stringData.length < 4 || !stringData[0].equals("Food")) {
            throw new IllegalArgumentException("Invalid save string format for Food Entry");
        }

        // Parse fields from the save string
        String foodDescription = stringData[1];
        int calories = Integer.parseInt(stringData[2]);

        try {
            LocalDateTime foodEntryTime = LocalDateTime.parse(stringData[3],
                    DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
            return new FoodEntry(foodDescription, calories, foodEntryTime);
        } catch (DateTimeParseException e) {
            System.out.println("Invalid save string format for Food Entry");
        }

        return null;
        // Return null if parsing fails
    }
}
