package fittrack;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import fittrack.healthprofile.FoodEntry;
import fittrack.healthprofile.FoodWaterIntake;
import fittrack.healthprofile.WaterEntry;
import fittrack.parser.Parser;
import fittrack.reminder.Reminder;
import fittrack.storage.Saveable;
import fittrack.trainingsession.TrainingSession;
import fittrack.user.User;

import static fittrack.exception.ParserExceptions.parseUserInfo;
import static fittrack.exception.ParserExceptions.validUser;
import static fittrack.logger.FitTrackLogger.setupLogger;
import static fittrack.messages.Messages.EXIT_COMMAND;
import static fittrack.storage.Storage.initialiseSaveFile;
import static fittrack.storage.Storage.loadSaveFile;
import static fittrack.storage.Storage.updateSaveFile;
import static fittrack.ui.Ui.printExitMessage;
import static fittrack.ui.Ui.printGreeting;
import static fittrack.ui.Ui.printHelp;
import static fittrack.ui.Ui.printUpcomingReminders;
import static fittrack.ui.Ui.printUser;
import fittrack.fitnessgoal.Goal;

public class FitTrack {
    /**
     * Main entry-point for the FitTrack CLI application.
     * Initializes and loads data, sets up the user interface,
     * and listens for user input to execute commands.
     */
    public static void main(String[] args) throws IOException {
        setupLogger(); // Set up the logger for tracking application logs

        // Initialize scanner and unified saveable-item list
        Scanner scan = new Scanner(System.in);
        ArrayList<Saveable> saveableList = new ArrayList<>();

        // Initialize and load the save file
        initialiseSaveFile();
        loadSaveFile(saveableList);

        // Initialize separate Goal/Reminder/Training Session lists for easier access
        ArrayList<TrainingSession> sessionList = new ArrayList<>();
        ArrayList<Reminder> reminderList = new ArrayList<>();
        ArrayList<Goal> goalList = new ArrayList<>();

        // Initialise Food/Water lists
        FoodWaterIntake foodWaterList = new FoodWaterIntake();

        // Set user gender and age
        User user = null;

        // Separate saveable items into specific lists based on their type / set user if data is found detected
        for (Saveable item : saveableList) {
            if (item instanceof User) {
                user = (User) item;
            } else if (item instanceof TrainingSession) {
                sessionList.add((TrainingSession) item);
            } else if (item instanceof Reminder) {
                reminderList.add((Reminder) item);
            } else if (item instanceof Goal) {
                goalList.add((Goal) item);
            } else if (item instanceof WaterEntry) {
                foodWaterList.addWater((WaterEntry) item);
            } else if (item instanceof FoodEntry) {
                foodWaterList.addFood((FoodEntry) item);
            }
        }


        // If no user data detected, prompt user for user information
        while(user == null) {
            printGreeting();
            try {
                String userInput = scan.nextLine();
                if(userInput.trim().equals(EXIT_COMMAND)) {
                    printExitMessage();
                    return;
                }
                String[] userInfo = parseUserInfo(userInput);
                validUser(userInfo[0].toLowerCase(), userInfo[1]);
                user = new User(userInfo[0].toLowerCase(), userInfo[1]);
                printUser(user.getAge(), user.getGender().toString().toLowerCase());
                updateSaveFile(user, sessionList, goalList, reminderList,  foodWaterList);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        printHelp();

        // Inform users of upcoming reminders
        printUpcomingReminders(reminderList);

        String input = scan.nextLine();

        // Until the exit command is entered, execute command then read user input
        while (!input.toLowerCase().trim().equals(EXIT_COMMAND)) {
            Parser.parse(user, input, sessionList, reminderList, goalList, foodWaterList);
            input = scan.nextLine();
        }

        printExitMessage();
    }
}
