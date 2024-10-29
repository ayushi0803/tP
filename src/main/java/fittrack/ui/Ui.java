package fittrack.ui;

import fittrack.reminder.Reminder;
import fittrack.trainingsession.TrainingSession;
import fittrack.user.User;

import java.util.ArrayList;
import java.util.List;


import static fittrack.messages.Messages.ADD_REMINDER_MESSAGE;
import static fittrack.messages.Messages.ADD_SESSION_MESSAGE;
import static fittrack.messages.Messages.DELETE_REMINDER_MESSAGE;
import static fittrack.messages.Messages.DELETE_SESSION_MESSAGE;
import static fittrack.messages.Messages.EXIT_MESSAGE;
import static fittrack.messages.Messages.HELP_MESSAGE;
import static fittrack.messages.Messages.INIT_SENTENCE;
import static fittrack.messages.Messages.INVALID_INPUT_MESSAGE;
import static fittrack.messages.Messages.LIST_REMINDER_EMPTY_MESSAGE;
import static fittrack.messages.Messages.LIST_REMINDER_MESSAGE;
import static fittrack.messages.Messages.LIST_SESSION_EMPTY_MESSAGE;
import static fittrack.messages.Messages.LIST_SESSION_MESSAGE;
import static fittrack.messages.Messages.NO_UPCOMING_REMINDERS;
import static fittrack.messages.Messages.SEPARATOR;
import static fittrack.reminder.Reminder.findUpcomingReminders;


public class Ui {
    /**
     * Prints a line of underscores to mark the start of a print segment.
     */
    public static void beginSegment() {
        System.out.println(SEPARATOR);
    }

    /**
     * Prints a line of underscores followed by a newline to mark the end of a print segment.
     */
    public static void endSegment() {
        System.out.println(SEPARATOR + System.lineSeparator());
    }

    public static void printGreeting() {
        beginSegment();
        System.out.println(INIT_SENTENCE); // Print the initial greeting
        endSegment();
    }

    public static void printUser(User user) {
        assert user != null : "User object must not be null";
        assert user.getAge() > 0 : "User age must be greater than 0";
        assert user.getGender() != null : "User gender must not be null";

        beginSegment();
        System.out.println("You are a " + user.getAge() + " year old "
            + user.getGender().toString().toLowerCase() + ".");
        endSegment();
    }

    public static void printHelp() {
        beginSegment();
        System.out.println(HELP_MESSAGE);
        endSegment();
    }

    public static void printAddedSession(ArrayList<TrainingSession> sessionList) {
        assert sessionList != null : "Session list must not be null";
        assert !sessionList.isEmpty() : "Session list must not be empty";

        beginSegment();
        System.out.println(ADD_SESSION_MESSAGE);
        System.out.print(sessionList.size() + ". ");
        sessionList.get(sessionList.size() - 1).printSessionDescription();
        printSessionCount(sessionList);
        endSegment();
    }

    public static void printDeletedSession(ArrayList<TrainingSession> sessionList, TrainingSession sessionToDelete) {
        assert sessionList != null : "Session list must not be null";
        assert sessionToDelete != null : "Session to delete must not be null";

        beginSegment();
        System.out.print(DELETE_SESSION_MESSAGE);
        sessionToDelete.printSessionDescription();
        printSessionCount(sessionList);
        endSegment();
    }

    public static void printSessionList(ArrayList<TrainingSession> sessionList) {
        assert sessionList != null : "Session list must not be null";

        beginSegment();
        if (sessionList.isEmpty()) {
            System.out.println(LIST_SESSION_EMPTY_MESSAGE);
            endSegment();
            return;
        }
        int index = 0;
        System.out.println(LIST_SESSION_MESSAGE);
        while (index < sessionList.size()) {
            System.out.print(index + 1 + ". ");
            sessionList.get(index).printSessionDescription();
            index++;
        }
        printSessionCount(sessionList);
        endSegment();
    }

    public static void printSessionView(ArrayList<TrainingSession> sessionList, int index) {
        assert sessionList != null : "Session list must not be null";
        assert index >= 0 && index < sessionList.size() : "Index is out of bounds";

        beginSegment();
        sessionList.get(index).viewSession();
        endSegment();
    }

    public static void printSessionCount(List<TrainingSession> sessionList) {
        assert sessionList != null : "Session list must not be null";

        System.out.println("There are " + sessionList.size() + " sessions in the list.");
    }

    public static void printReminderCount(ArrayList<Reminder> reminderList) {
        assert reminderList != null : "Reminder list must not be null";

        System.out.println("There are " + reminderList.size() + " reminders in your list.");
    }

    public static void printAddedReminder(ArrayList<Reminder> reminderList) {
        assert reminderList != null : "Reminder list must not be null";
        assert !reminderList.isEmpty() : "Reminder list must not be empty";

        beginSegment();
        System.out.println(ADD_REMINDER_MESSAGE);
        System.out.print(reminderList.size() + ". ");
        reminderList.get(reminderList.size() - 1).printReminderDescription();
        printReminderCount(reminderList);
        endSegment();
    }

    /**
     * Called at program initialisation. Prints a list of upcoming reminders due in the next week.
     *
     * @param reminderList The list of all reminders.
     * @throws IllegalArgumentException if the reminder list is null.
     */

    public static void printUpcomingReminders(ArrayList<Reminder> reminderList) {
        assert reminderList != null : "Reminder list must not be null";

        if (reminderList.isEmpty()) {
            System.out.println(LIST_REMINDER_EMPTY_MESSAGE);
            System.out.println(NO_UPCOMING_REMINDERS);
            endSegment();
            return;
        }

        ArrayList<Reminder> upcomingReminders = findUpcomingReminders(reminderList);
        System.out.println("There are " + upcomingReminders.size() + " reminders due in the next week:");

        // Print upcoming reminders
        int index = 0;
        while (index < upcomingReminders.size()) {
            System.out.print(index + 1 + ". ");
            upcomingReminders.get(index).printReminderDescription();
            index++;
        }
        System.out.println("You have " + reminderList.size() + " reminders in total. View them with 'list-remind'.");
        endSegment();
    }

    public static void printDeletedReminder(ArrayList<Reminder> reminderList, Reminder reminderToDelete) {
        assert reminderList != null : "Session list must not be null";
        assert reminderToDelete != null : "Session to delete must not be null";

        beginSegment();
        System.out.print(DELETE_REMINDER_MESSAGE);
        reminderToDelete.printReminderDescription();
        printReminderCount(reminderList);
        endSegment();
    }

    public static void printReminderList(ArrayList<Reminder> reminderList) {
        assert reminderList != null : "Reminder list must not be null";

        beginSegment();
        if (reminderList.isEmpty()) {
            System.out.println(LIST_SESSION_EMPTY_MESSAGE);
            endSegment();
            return;
        }
        int index = 0;
        System.out.println(LIST_REMINDER_MESSAGE);
        while (index < reminderList.size()) {
            System.out.print(index + 1 + ". ");
            reminderList.get(index).printReminderDescription();
            index++;
        }
        printReminderCount(reminderList);
        endSegment();
    }

    public static void printUnrecognizedInputMessage() {
        beginSegment();
        System.out.println(INVALID_INPUT_MESSAGE); // Print the unrecognized input message
        endSegment();
    }

    public static void printExitMessage() {
        beginSegment();
        System.out.println(EXIT_MESSAGE); // Print the exit message
        endSegment();
    }
}
