package fittrack.storage;

import fittrack.exception.InvalidSaveDataException;
import fittrack.fitnessgoal.Goal;
import fittrack.healthprofile.FoodEntry;
import fittrack.healthprofile.FoodWaterIntake;
import fittrack.healthprofile.WaterEntry;
import fittrack.reminder.Reminder;
import fittrack.trainingsession.TrainingSession;
import fittrack.user.User;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

import static fittrack.logger.FitTrackLogger.LOGGER;

public class Storage {

    public static final String SAVE_FILE = "data/saveFile.txt"; // Path to the save file
    public static final File SAVEFILE = new File(SAVE_FILE); // File object for the save file

    public static final String DATA_DELIMITER = "|";
    public static final String DATA_DELIMITER_REGEX = "\\|";

    /**
     * Initializes the save file by creating the necessary directories and file if they do not exist.
     */
    public static void initialiseSaveFile() {
        Path dirPath = Paths.get("data"); // Path to the data directory
        Path filePath = dirPath.resolve("saveFile.txt"); // Path to the save file
        File file = new File(filePath.toString()); // File object for the save file

        // Create directories if they do not exist
        if (!file.exists()) {
            try {
                Files.createDirectories(dirPath);
            } catch (IOException e) {
                System.out.println("SaveFile directory could not be created");
                LOGGER.info("SaveFile directory could not be created");
                throw new RuntimeException(e);
            }
        }

        // Create a save file if it does not already exist
        try {
            if (file.createNewFile()) {
                System.out.println("No existing save file found. New save file created: " + file.getName());
                LOGGER.info("No existing save file found. New save file created: " + file.getName());
            } else {
                System.out.println("Accessing existing save file...");
                LOGGER.info("Accessing existing save file...");
            }
        } catch (IOException e) {
            System.out.println("Save file could not be created.");
            LOGGER.info("Save file could not be created.");
            throw new RuntimeException(e);
        }

        // Assert that the file exists after initialization
        assert file.exists() : "Save file should exist after initialization";
    }

    /**
     * Loads Saveable items from a save file into the provided list.
     * Each line in the save file represents a serialized Saveable item and is parsed to
     * create the corresponding object. If an error occurs, loading terminates, and a
     * message is logged.
     *
     * @param loadList An ArrayList to store Saveable items loaded from the save file.
     * @throws FileNotFoundException If the save file is not found.
     * @throws InvalidSaveDataException If an invalid save format or unrecognized item type is encountered.
     */
    public static void loadSaveFile(ArrayList<Saveable> loadList) {
        try (Scanner scanner = new Scanner(SAVEFILE)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                Saveable item = createSaveableFromString(line);
                loadList.add(item);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Scanner object failed to initialise.");
            LOGGER.info("Scanner object failed to initialise.");
            return;
        } catch (InvalidSaveDataException e) {
            System.out.println("Invalid save data found. Data loading terminated.");
            LOGGER.info("Invalid save data found. Data loading terminated.");
            return;
        }

        System.out.println("Save file successfully loaded.");
        LOGGER.info("Save file successfully loaded.");
    }

    /**
     * Factory method to create Saveable objects from their string representations.
     * Based on the prefix of the string, this method determines the appropriate
     * Saveable subclass (TrainingSession, Goal, or Reminder) and delegates to its
     * fromSaveString method. If the descriptor is unrecognized, an exception is thrown.
     *
     * @param saveString A string representation of a Saveable item, prefixed by the item type.
     * @return A Saveable object corresponding to the serialized data.
     * @throws InvalidSaveDataException If the prefix does not match any recognized Saveable subclass.
     */
    private static Saveable createSaveableFromString(String saveString) throws InvalidSaveDataException {
        if (saveString.startsWith("TrainingSession")) {
            return TrainingSession.fromSaveString(saveString);
        } else if (saveString.startsWith("Goal")) {
            return Goal.fromSaveString(saveString);
        } else if (saveString.startsWith("Reminder")) {
            return Reminder.fromSaveString(saveString);
        } else if (saveString.startsWith("Water")) {
            return WaterEntry.fromSaveString(saveString);
        } else if (saveString.startsWith("Food")) {
            return FoodEntry.fromSaveString(saveString);
        } else if (saveString.startsWith("User")) {
            return User.fromSaveString(saveString);
        }
        throw new InvalidSaveDataException("Unrecognised Saveable descriptor detected.");
    }

    /**
     * Updates the save file with the latest information from the provided lists of
     * `TrainingSession`, `Goal`, and `Reminder` objects. Each list item is serialized
     * into its string representation and written to the save file, with each entry on
     * a new line.
     *
     * @param sessionList The list of `TrainingSession` objects to be saved.
     * @param goalList The list of `Goal` objects to be saved.
     * @param reminderList The list of `Reminder` objects to be saved.
     * @throws IOException If any of the provided lists are null or if an I/O error occurs during writing.
     */
    public static void updateSaveFile(User user, ArrayList<TrainingSession> sessionList, ArrayList<Goal> goalList,
                                      ArrayList<Reminder> reminderList,
                                      FoodWaterIntake foodWaterList) throws IOException {

        // Determine  provided lists are not null before saving
        if (sessionList == null || goalList == null || reminderList == null || foodWaterList == null) {
            throw new IOException("Save file could not be updated. Invalid null list passed to function");
        }

        try (FileWriter fw = new FileWriter(SAVEFILE)) {

            // Update user information
            fw.write(user.toSaveString());
            fw.write(System.lineSeparator());

            for (TrainingSession session : sessionList) {
                // Assert that session objects are valid
                assert session != null : "Training session must not be null";
                fw.write(session.toSaveString()); // Write the session to the file
                fw.write(System.lineSeparator()); // Add a new line after each session
                LOGGER.info("Save file successfully updated.");
            }
            for (Goal goal : goalList) {
                // Assert that session objects are valid
                assert goal != null : "Training session must not be null";
                fw.write(goal.toSaveString()); // Write the goal to the file
                fw.write(System.lineSeparator()); // Add a new line after each goal
                LOGGER.info("Save file successfully updated.");
            }
            for (Reminder reminder : reminderList) {
                // Assert that session objects are valid
                assert reminder != null : "Training session must not be null";
                fw.write(reminder.toSaveString()); // Write the reminder to the file
                fw.write(System.lineSeparator()); // Add a new line after each reminder
                LOGGER.info("Save file successfully updated.");
            }
            for (FoodEntry entry : foodWaterList.getFoodList()) {
                // Assert that session objects are valid
                assert entry != null : "Entry must not be null";
                fw.write(entry.toSaveString()); // Write the reminder to the file
                fw.write(System.lineSeparator()); // Add a new line after each reminder
                LOGGER.info("Save file successfully updated.");
            }
            for (WaterEntry entry : foodWaterList.getWaterList()) {
                // Assert that session objects are valid
                assert entry != null : "Entry must not be null";
                fw.write(entry.toSaveString()); // Write the reminder to the file
                fw.write(System.lineSeparator()); // Add a new line after each reminder
                LOGGER.info("Save file successfully updated.");
            }
        } catch (FileNotFoundException e) {
            System.out.println("Save file could not be updated (File could not be found).");
        }
    }
}
