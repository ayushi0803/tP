package fittrack.parser;

import fittrack.user.User;
import fittrack.healthprofile.FoodWaterIntake;
import fittrack.trainingsession.TrainingSession;
import fittrack.reminder.Reminder;
import fittrack.fitnessgoal.Goal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import static fittrack.messages.Messages.HELP_MESSAGE;
import static fittrack.messages.Messages.INVALID_INPUT_MESSAGE;
import static fittrack.messages.Messages.INVALID_USER_INFO_MESSAGE;
import static fittrack.messages.Messages.INVALID_VERTICAL_BAR_INPUT_MESSAGE;
import static fittrack.messages.Messages.SEPARATOR;
import static fittrack.parser.Parser.parse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ParserTest {
    private User user;
    private ArrayList<TrainingSession> sessionList;
    private ArrayList<Reminder> reminderList;
    private ArrayList<Goal> goalList;
    private FoodWaterIntake foodWaterList;

    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    void setUp() {
        user = new User("Male", "20");
        sessionList = new ArrayList<>();
        reminderList = new ArrayList<>();
        goalList = new ArrayList<>();
        foodWaterList = new FoodWaterIntake();

        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @Test
    void testHelpCommand() throws IOException {
        String input = "help";
        String expectedOutput = SEPARATOR + System.lineSeparator()
                + HELP_MESSAGE + System.lineSeparator()
                + SEPARATOR + System.lineSeparator() + System.lineSeparator();

        parse(user, input, sessionList, reminderList, goalList, foodWaterList);
        assertEquals(expectedOutput, outputStreamCaptor.toString());
    }

    @Test
    void testSetUserCommandValidInput() throws IOException {
        String input = "set male 24";
        parse(user, input, sessionList, reminderList, goalList, foodWaterList);
        assertEquals(24, user.getAge());
        assertEquals("MALE", user.getGender().toString());
        String test = user.toSaveString();
        assertEquals("User|MALE|24", user.toSaveString());
    }

    @Test
    public void testModifySessionDateTimeNoReorder() throws IOException {
        // Modify the datetime of a session to a value that doesn't change its order
        String input = "modify 2 11/11/2024 11:10"; // Modify session at index 2 (original datetime 2024-11-12 11:00)

        Parser.parse(user, input, sessionList, new ArrayList<>(), new ArrayList<>(), new FoodWaterIntake());

        // Verify the session list order is unchanged
        for (int i = 0; i < sessionList.size(); i++) {
            assertEquals("Test Session " + (i + 1), sessionList.get(i).getSessionDescription());
            // Verify that the datetime has been updated
            if (i == 1) {
                assertEquals("11/11/2024 11:10", sessionList.get(i).getSessionDatetime());
            }
        }
    }

    @Test
    void testSetUserCommandInvalidInput() throws IOException {
        String input = "set";
        String expectedOutput = INVALID_USER_INFO_MESSAGE;

        parse(user, input, sessionList, reminderList, goalList, foodWaterList);

        // Capture the actual output
        String actualOutput = outputStreamCaptor.toString().trim().replaceAll("\\r\\n?", "\n");

        // Normalize both the expected and actual output by removing extra line breaks or spaces
        expectedOutput = expectedOutput.trim().replaceAll("\\r\\n?", "\n");

        // Check console output is correct
        assertEquals(expectedOutput, actualOutput);
    }


    @Test
    public void testModifySessionDateTimeReorder() throws IOException {

        LocalDateTime startDateTime = LocalDateTime.parse("11/11/2024 10:00",
                DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));

        // Create 5 sessions with chronologically increasing datetime values
        for (int i = 0; i < 5; i++) {
            LocalDateTime sessionDateTime = startDateTime.plusHours(i);  // Increment by 1 hour for each session
            TrainingSession session =
                    new TrainingSession(sessionDateTime, "Test Session " + (i + 1), user);
            sessionList.add(session);
        }

        // Modify the datetime of a session to make it fall earlier (to trigger reordering)
        String input = "modify 3 10/11/2024 10:00";

        Parser.parse(user, input, sessionList, reminderList, goalList, foodWaterList);

        // Verify the order of session datetime after modification
        String expectedFirstSessionDatetime = "10/11/2024 10:00";
        String expectedSecondSessionDatetime = "11/11/2024 10:00";

        assertEquals(expectedFirstSessionDatetime, sessionList.get(0).getSessionDatetime());
        assertEquals(expectedSecondSessionDatetime, sessionList.get(1).getSessionDatetime());
    }

    @Test
    void testAddSessionCommandValidInput() {
    }

    @Test
    void testDeleteSessionCommandValidIndex() {

    }


    @Test
    void testDeleteSessionCommandInvalidIndex() {

    }

    @Test
    void testGraphPerformanceCommandValidInput() {

    }

    @Test
    void testListGoalCommandEmptyGoalList() {

    }

    @Test
    void testAddWaterCommandValidInput() {

    }

    @Test
    void testAddFoodCommandValidInput() {

    }

    @Test
    void testInvalidCommand() throws IOException {
        String input = "unknown-command";
        parse(user, input, sessionList, reminderList, goalList, foodWaterList);

        String expectedOutput = SEPARATOR + System.lineSeparator()
                + INVALID_INPUT_MESSAGE + System.lineSeparator() +
                SEPARATOR + System.lineSeparator();

        // Capture the actual output
        String actualOutput = outputStreamCaptor.toString().trim().replaceAll("\\r\\n?", "\n");

        // Normalize both the expected and actual output by removing extra line breaks or spaces
        expectedOutput = expectedOutput.trim().replaceAll("\\r\\n?", "\n");

        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    void testInvalidCommandWithVerticalBars() {

        String[] invalidInputs = {"add TEST|",
                                  "remind TEST| // 11/11/1111",
                                  "add-food app|le 100",
                                  "edit-mood happ|y"
        };

        for (String input : invalidInputs) {
            Exception exception = assertThrows(IOException.class, () -> {
                // This method should throw YourException with a specific message
                parse(user, input, sessionList, reminderList, goalList, foodWaterList);
            });

            String actualMessage = exception.getMessage();
            assertTrue(actualMessage.contains(INVALID_VERTICAL_BAR_INPUT_MESSAGE));
        }
    }
}

