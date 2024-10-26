package fittrack;

import fittrack.fitnessgoal.Goal;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import fittrack.parser.Parser;
import fittrack.reminder.Reminder;
import fittrack.trainingsession.TrainingSession;
import fittrack.user.User;

    import static fittrack.logger.FitTrackLogger.setupLogger;
    import static fittrack.messages.Messages.EXIT_COMMAND;
    import static fittrack.storage.Storage.initialiseSaveFile;
    import static fittrack.storage.Storage.loadSaveFile;
    import static fittrack.ui.Ui.printExitMessage;
    import static fittrack.ui.Ui.printGreeting;
    import static fittrack.ui.Ui.printHelp;
    import static fittrack.ui.Ui.printUpcomingReminders;
    import static fittrack.ui.Ui.printUser;


public class FitTrack {

    /**
     * Helper method to check if a string is numeric.
     *
     * @param str The string to check.
     * @return true if the string is numeric, false otherwise.
     */
    public static boolean isNumeric(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static void main(String[] args) throws FileNotFoundException {
        setupLogger();

        // Initialize scanner and session / reminder list
        Scanner scan = new Scanner(System.in);
        ArrayList<TrainingSession> sessionList = new ArrayList<>();
        ArrayList<Reminder> reminderList = new ArrayList<>();
        ArrayList<Goal> goalList = new ArrayList<>();

        // Initialize and load the save file
        initialiseSaveFile();
        loadSaveFile(sessionList);

        // Set user gender and age
        printGreeting();
        String[] userInfo = scan.nextLine().split(" ", 2);

        // Assert user info is valid
        assert userInfo.length == 2 : "User info should contain both gender and age";
        String gender = userInfo[0];
        String age = userInfo[1];

        // Assert that age is a valid integer
        assert isNumeric(age) : "Age should be a valid integer";

        User user = new User(gender, age);
        printUser(user);
        printHelp();

        // Inform users of upcoming reminders
        printUpcomingReminders(reminderList);

        String input = scan.nextLine();

        // Until the exit command is entered, execute command then read user input
        while (!input.equals(EXIT_COMMAND)) {
            assert !input.trim().isEmpty() : "User input should not be null or empty";
            Parser.parse(user, input, sessionList, reminderList, goalList);
            input = scan.nextLine();
        }
        printExitMessage();
    }
}
