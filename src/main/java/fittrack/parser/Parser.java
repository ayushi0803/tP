// Parser class to handle user input and manage various FitTrack commands, organizing
// user goals, sessions, reminders, food, and water intake into respective lists

// Import relevant packages and modules for handling data operations
package fittrack.parser;

// Import user, goal, and training session handling modules
import fittrack.fitnessgoal.Goal;
import fittrack.healthprofile.FoodEntry;
import fittrack.healthprofile.FoodWaterIntake;
import fittrack.healthprofile.WaterEntry;
import fittrack.trainingsession.TrainingSession;
import fittrack.reminder.Reminder;
import fittrack.user.User;

// Import Java standard libraries for date-time and array handling
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

// Import enums, exceptions, and message constants for input parsing and validation
import static fittrack.enums.Exercise.fromUserInput;

import static fittrack.exception.ParserExceptions.stringToValidInteger;
import static fittrack.exception.ParserExceptions.validModifySessionDateTime;
import static fittrack.exception.ParserExceptions.validSession;
import static fittrack.exception.ParserExceptions.validSessionIndex;
import static fittrack.exception.ParserExceptions.validUser;
import static fittrack.exception.ParserExceptions.parseUserInfo;
import static fittrack.exception.ParserExceptions.validEditDetails;
import static fittrack.messages.Messages.ADD_FOOD_COMMAND;
import static fittrack.messages.Messages.ADD_GOAL_COMMAND;
import static fittrack.messages.Messages.ADD_REMINDER_COMMAND;
import static fittrack.messages.Messages.ADD_SESSION_COMMAND;
import static fittrack.messages.Messages.ADD_WATER_COMMAND;
import static fittrack.messages.Messages.DELETE_FOOD_COMMAND;
import static fittrack.messages.Messages.DELETE_GOAL_COMMAND;
import static fittrack.messages.Messages.DELETE_REMINDER_COMMAND;
import static fittrack.messages.Messages.DELETE_SESSION_COMMAND;
import static fittrack.messages.Messages.DELETE_WATER_COMMAND;
import static fittrack.messages.Messages.EDIT_EXERCISE_COMMAND;
import static fittrack.messages.Messages.EDIT_MOOD_COMMAND;
import static fittrack.messages.Messages.GRAPH_PERFORMANCE_COMMAND;
import static fittrack.messages.Messages.GRAPH_POINTS_COMMAND;
import static fittrack.messages.Messages.HELP_COMMAND;
import static fittrack.messages.Messages.INDEX_OUT_OF_BOUNDS_MESSAGE;
import static fittrack.messages.Messages.INVALID_DATETIME_MESSAGE;
import static fittrack.messages.Messages.INVALID_INDEX_NON_NUMERIC_MESSAGE;
import static fittrack.messages.Messages.INVALID_SESSION_INDEX_MESSAGE;
import static fittrack.messages.Messages.INVALID_VERTICAL_BAR_INPUT_MESSAGE;
import static fittrack.messages.Messages.LIST_FOOD_COMMAND;
import static fittrack.messages.Messages.LIST_GOAL_COMMAND;
import static fittrack.messages.Messages.LIST_DAILY_INTAKE_COMMAND;
import static fittrack.messages.Messages.LIST_REMINDER_COMMAND;
import static fittrack.messages.Messages.LIST_SESSIONS_COMMAND;
import static fittrack.messages.Messages.LIST_UPCOMING_REMINDER_COMMAND;
import static fittrack.messages.Messages.LIST_WATER_COMMAND;
import static fittrack.messages.Messages.MODIFY_SESSION_DATETIME_COMMAND;
import static fittrack.messages.Messages.SEPARATOR;
import static fittrack.messages.Messages.SET_USER_COMMAND;
import static fittrack.messages.Messages.VIEW_SESSION_COMMAND;
import static fittrack.parser.ParserHelpers.parseDeadline;
import static fittrack.storage.Storage.updateSaveFile;
import static fittrack.ui.Ui.printAddedReminder;
import static fittrack.ui.Ui.printAddedSession;
import static fittrack.ui.Ui.printDeletedReminder;
import static fittrack.ui.Ui.printDeletedSession;
import static fittrack.ui.Ui.printHelp;
import static fittrack.ui.Ui.printInvalidListCommandMessage;
import static fittrack.ui.Ui.printModifiedSession;
import static fittrack.ui.Ui.printPerformanceGraph;
import static fittrack.ui.Ui.printPointGraph;
import static fittrack.ui.Ui.printReminderList;
import static fittrack.ui.Ui.printSessionList;
import static fittrack.ui.Ui.printSessionView;
import static fittrack.ui.Ui.printUnrecognizedInputMessage;
import static fittrack.ui.Ui.printUpcomingReminders;
import static fittrack.ui.Ui.printUpdatedMood;
import static fittrack.ui.Ui.printUser;

