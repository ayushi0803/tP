package fittrack.ui;

import fittrack.enums.Exercise;
import fittrack.graph.GraphPerformance;
import fittrack.graph.GraphPoints;
import fittrack.reminder.Reminder;
import fittrack.trainingsession.TrainingSession;

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
import static fittrack.messages.Messages.INVALID_LIST_COMMAND_MESSAGE;
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

    public static void printUser(int userAge, String userGender) {
        beginSegment();
        System.out.println("You are a " + userAge + " year old " + userGender + ".");
        endSegment();
    }

    public static void printHelp() {
        beginSegment();
        System.out.println(HELP_MESSAGE);
        endSegment();
    }

    public static void printAddedSession(ArrayList<TrainingSession> sessionList, int sessionIndex) {
        assert sessionList != null : "Session list must not be null";
        assert !sessionList.isEmpty() : "Session list must not be empty";
        beginSegment();
        System.out.println(ADD_SESSION_MESSAGE);
        System.out.print(sessionList.size() + ". ");
        sessionList.get(sessionIndex).printSessionInformation();
        printSessionCount(sessionList);
        endSegment();
    }

    public static void printModifiedSession(ArrayList<TrainingSession> sessionList, int sessionIndex) {
        assert sessionList != null : "Session list must not be null";
        assert !sessionList.isEmpty() : "Session list must not be empty";
        beginSegment();
        System.out.println("Session " + sessionIndex + " has been modified:");
        System.out.println("New Date/Time: " + sessionList.get(sessionIndex).getSessionDatetime());
        endSegment();
    }

    public static void printUpdatedMood(int sessionId, String newMood) {
        System.out.println(SEPARATOR);
        System.out.println("Mood for Training Session " + (sessionId + 1) + " updated: " + newMood);
        System.out.println(SEPARATOR);
    }

    public static void printDeletedSession(ArrayList<TrainingSession> sessionList, TrainingSession sessionToDelete,
                                           String sessionDescription) {
        assert sessionList != null : "Session list must not be null";
        assert sessionToDelete != null : "Session to delete must not be null";
        beginSegment();
        System.out.print(DELETE_SESSION_MESSAGE);
        System.out.println(sessionDescription);
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
        int sessionIndex = 0;
        System.out.println(LIST_SESSION_MESSAGE);
        while (sessionIndex < sessionList.size()) {
            System.out.print(sessionIndex + 1 + ". ");
            sessionList.get(sessionIndex).printSessionInformation();
            sessionIndex++;
        }
        printSessionCount(sessionList);
        endSegment();
    }

    public static void printInvalidListCommandMessage() {
        beginSegment();
        System.out.println(INVALID_LIST_COMMAND_MESSAGE);
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

    public static void printPointGraph(Exercise exercise, ArrayList<TrainingSession> sessionList){
        if(exercise == null){
            GraphPoints.graphSessions(sessionList);
        } else {
            GraphPoints.graphExercisePoints(exercise, sessionList);
        }
    }

    public static void printPerformanceGraph(Exercise exercise, ArrayList<TrainingSession> sessionList){
        GraphPerformance.graphExercisePerformance(exercise, sessionList);
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
        reminderList.get(reminderList.size() - 1).printReminderInformation();
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

        beginSegment();
        ArrayList<Reminder> upcomingReminders = findUpcomingReminders(reminderList);
        System.out.println("There are " + upcomingReminders.size() + " reminders due in the next week:");

        // Print upcoming reminders
        int index = 0;
        while (index < upcomingReminders.size()) {
            System.out.print(index + 1 + ". ");
            upcomingReminders.get(index).printReminderInformation();
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
        reminderToDelete.printReminderInformation();
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
            reminderList.get(index).printReminderInformation();
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

