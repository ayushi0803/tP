package fittrack.reminder;

import fittrack.storage.Saveable;
import fittrack.user.User;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import static fittrack.storage.Storage.DATA_DELIMITER;
import static fittrack.storage.Storage.DATA_DELIMITER_REGEX;

public class Reminder extends Saveable {

    private final LocalDateTime reminderDeadline;
    private final String reminderDescription;
    private User user;

    public Reminder(String description, LocalDateTime deadline, User user) {
        this.reminderDescription = description;
        this.reminderDeadline = deadline;
        this.user = user;

    }

    public String getDescription() {
        return reminderDescription;
    }

    public String getDeadlineString() {
        return reminderDeadline.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
    }

    public void printReminderInformation() {
        System.out.print(this.reminderDescription + " | " +
                this.reminderDeadline.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")) + System.lineSeparator());
    }

    /**
     * Saves the `Reminder` object's data in a formatted string representation for storage.
     * The format includes the reminder type, description, deadline, and user data.
     * Format: {@code Reminder | Description | Deadline | User}.
     *
     * @return A string in the format {@code Reminder | Description | Deadline | User}.
     */
    @Override
    public String toSaveString() {
        return "Reminder" + DATA_DELIMITER + reminderDescription + DATA_DELIMITER +
                reminderDeadline.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")) + DATA_DELIMITER
                + user.toString();
    }

    /**
     * Creates a `Reminder` object from a formatted string loaded from the save file.
     * The format should match: {@code "Reminder" | Description | Deadline | User}.
     * <p>
     * This method parses the reminder description, deadline, and user information
     * to reconstruct the `Reminder` object. The deadline format is expected to be "dd/MM/yyyy HH:mm" or "dd/MM/yyyy".
     *
     * @param saveString A formatted string representation of a `Reminder`.
     * @return A new `Reminder` instance populated with the parsed data.
     * @throws IllegalArgumentException If the format of `saveString` is invalid or does not match the expected format.
     */
    public static Reminder fromSaveString(String saveString) {
        // Split the string by the " | " delimiter
        String[] stringData = saveString.split(DATA_DELIMITER_REGEX);

        // Check if the format is correct
        if (stringData.length < 3 || !stringData[0].equals("Reminder")) {
            throw new IllegalArgumentException("Invalid save string format for Reminder: " + saveString);
        }

        String reminderDescription = stringData[1];
        LocalDateTime reminderDeadline = LocalDateTime.parse(stringData[2],
                DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));

        String[] reminderUserData = stringData[3].split(" ");

        User reminderUser = new User(reminderUserData[0], reminderUserData[1]);

        return new Reminder(reminderDescription, reminderDeadline, reminderUser);
    }

    /**
     * Finds and returns a list of reminders from the main reminder list that occur within the next week.
     *
     * @param mainReminderList The main list of reminders to be checked.
     * @return {@code upcomingReminderList} Containing reminders that occur within the next week from now.
     */
    public static ArrayList<Reminder> findUpcomingReminders(ArrayList<Reminder> mainReminderList) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime nextWeek = now.plusWeeks(1);
        ArrayList<Reminder> upcomingReminderList = new ArrayList<>();

        for (Reminder reminder : mainReminderList) {
            if (reminder.reminderDeadline.isAfter(now) && reminder.reminderDeadline.isBefore(nextWeek)) {
                upcomingReminderList.add(reminder);
            }
        }
        return upcomingReminderList;
    }
}