// Parser class to manage command input for goals, sessions, reminders, water, and food intake
public class Parser {
    // Method to print details of the recently added goal
    private static void printAddedGoal(ArrayList<Goal> goalList) {
        if (goalList.isEmpty()) {
            System.out.println("Your goals list is currently empty.");
        } else {
            Goal lastGoal = goalList.get(goalList.size() - 1); // Last added goal
            System.out.println("Goal added: " + lastGoal.getDescription());
            if (lastGoal.getDeadline() != null) {
                System.out.println("Deadline: " +
                    lastGoal.getDeadline().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
            } else {
                System.out.println("No deadline set.");
            }
        }
    }

    // Main parse method to interpret and execute commands based on user input
    public static void parse(User user, String input, ArrayList<TrainingSession> sessionList,
                             ArrayList<Reminder> reminderList, ArrayList<Goal> goalList, FoodWaterIntake
                                     foodWaterList) throws IOException {
        assert input != null : "Input must not be null";
        assert user != null : "User object must not be null";
        assert sessionList != null : "Session list must not be null";
        assert goalList != null : "Goal list must not be null";

        String[] sentence = {input, input};
        String command = input;
        String description = "";

        LocalDateTime timeNow = LocalDateTime.now();
        String formattedTimeNow = timeNow.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));

        // Throw exception if user inputs a vertical bar '|'
        if (input.contains("|")){
            throw new IOException(INVALID_VERTICAL_BAR_INPUT_MESSAGE);
        }

        // Split the input into command and description if applicable
        if (input.contains(" ")) {
            sentence = input.split(" ", 2);
            command = sentence[0].trim();
            description = sentence[1].trim();
        }

