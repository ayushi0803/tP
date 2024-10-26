package fittrack.reminder;

import fittrack.user.User;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Reminder {

    private final LocalDateTime reminderDeadline;
    private final String reminderDescription;
    private final User User;

    public Reminder(String description, LocalDateTime deadline, User user) {
        this.reminderDescription = description;
        this.reminderDeadline = deadline;
        this.User = user;

    }

    public void printReminderDescription() {
        System.out.print(this.reminderDescription + " | " +
            this.reminderDeadline.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")) + System.lineSeparator());
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
