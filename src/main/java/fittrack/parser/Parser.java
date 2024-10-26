package fittrack.parser;
import fittrack.fitnessgoal.Goal;
import fittrack.trainingsession.TrainingSession;
import fittrack.reminder.Reminder;
import fittrack.user.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Objects;

import static fittrack.enums.Exercise.fromUserInput;

import static fittrack.messages.Messages.ADD_REMINDER_COMMAND;
import static fittrack.messages.Messages.ADD_SESSION_COMMAND;
import static fittrack.messages.Messages.DELETE_REMINDER_COMMAND;
import static fittrack.messages.Messages.DELETE_SESSION_COMMAND;
import static fittrack.messages.Messages.EDIT_EXERCISE_COMMAND;
import static fittrack.messages.Messages.HELP_COMMAND;
import static fittrack.messages.Messages.LIST_REMINDER_COMMAND;
import static fittrack.messages.Messages.LIST_SESSIONS_COMMAND;
import static fittrack.messages.Messages.LIST_UPCOMING_REMINDER_COMMAND;
import static fittrack.messages.Messages.SET_USER_COMMAND;
import static fittrack.messages.Messages.VIEW_SESSION_COMMAND;
import static fittrack.ui.Ui.beginSegment;
import static fittrack.ui.Ui.printAddedReminder;
import static fittrack.ui.Ui.printAddedSession;
import static fittrack.ui.Ui.printDeletedReminder;
import static fittrack.ui.Ui.printDeletedSession;
import static fittrack.ui.Ui.printHelp;
import static fittrack.ui.Ui.printReminderList;
import static fittrack.ui.Ui.printSessionList;
import static fittrack.ui.Ui.printSessionView;
import static fittrack.ui.Ui.printUnrecognizedInputMessage;
import static fittrack.ui.Ui.printUpcomingReminders;
import static fittrack.ui.Ui.printUser;


public class Parser {

    /**
    * Parses the user's input and calls the corresponding method based on the command.
    *
    * @param user The user object to be manipulated based on the command.
    * @param input The input string entered by the user.
    * @param sessionList The list of sessions to be manipulated based on the command.
    * @param reminderList The list of reminders to be manipulated based on the command.
    */
    private static void printGoalList(ArrayList<String> goalList) {
        if (goalList.isEmpty()){
            System.out.println("Your goals list is currently empty.");
        } else {
            System.out.println("Your goals:");
            for (int i = 0; i < goalList.size(); i++) {
                System.out.println((i + 1) + ". " + goalList.get(i));
            }
        }
    }