        // Switch-case structure to handle each command type
        switch (command.toLowerCase()) {
        case HELP_COMMAND:
            printHelp();
            break;
        case SET_USER_COMMAND:
            try {
                // Parse user information and validate user attributes
                // Check if details are appropriate
                String[] userInfo = parseUserInfo(description);
                validUser(userInfo[0], userInfo[1]);

                user.setGender(userInfo[0]);
                user.setAge(userInfo[1]);

                // Report updated user details to user and update save file
                printUser(user.getAge(), user.getGender().toString().toLowerCase());
                updateSaveFile(user, sessionList, goalList, reminderList,  foodWaterList);

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            break;
        case ADD_SESSION_COMMAND:
            try {
                // Add a new session to the session list after validating the input and user details.
                sessionList.add(validSession(description, user));
                int sessionIndex = sessionList.size() - 1;
                printAddedSession(sessionList, sessionIndex);
                // Update the saved data with the new session.
                updateSaveFile(user, sessionList, goalList, reminderList, foodWaterList);
            } catch (Exception e) {
                // Handle any exceptions during session addition and display error message.
                System.out.println(e.getMessage());
            }
            break;
        case MODIFY_SESSION_DATETIME_COMMAND:
            try {
                // Validate user input for modifying session datetime.
                String[] userInput = validModifySessionDateTime(description, sessionList.size());
                int sessionIndex = stringToValidInteger(userInput[0]) - 1;
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
                LocalDateTime newDateTime = LocalDateTime.parse(userInput[1], formatter);
                // Modify the session's datetime and print the updated session.
                sessionList.get(sessionIndex).setSessionDateTime(newDateTime);

                // Removing the modifiedSession, and reinserting it into the list so that it is in order by datetime
                TrainingSession modifiedSession = sessionList.remove(sessionIndex);
                int insertIndex = 0;
                while (insertIndex < sessionList.size() &&
                        LocalDateTime.parse(sessionList.get(insertIndex).getSessionDatetime(), formatter).
                                isBefore(newDateTime)) {
                    insertIndex++;
                }
                sessionList.add(insertIndex, modifiedSession);
                printModifiedSession(sessionList,sessionIndex + 1);
            } catch (Exception e) {
                // Handle any exceptions and print error message.
                System.out.println(e.getMessage());
            }
            break;
        case EDIT_EXERCISE_COMMAND:
            try {
                // Validate the user input for editing exercise details in a session.
                String[] userInput = validEditDetails(description,sessionList.size());
                int sessionIndex = stringToValidInteger(userInput[0]) - 1;
                String exerciseAcronym = userInput[1].trim().toUpperCase();
                String exerciseData = userInput[2].trim();
                // Edit the exercise in the specified session and print the updated session.
                sessionList.get(sessionIndex).editExercise(fromUserInput(exerciseAcronym), exerciseData);
                printSessionView(sessionList, sessionIndex);
                // Save the updated session data.
                updateSaveFile(user, sessionList, goalList, reminderList, foodWaterList);
            } catch (Exception e) {
                // Handle any exceptions and print error message.
                System.out.println(e.getMessage());
            }
            break;
        case LIST_SESSIONS_COMMAND:
            // If no description is provided, list all sessions. Otherwise, print an error message.
            if(description.isEmpty()) {
                printSessionList(sessionList); // Print the list of sessions
                break;
            }
            printInvalidListCommandMessage();
            break;
        case VIEW_SESSION_COMMAND:
            try {
                // Validate the session index and print the details of the specified session.
                int indexToView = validSessionIndex(description, sessionList.size());
                printSessionView(sessionList, indexToView); // Print the session view
            } catch (Exception e) {
                // Handle any exceptions and print error message.
                System.out.println(e.getMessage());
            }
            break;
        case DELETE_SESSION_COMMAND:
            try {
                // Validate the session index and delete the corresponding session.
                int indexToDelete = validSessionIndex(description, sessionList.size());
                TrainingSession sessionToDelete = sessionList.get(indexToDelete);
                String sessionDescription = sessionList.get(indexToDelete).getSessionDescription();
                // Remove the session from the list and print the deletion details.
                sessionList.remove(indexToDelete);
                printDeletedSession(sessionList, sessionToDelete, sessionDescription);
                // Save the updated session data.
                updateSaveFile(user, sessionList, goalList, reminderList, foodWaterList);
            } catch (Exception e) {
                // Handle any exceptions and print error message.
                System.out.println(e.getMessage());
            }
            break;

        case GRAPH_POINTS_COMMAND:
            description = description.trim();
            // If no description is given, print the point graph for all sessions;
            // otherwise, print based on the input.
            if(description.isEmpty()){
                printPointGraph(null, sessionList);
            } else {
                try {
                    printPointGraph(fromUserInput(description), sessionList);
                } catch (Exception e) {
                    // Handle any exceptions and print error message.
                    System.out.println(e.getMessage());
                }
            }
            break;

        case GRAPH_PERFORMANCE_COMMAND:
            try {
                // Print the performance graph for the session based on the user input.
                printPerformanceGraph(fromUserInput(description), sessionList);
            } catch (Exception e) {
                // Handle any exceptions and print error message.
                System.out.println(e.getMessage());
            }
            break;

        case ADD_REMINDER_COMMAND:
            try {
                String[] remindInfo = description.split("//", 2);

                // Check if sentence contains both description and deadline
                if (remindInfo.length < 2) {
                    throw new IllegalArgumentException("Input must contain a non-blank description and a deadline," +
                            " with '//' between them");
                }

                String inputDeadline = remindInfo[1].trim();
                String inputDescription = remindInfo[0].trim();

                if (inputDeadline.isEmpty() || inputDescription.isEmpty()) {
                    throw new IllegalArgumentException("Input must contain a non-blank description and a deadline, " +
                            "with '//' between them");
                }

                // Parse the deadline, handle any parsing errors
                LocalDateTime deadline;
                try {
                    deadline = parseDeadline(inputDeadline);
                } catch (DateTimeParseException e) {
                    throw new IllegalArgumentException(INVALID_DATETIME_MESSAGE, e);
                }

                // Proceed with adding the reminder if no exceptions were thrown
                reminderList.add(new Reminder(inputDescription, deadline, user));
                printAddedReminder(reminderList);
                updateSaveFile(user, sessionList, goalList, reminderList, foodWaterList);

            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            } catch (Exception e) {
                System.err.println("An unexpected error occurred: " + e.getMessage());
            }
            break;

        case DELETE_REMINDER_COMMAND:
            try {
                int reminderIndexToDelete;

                // Try parsing the index; handle invalid number formats
                try {
                    reminderIndexToDelete = Integer.parseInt(description) - 1;
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException(INVALID_INDEX_NON_NUMERIC_MESSAGE);
                }

                // Check if the index is within bounds
                if (reminderIndexToDelete < 0 || reminderIndexToDelete >= reminderList.size()) {
                    throw new IndexOutOfBoundsException(INDEX_OUT_OF_BOUNDS_MESSAGE);
                }

                // Retrieve and delete the reminder if the index is valid
                Reminder reminderToDelete = reminderList.get(reminderIndexToDelete);
                reminderList.remove(reminderIndexToDelete);
                printDeletedReminder(reminderList, reminderToDelete);
                updateSaveFile(user, sessionList, goalList, reminderList, foodWaterList);

            } catch (IllegalArgumentException | IndexOutOfBoundsException e) {
                System.out.println(e.getMessage());
            } catch (Exception e) {
                System.out.println("An unexpected error occurred: " + e.getMessage());
            }
            break;

        case LIST_REMINDER_COMMAND:
            // Print the list of all reminders.
            printReminderList(reminderList);
            break;

        case LIST_UPCOMING_REMINDER_COMMAND:
            // Print the list of upcoming reminders.
            printUpcomingReminders(reminderList);
            break;

        case ADD_GOAL_COMMAND:
            try {
                // Validate and add a new goal to the goal list, including handling deadline if provided.
                if (!description.isEmpty()) {
                    String[] goalParts = description.split(" ", 2);
                    String goalDescription = goalParts[0];
                    LocalDateTime goalDeadline = null;

                    if (goalParts.length > 1) {
                        String goalDeadlineInput = goalParts[1];
                        try {
                            goalDeadline = parseDeadline(goalDeadlineInput);
                        } catch (IllegalArgumentException e) {
                            System.out.println("Invalid date format: " + e.getMessage());
                            return;
                        }
                    }
                    Goal newGoal = new Goal(goalDescription, goalDeadline);
                    goalList.add(newGoal);
                    printAddedGoal(goalList);
                } else {
                    System.out.println("Please specify a goal to add.");
                }
                updateSaveFile(user, sessionList, goalList, reminderList, foodWaterList);
            } catch (DateTimeParseException e) {
                // Handle invalid date format for the goal deadline.
                System.out.println("Invalid date format for goal deadline. "
                    + "Please use 'dd/MM/yyyy HH:mm'.");
            } catch (IllegalArgumentException e) {
                // Handle invalid input or missing goal description.
                System.out.println(e.getMessage());
            }
            break;

        case DELETE_GOAL_COMMAND:
            try {
                // Validate the goal index and remove the goal from the list.
                int index = Integer.parseInt(description) - 1;
                if (index >= 0 && index < goalList.size()) {
                    goalList.remove(index);
                    System.out.println("Goal at index " + (index + 1) + " has been removed.");
                } else {
                    System.out.println("Invalid goal index.");
                }
            } catch (NumberFormatException e) {
                // Handle invalid format when parsing the goal index.
                System.out.println("Invalid format: Please specify a valid index to delete.");
            }
            updateSaveFile(user, sessionList, goalList, reminderList, foodWaterList);
            break;

        case LIST_GOAL_COMMAND:
            // Display all goals if available; otherwise, inform the user that there are no goals to show.
            if (goalList.isEmpty()) {
                System.out.println("No goals to display.");
            } else {
                System.out.println("Goals:");
                for (int i = 0; i < goalList.size(); i++) {
                    System.out.println((i + 1) + ". " + goalList.get(i));
                }
            }
            break;

        case ADD_WATER_COMMAND:
            // Check if description is empty or not a valid single numeral
            if (description.isEmpty() || !description.matches("\\d+")) {
                System.out.println("Invalid water format: Please use 'add-water' + (water in ml)");
                break;
            }

            int waterAmount = Integer.parseInt(description);

            foodWaterList.addWater(new WaterEntry(waterAmount, LocalDateTime.now()));

            System.out.println(SEPARATOR);
            System.out.println("Got it. I've added " + waterAmount + "ml of water at " +
                formattedTimeNow + ".");
            System.out.println(SEPARATOR);
            updateSaveFile(user, sessionList, goalList, reminderList, foodWaterList);
            break;

        case DELETE_WATER_COMMAND:
            // Check if description is empty or not a valid single numeral
            if (description.isEmpty() || !description.matches("\\d+")) {
                System.out.println("Invalid format: Please provide a valid water index number.");
                break;
            }

            int waterIndex = Integer.parseInt(description) - 1;
            foodWaterList.deleteWater(waterIndex);
            updateSaveFile(user, sessionList, goalList, reminderList, foodWaterList);
            break;

        case LIST_WATER_COMMAND:
            // Display all water entries if they exist; otherwise, print a message that there are no entries.
            System.out.println(SEPARATOR);
            foodWaterList.listDailyWaterIntake();
            System.out.println(SEPARATOR);
            break;

        case ADD_FOOD_COMMAND:
            // Attempt to parse the description for a valid food entry, which includes food type and calories.
            String[] foodParts = description.split(" ", 2); // Split description into parts
            if (foodParts.length > 1) { // Ensure there are both food name and calories
                String foodName = foodParts[0];
                try {
                    int calories = Integer.parseInt(foodParts[1].trim());
                    foodWaterList.addFood(new FoodEntry(foodName,calories,LocalDateTime.now()));
                    System.out.println(SEPARATOR);
                    System.out.println("Got it. I've added food item: " + foodName
                            + " (" + calories + " calories) at " + formattedTimeNow + ").");
                    System.out.println(SEPARATOR);
                    updateSaveFile(user, sessionList, goalList, reminderList, foodWaterList);
                } catch (NumberFormatException e) {
                    System.out.println("Invalid water format: Please use 'add-food' + (food item) "
                        + "+ (calories)");
                }
            } else {
                System.out.println("Invalid format: Please provide both food name and calories.");
            }
            break;

        case DELETE_FOOD_COMMAND:
            // Validate the input index for deletion; check if it is a number and within the
            // food list range.
            if (description.isEmpty() || !description.matches("\\d+")) {
                System.out.println("Invalid format: Please provide a valid food index number.");
                break;
            }

            int foodIndex = Integer.parseInt(description) - 1;
            foodWaterList.deleteFood(foodIndex);
            updateSaveFile(user, sessionList, goalList, reminderList, foodWaterList);
            break;

        case LIST_FOOD_COMMAND:
            // Display the list of all food entries if any are present; otherwise,
            // print that the list is empty.
            System.out.println(SEPARATOR);
            foodWaterList.listDailyFoodIntake();
            System.out.println(SEPARATOR);
            break;

        case EDIT_MOOD_COMMAND:
            // Edit the user's mood by updating the mood log with the description provided.
            String[] editMoodParts = description.split(" ", 2);
            if (editMoodParts.length < 2) {
                System.out.println("Please specify the session-ID and new mood");

                return;
            }
            try {
              
                // Parse session ID and new Mood String from provided user input
                int sessionId = validSessionIndex(editMoodParts[0], sessionList.size());
                String newMood = editMoodParts[1]; // New mood from the second part

                // Call the edit method with the necessary arguments
                TrainingSession sessionToEdit = sessionList.get(sessionId);
                sessionToEdit.setMood(newMood);
                printUpdatedMood(sessionId, newMood);

            } catch (NumberFormatException e) {
                System.out.println(INVALID_SESSION_INDEX_MESSAGE);
            } catch (Exception e) {
                System.out.println("An error occurred: " + e.getMessage());
            }
            updateSaveFile(user, sessionList, goalList, reminderList, foodWaterList);
            break;

        case LIST_DAILY_INTAKE_COMMAND:
            // Combine water, food, and calorie lists into one daily intake summary
            System.out.println("Here is your daily intake summary:");
            foodWaterList.listDailyIntake();
            break;

        default:
            printUnrecognizedInputMessage(); // Handle unrecognized commands
            break;
        }
    }
}
