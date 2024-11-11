package fittrack.parser;

import fittrack.fitnessgoal.Goal;
import fittrack.healthprofile.FoodWaterIntake;
import fittrack.reminder.Reminder;
import fittrack.trainingsession.TrainingSession;
import fittrack.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;

import static fittrack.messages.Messages.ADD_REMINDER_MESSAGE;
import static fittrack.messages.Messages.INVALID_DATETIME_MESSAGE;
import static fittrack.messages.Messages.SEPARATOR;
import static fittrack.parser.Parser.parse;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ParserReminderTest {

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
    void testAddReminderCommandValidMultiWordReminderInput() throws IOException {
        String input = "remind Workout as best as I can // 20/12/2023 10:00";
        parse(user, input, sessionList, reminderList, goalList, foodWaterList);

        assertEquals(1, reminderList.size()); // Assert reminder was added

        // Check parsed information was stored correctly
        assertEquals("Workout as best as I can", reminderList.get(0).getDescription());
        assertEquals("20/12/2023 10:00", reminderList.get(0).getDeadlineString());

        String expectedOutput = SEPARATOR + System.lineSeparator()
                + ADD_REMINDER_MESSAGE + System.lineSeparator()
                + "1. Workout as best as I can | 20/12/2023 10:00"  + System.lineSeparator()
                + "There are 1 reminders in your list." + System.lineSeparator()
                + SEPARATOR + System.lineSeparator();

        // Capture the actual output
        String actualOutput = outputStreamCaptor.toString().trim().replaceAll("\\r\\n?", "\n");

        // Normalize both the expected and actual output by removing extra line breaks or spaces
        expectedOutput = expectedOutput.trim().replaceAll("\\r\\n?", "\n");

        // Check console output is correct
        assertEquals(expectedOutput, actualOutput);
    }


    @Test
    void testAddReminderCommandValidBeforeSystemDateInput() throws IOException {
        String input = "remind Workout // 20/12/2023 10:00";
        parse(user, input, sessionList, reminderList, goalList, foodWaterList);

        assertEquals(1, reminderList.size()); // Assert reminder was added

        // Check parsed information was stored correctly
        assertEquals("Workout", reminderList.get(0).getDescription());
        assertEquals("20/12/2023 10:00", reminderList.get(0).getDeadlineString());

        String expectedOutput = SEPARATOR + System.lineSeparator()
                + ADD_REMINDER_MESSAGE + System.lineSeparator()
                + "1. Workout | 20/12/2023 10:00"  + System.lineSeparator()
                + "There are 1 reminders in your list." + System.lineSeparator()
                + SEPARATOR + System.lineSeparator();

        // Capture the actual output
        String actualOutput = outputStreamCaptor.toString().trim().replaceAll("\\r\\n?", "\n");

        // Normalize both the expected and actual output by removing extra line breaks or spaces
        expectedOutput = expectedOutput.trim().replaceAll("\\r\\n?", "\n");

        // Check console output is correct
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    void testAddReminderCommandValidAfterSystemDateInput() throws IOException {
        String input = "remind Do Something Fun // 31/12/2024 16:39";
        parse(user, input, sessionList, reminderList, goalList, foodWaterList);

        assertEquals(1, reminderList.size()); // Assert reminder was added

        // Check parsed information was stored correctly
        assertEquals("Do Something Fun", reminderList.get(0).getDescription());
        assertEquals("31/12/2024 16:39", reminderList.get(0).getDeadlineString());

        String expectedOutput = SEPARATOR + System.lineSeparator()
                + ADD_REMINDER_MESSAGE + System.lineSeparator()
                + "1. Do Something Fun | 31/12/2024 16:39"  + System.lineSeparator()
                + "There are 1 reminders in your list." + System.lineSeparator()
                + SEPARATOR + System.lineSeparator();

        // Capture the actual output
        String actualOutput = outputStreamCaptor.toString().trim().replaceAll("\\r\\n?", "\n");

        // Normalize both the expected and actual output by removing extra line breaks or spaces
        expectedOutput = expectedOutput.trim().replaceAll("\\r\\n?", "\n");

        // Check console output is correct
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    void testAddReminderCommandInvalidDateTime() throws IOException {
        String input = "remind Workout // invalid_date";

        parse(user, input, sessionList, reminderList, goalList, foodWaterList);
        assertEquals(INVALID_DATETIME_MESSAGE, outputStreamCaptor.toString().trim());
    }

    @Test
    void testAddReminderCommandMissingInformation() throws IOException {
        String[] input = { "remind Workout // ",        // Missing Deadline information
                           "remind // 25/12/2024",      // Missing Description information
                           "remind // ",      // Missing Deadline and Description information
                           "remind "                    // Missing '//' marker, Description and  Deadline information
        };

        for (String s : input) {
            parse(user, s, sessionList, reminderList, goalList, foodWaterList);

            // Normalize the output to avoid newline issues
            String normalizedOutput = outputStreamCaptor.toString().trim().replaceAll("\\r\\n?", "\n");

            assertEquals("Input must contain a non-blank description and a deadline, with '//' between them",
                    normalizedOutput);

            outputStreamCaptor.reset();
        }
    }

}