    private static void printAddedGoal(ArrayList<Goal> goalList) {
        if (goalList.isEmpty()) {
            System.out.println("Your goals list is currently empty.");
        } else {
            Goal lastGoal = goalList.get(goalList.size() - 1); // Get the last added goal
            System.out.println("Goal added: " + lastGoal.getDescription());
            if (lastGoal.getDeadline() != null) {
                System.out.println("Deadline: " +
                    lastGoal.getDeadline().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
            } else {
                System.out.println("No deadline set.");
            }
        }
    }

    private static void printDeletedGoal(ArrayList<Goal> goalList, String goalDescription) {
        System.out.println("Deleted goal: " + goalDescription);
        if (goalList.isEmpty()) {
            System.out.println("Your goals list is now empty.");
        } else {
            System.out.println("Current goals:");
            for (int i = 0; i < goalList.size(); i++) {
                System.out.println((i + 1) + ". " + goalList.get(i).getDescription());
            }
        }
    }

    public static void parse(User user, String input, ArrayList<TrainingSession> sessionList,
        ArrayList<Reminder> reminderList, ArrayList<Goal> goalList) {
            assert input != null : "Input must not be null";
            assert user != null : "User object must not be null";
            assert sessionList != null : "Session list must not be null";

        String[] sentence = {input, input};
        String command = input;
        String description = "";

        // Split the input into command and description if applicable
        if (input.contains(" ")) {
            sentence = input.split(" ", 2);
            command = sentence[0];
            description = sentence[1];
        }

        switch (command) {
        case SET_USER_COMMAND:
            assert description.contains(" ") : "Invalid user data format";
            sentence = description.split(" ", 2);
            user.setGender(sentence[0]);
            user.setAge(sentence[1]);
            printUser(user);
            break;
        case HELP_COMMAND:
            printHelp();
            break;
        case ADD_SESSION_COMMAND:
            assert !description.isEmpty() : "Session description must not be empty";
            LocalDateTime currentTime = LocalDateTime.now();
            sessionList.add(new TrainingSession(currentTime, description, user));
            printAddedSession(sessionList);
            break;
        case EDIT_EXERCISE_COMMAND:
            sentence = description.split(" ", 3);
            System.out.println(sentence[0]);
            assert sentence.length == 3 : "Edit exercise command requires exactly 3 arguments";
            int sessionIndex = Integer.parseInt(sentence[0]) - 1;
            String exerciseAcronym = sentence[1];
            String exerciseData = sentence[2];
            assert
                sessionIndex >= 0 && sessionIndex < sessionList.size() : "Session index out of bounds";
            try {
                sessionList.get(sessionIndex).editExercise(fromUserInput(exerciseAcronym), exerciseData);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            printSessionView(sessionList, sessionIndex);
            break;
        case LIST_SESSIONS_COMMAND:
            printSessionList(sessionList); // Print the list of sessions
            break;
        case VIEW_SESSION_COMMAND:
            int viewIndex = Integer.parseInt(description) - 1;
            assert
                viewIndex >= 0 && viewIndex < sessionList.size() : "View session index out of bounds";
            printSessionView(sessionList, viewIndex); // Print the session view
            break;
        case DELETE_SESSION_COMMAND:
            int indexToDelete = Integer.parseInt(description) - 1;
            assert indexToDelete >= 0
                && indexToDelete < sessionList.size() : "Delete session index out of bounds";
            TrainingSession sessionToDelete = sessionList.get(indexToDelete);
            sessionList.remove(indexToDelete);
            printDeletedSession(sessionList, sessionToDelete);
            break;
        case ADD_REMINDER_COMMAND:
            sentence = description.split(" ", 2);

            String inputDeadline = sentence[1];
            description = sentence[0];

            assert !description.isEmpty() : "Reminder description must not be empty";
            assert !Objects.equals(inputDeadline, "") : "Reminder deadline must not be empty";
            LocalDateTime deadline = parseReminderDeadline(inputDeadline);
            reminderList.add(new Reminder(description, deadline, user));
            printAddedReminder(reminderList);
            break;
        case DELETE_REMINDER_COMMAND:
            int reminderIndexToDelete = Integer.parseInt(description) - 1;
            assert reminderIndexToDelete >= 0 && reminderIndexToDelete < reminderList.size() :
                "Delete reminder index "
                    + "out of bounds";
            Reminder reminderToDelete = reminderList.get(reminderIndexToDelete);
            reminderList.remove(reminderIndexToDelete);
            printDeletedReminder(reminderList, reminderToDelete);
            break;
        case LIST_REMINDER_COMMAND:
            printReminderList(reminderList);
            break;
        case LIST_UPCOMING_REMINDER_COMMAND:
            beginSegment();
            printUpcomingReminders(reminderList);
            break;
        case "add goal":
            if (!description.isEmpty()) {
                String[] goalParts = description.split(" ", 2);
                String goalDescription = goalParts[0];
                LocalDateTime goalDeadline = null;

                if (goalParts.length > 1) {
                    String goalDeadlineInput = goalParts[1];
                    try {
                        goalDeadline = parseGoalDeadline(goalDeadlineInput);
                    } catch (IllegalArgumentException e) {
                        System.out.println("Invalid date format: " + e.getMessage());
                        return;
                    }
                }
                Goal newGoal = new Goal(goalDescription,
                    goalDeadline); // Assuming Goal constructor takes description and deadline
                goalList.add(newGoal); // Add the Goal object to the goalList
                printAddedGoal(goalList); // Print added goal
            } else {
                System.out.println("Please specify a goal to add.");
            }
            break;
        case "delete goal":
            int goalIndexToDelete = Integer.parseInt(description) - 1;
            assert goalIndexToDelete >= 0
                && goalIndexToDelete < goalList.size() : "Delete goal index out of bounds";
            Goal goalToDelete = goalList.get(goalIndexToDelete);
            goalList.remove(goalIndexToDelete);
            printDeletedGoal(goalList,
                goalToDelete.getDescription());
            break;

        default:
            printUnrecognizedInputMessage(); // Response to unrecognized inputs
            break;
        }
    }

    private static LocalDateTime parseGoalDeadline(String inputDeadline)
                throws IllegalArgumentException {
        try {
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
            try {
                return LocalDateTime.parse(inputDeadline, dateTimeFormatter);
            } catch (DateTimeParseException e) {
                LocalDate date = LocalDate.parse(inputDeadline, dateFormatter);
                return date.atStartOfDay();
            }
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid date format. Please use DD/MM/YYYY or DD/MM/YYYY HH:mm:ss.");
        }
    }

    /**
     * Parses user input indicating the deadline of a {@code reminder} object.
     * Throws an exception if user-input String is inappropriate or ill-formatted.
     *
     * @param inputDeadline A string input by the user. Intended format is DD/MM/YYYY or DD/MM/YYYY HH:mm:ss.
     * @return A {@code LocalDateTime} object indicating reminder deadline
     * @throws IllegalArgumentException
     */
    static LocalDateTime parseReminderDeadline(String inputDeadline) throws IllegalArgumentException {
        try {
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

            // Try to parse with time (dd/MM/yyyy HH:mm:ss)
            try {
                return LocalDateTime.parse(inputDeadline, dateTimeFormatter);
            } catch (DateTimeParseException e) {
                // If failed, try to parse without time (HH:mm:ss)
                LocalDate date = LocalDate.parse(inputDeadline, dateFormatter);
                return date.atStartOfDay();
            }
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid date format. Please use DD/MM/YYYY or DD/MM/YYYY HH:mm:ss.");
        }
    }
}
