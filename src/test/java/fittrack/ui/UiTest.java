package fittrack.ui;

import fittrack.enums.Gender;
import fittrack.trainingsession.TrainingSession;
import fittrack.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static fittrack.messages.Messages.ADD_SESSION_MESSAGE;
import static fittrack.messages.Messages.DELETE_SESSION_MESSAGE;
import static fittrack.messages.Messages.EXIT_MESSAGE;
import static fittrack.messages.Messages.INIT_SENTENCE;
import static fittrack.messages.Messages.INVALID_INPUT_MESSAGE;
import static fittrack.messages.Messages.INVALID_LIST_COMMAND_MESSAGE;
import static fittrack.messages.Messages.LIST_SESSION_EMPTY_MESSAGE;
import static fittrack.messages.Messages.LIST_SESSION_MESSAGE;
import static fittrack.messages.Messages.SEPARATOR;
import static fittrack.ui.Ui.printUser;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDateTime;
import java.util.ArrayList;


public class UiTest {

    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor)); // Redirects System.out
    }

    @Test
    public void testPrintGreeting() {
        Ui.printGreeting();
        assertEquals(SEPARATOR + System.lineSeparator() + INIT_SENTENCE + System.lineSeparator() + SEPARATOR
                + System.lineSeparator() + System.lineSeparator(), outputStreamCaptor.toString());
    }

    @Test
    public void testPrintUser() {
        User user = new User(Gender.MALE.toString(), "12");
        printUser(user.getAge(), user.getGender().toString().toLowerCase());
        assertEquals(SEPARATOR + System.lineSeparator() + "You are a 12 year old male."
                + System.lineSeparator() + SEPARATOR + System.lineSeparator() + System.lineSeparator(),
                outputStreamCaptor.toString());
    }

    @Test
    public void testPrintAddedSession() {
        User user = new User(Gender.MALE.toString(), "12");
        ArrayList<TrainingSession> sessions = new ArrayList<>();
        TrainingSession session = new TrainingSession(LocalDateTime.now(), "test1", user);
        sessions.add(session);
        Ui.printAddedSession(sessions, sessions.size() - 1);
        assertEquals(SEPARATOR + System.lineSeparator() + ADD_SESSION_MESSAGE + System.lineSeparator()
                + sessions.size() + ". " + session.getSessionDescription() + " | " + session.getSessionDatetime()
                + System.lineSeparator() + "There are 1 sessions in the list." + System.lineSeparator()
                + SEPARATOR + System.lineSeparator() + System.lineSeparator(), outputStreamCaptor.toString());
    }

    @Test
    public void testPrintDeletedSession() {
        User user = new User(Gender.MALE.toString(), "12");
        ArrayList<TrainingSession> sessions = new ArrayList<>();
        TrainingSession session = new TrainingSession(LocalDateTime.now(), "test1", user);
        sessions.add(session);
        Ui.printDeletedSession(sessions, session, session.getSessionDescription());
        assertEquals(SEPARATOR + System.lineSeparator() + DELETE_SESSION_MESSAGE
                + session.getSessionDescription() + System.lineSeparator() + "There are 1 sessions in the list."
                + System.lineSeparator() + SEPARATOR + System.lineSeparator() + System.lineSeparator(),
                outputStreamCaptor.toString());
    }

    @Test
    public void testPrintSessionListEmpty() {
        ArrayList<TrainingSession> sessions = new ArrayList<>();
        Ui.printSessionList(sessions);
        assertEquals(SEPARATOR + System.lineSeparator() + LIST_SESSION_EMPTY_MESSAGE + System.lineSeparator()
                + SEPARATOR + System.lineSeparator()+ System.lineSeparator(), outputStreamCaptor.toString());
    }

    @Test
    public void testPrintSessionListNonEmpty() {
        User user = new User(Gender.MALE.toString(), "12");
        ArrayList<TrainingSession> sessions = new ArrayList<>();
        TrainingSession session = new TrainingSession(LocalDateTime.now(), "test1", user);
        sessions.add(session);
        Ui.printSessionList(sessions);
        assertEquals(SEPARATOR + System.lineSeparator() + LIST_SESSION_MESSAGE + System.lineSeparator() + "1. "
                + session.getSessionDescription() + " | " + session.getSessionDatetime() + System.lineSeparator()
                + "There are 1 sessions in the list." + System.lineSeparator() + SEPARATOR + System.lineSeparator()
                + System.lineSeparator(), outputStreamCaptor.toString());
    }

    @Test
    public void testPrintInvalidListCommandMessage() {
        Ui.printInvalidListCommandMessage();
        assertEquals(SEPARATOR + System.lineSeparator() + INVALID_LIST_COMMAND_MESSAGE + System.lineSeparator()
                + SEPARATOR + System.lineSeparator() + System.lineSeparator(), outputStreamCaptor.toString());
    }

    @Test
    public void testPrintSessionViewIndexOutOfBounds() {
        ArrayList<TrainingSession> sessions = new ArrayList<>();
        TrainingSession session = new TrainingSession(LocalDateTime.now(), "Session Description",
                new User("Male", "20"));
        sessions.add(session);

        // Test for an index that is out of bounds
        try {
            Ui.printSessionView(sessions, 1);
        } catch (AssertionError e) {
            assertEquals("Index is out of bounds", e.getMessage());
        }
    }

    @Test
    public void testPrintUnrecognizedInputMessage() {
        Ui.printUnrecognizedInputMessage();
        assertEquals(SEPARATOR + System.lineSeparator() + INVALID_INPUT_MESSAGE + System.lineSeparator()
                + SEPARATOR + System.lineSeparator() + System.lineSeparator(), outputStreamCaptor.toString());
    }

    @Test
    public void testPrintExitMessage() {
        Ui.printExitMessage();
        assertEquals(SEPARATOR + System.lineSeparator() + EXIT_MESSAGE + System.lineSeparator() + SEPARATOR
                + System.lineSeparator() + System.lineSeparator(), outputStreamCaptor.toString());
    }
}
