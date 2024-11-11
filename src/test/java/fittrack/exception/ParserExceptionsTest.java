package fittrack.exception;

import fittrack.trainingsession.TrainingSession;
import fittrack.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static fittrack.messages.Messages.FEMALE_GENDER;
import static fittrack.messages.Messages.INVALID_EXERCISE_ACRONYM_MESSAGE;
import static fittrack.messages.Messages.INVALID_MODIFY_DETAILS_MESSAGE;
import static fittrack.messages.Messages.INVALID_PULL_UP_REPETITIONS_MESSAGE;
import static fittrack.messages.Messages.INVALID_SESSION_INDEX_MESSAGE;
import static fittrack.messages.Messages.INVALID_SESSION_NAME_MESSAGE;
import static fittrack.messages.Messages.INVALID_SHUTTLE_RUN_TIMING_MESSAGE;
import static fittrack.messages.Messages.INVALID_USER_INFO_MESSAGE;
import static fittrack.messages.Messages.INVALID_WALK_AND_RUN_TIMING_MESSAGE;
import static fittrack.messages.Messages.MALE_GENDER;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ParserExceptionsTest {
    public static final int SESSION_LIST_SIZE = 5;
    private User testUser;
    private TrainingSession session;

    @BeforeEach
    public void setUp() {
        testUser = new User("MALE", "20");
        session = new TrainingSession(java.time.LocalDateTime.now(), "Sample Session", testUser);
    }

    @Test
    public void testValidShuttleRunTiming() {
        // Valid case: Shuttle Run time in decimal format
        String[] result = ParserExceptions.validEditDetails("1 SR 11.2", SESSION_LIST_SIZE);
        assertEquals("1", result[0]);
        assertEquals("SR", result[1]);
        assertEquals("11.2", result[2]);

        // Invalid case: Shuttle Run with invalid decimal format
        Exception exception1 = assertThrows(IllegalArgumentException.class, () ->
                ParserExceptions.validEditDetails("1 SR 11.25", SESSION_LIST_SIZE));
        assertTrue(exception1.getMessage().contains(INVALID_SHUTTLE_RUN_TIMING_MESSAGE));

        // Invalid case: Shuttle Run with invalid decimal format
        Exception exception2 = assertThrows(IllegalArgumentException.class, () ->
                ParserExceptions.validEditDetails("1 SR 11.2.5", SESSION_LIST_SIZE));
        assertTrue(exception2.getMessage().contains(INVALID_SHUTTLE_RUN_TIMING_MESSAGE));

        // Invalid case: Non-numeric input for Shuttle Run
        Exception exception3 = assertThrows(IllegalArgumentException.class, () ->
                ParserExceptions.validEditDetails("1 SR abc", SESSION_LIST_SIZE));
        assertTrue(exception3.getMessage().contains(INVALID_SHUTTLE_RUN_TIMING_MESSAGE));
    }

    @Test
    public void testValidWalkAndRunTiming() {
        // Valid case: Walk and Run time in HH:MM format
        String[] result = ParserExceptions.validEditDetails("2 WAR 12:45", SESSION_LIST_SIZE);
        assertEquals("2", result[0]);
        assertEquals("WAR", result[1]);
        assertEquals("12:45", result[2]);

        // Invalid case: Walk and Run with invalid minutes (greater than 59)
        Exception exception1 = assertThrows(IllegalArgumentException.class, () ->
                ParserExceptions.validEditDetails("2 WAR 12:1234", SESSION_LIST_SIZE));
        assertTrue(exception1.getMessage().contains(INVALID_WALK_AND_RUN_TIMING_MESSAGE));

        // Invalid case: Walk and Run with more than one colon
        Exception exception2 = assertThrows(IllegalArgumentException.class, () ->
                ParserExceptions.validEditDetails("2 WAR 12:30:45", SESSION_LIST_SIZE));
        assertTrue(exception2.getMessage().contains(INVALID_WALK_AND_RUN_TIMING_MESSAGE));

        // Invalid case: Non-numeric input for Walk and Run
        Exception exception3 = assertThrows(IllegalArgumentException.class, () ->
                ParserExceptions.validEditDetails("2 WAR abc", SESSION_LIST_SIZE));
        assertTrue(exception3.getMessage().contains(INVALID_WALK_AND_RUN_TIMING_MESSAGE));
    }

    @Test
    public void testInvalidSessionIndex() {
        // Test invalid session index outside of range
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                ParserExceptions.validEditDetails("6 SR 11.2", SESSION_LIST_SIZE)); // Index 6 is out of range
        assertTrue(exception.getMessage().contains(INVALID_SESSION_INDEX_MESSAGE));
    }

    // Test for parseUserInfo
    @Test
    public void parseUserInfo_validInput_returnsUserInfo() {
        String[] userInfo = ParserExceptions.parseUserInfo("John 25");
        assertEquals(2, userInfo.length);
        assertEquals("John", userInfo[0]);
        assertEquals("25", userInfo[1]);
    }

    @Test
    public void parseUserInfo_invalidInput_throwsException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                ParserExceptions.parseUserInfo("InvalidInput"));
        assertEquals(INVALID_USER_INFO_MESSAGE, exception.getMessage());
    }

    // Test for validUser
    @Test
    public void validUser_validGenderAndAge_noExceptionThrown() {
        assertDoesNotThrow(() -> ParserExceptions.validUser(MALE_GENDER, "18"));
        assertDoesNotThrow(() -> ParserExceptions.validUser(FEMALE_GENDER, "22"));
    }

    @Test
    public void validUser_invalidGender_throwsException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                ParserExceptions.validUser("Other", "18"));
        assertEquals(INVALID_USER_INFO_MESSAGE, exception.getMessage());
    }

    @Test
    public void validUser_invalidAge_throwsException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                ParserExceptions.validUser(MALE_GENDER, "10"));
        assertEquals(INVALID_USER_INFO_MESSAGE, exception.getMessage());
    }

    // Test for validSession
    @Test
    public void validSession_validDescription_returnsTrainingSession() {
        TrainingSession session = ParserExceptions.validSession("Session1", testUser);
        assertEquals("Session1", session.getSessionDescription());
    }

    @Test
    public void validSession_emptyDescription_throwsException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                ParserExceptions.validSession(" ", testUser));
        assertEquals(INVALID_SESSION_NAME_MESSAGE, exception.getMessage());
    }

    // Test for validSessionIndex
    @Test
    public void validSessionIndex_validInput_returnsIndex() {
        int index = ParserExceptions.validSessionIndex("1", 5);
        assertEquals(0, index); // Index is zero-based
    }

    @Test
    public void validSessionIndex_invalidInput_throwsException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                ParserExceptions.validSessionIndex("10", 5));
        assertEquals(INVALID_SESSION_INDEX_MESSAGE, exception.getMessage());
    }

    // Test for validModifySessionDateTime
    @Test
    public void validModifySessionDateTime_validInput_returnsModifyDetails() {
        String[] modifyDetails = ParserExceptions.validModifySessionDateTime("1 12/12/2023 12:00", 5);
        assertEquals("1", modifyDetails[0]);
        assertEquals("12/12/2023 12:00", modifyDetails[1]);
    }

    @Test
    public void validModifySessionDateTime_invalidFormat_throwsException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                ParserExceptions.validModifySessionDateTime("1 12-12-2023 12:00", 5));
        assertEquals(INVALID_MODIFY_DETAILS_MESSAGE, exception.getMessage());
    }

    // Test for validEditDetails
    @Test
    public void validEditDetails_validInput_returnsEditDetails() {
        String[] editDetails = ParserExceptions.validEditDetails("1 PU 15", 5);
        assertEquals("1", editDetails[0]);
        assertEquals("PU", editDetails[1]);
        assertEquals("15", editDetails[2]);
    }

    @Test
    public void validEditDetails_invalidExerciseAcronym_throwsException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                ParserExceptions.validEditDetails("1 XYZ 15", 5));
        assertTrue(exception.getMessage().contains(INVALID_EXERCISE_ACRONYM_MESSAGE));
    }

    @Test
    public void validEditDetails_invalidExerciseData_throwsException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                ParserExceptions.validEditDetails("1 PU -1", 5));
        assertTrue(exception.getMessage().contains(INVALID_PULL_UP_REPETITIONS_MESSAGE));
    }

    // Test for stringToValidInteger
    @Test
    public void stringToValidInteger_validString_returnsInteger() {
        assertEquals(10, ParserExceptions.stringToValidInteger("10"));
    }

    @Test
    public void stringToValidInteger_invalidString_returnsNegativeOne() {
        assertEquals(-1, ParserExceptions.stringToValidInteger("invalid"));
    }
}

