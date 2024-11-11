package fittrack.exception;

import fittrack.trainingsession.TrainingSession;
import fittrack.healthprofile.FoodEntry;
import fittrack.user.User;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import static fittrack.enums.Exercise.isValidExercise;
import static fittrack.messages.Messages.EXAMPLE_PULL_UP_REPETITIONS_FORMAT;
import static fittrack.messages.Messages.EXAMPLE_SHUTTLE_RUN_TIMING_FORMAT;
import static fittrack.messages.Messages.EXAMPLE_SIT_AND_REACH_DISTANCE_FORMAT;
import static fittrack.messages.Messages.EXAMPLE_SIT_UP_REPETITIONS_FORMAT;
import static fittrack.messages.Messages.EXAMPLE_STANDING_BROAD_JUMP_DISTANCE_FORMAT;
import static fittrack.messages.Messages.EXAMPLE_WALK_AND_RUN_TIMING_FORMAT;
import static fittrack.messages.Messages.FEMALE_GENDER;
import static fittrack.messages.Messages.INVALID_EXERCISE_ACRONYM_MESSAGE;
import static fittrack.messages.Messages.INVALID_EXERCISE_DETAILS_MESSAGE;
import static fittrack.messages.Messages.INVALID_MODIFY_DETAILS_MESSAGE;
import static fittrack.messages.Messages.INVALID_PULL_UP_REPETITIONS_MESSAGE;
import static fittrack.messages.Messages.INVALID_SESSION_INDEX_MESSAGE;
import static fittrack.messages.Messages.INVALID_SESSION_NAME_MESSAGE;
import static fittrack.messages.Messages.INVALID_SHUTTLE_RUN_TIMING_MESSAGE;
import static fittrack.messages.Messages.INVALID_SIT_AND_REACH_DISTANCE_MESSAGE;
import static fittrack.messages.Messages.INVALID_SIT_UP_REPETITIONS_MESSAGE;
import static fittrack.messages.Messages.INVALID_STANDING_BROAD_JUMP_DISTANCE_MESSAGE;
import static fittrack.messages.Messages.INVALID_USER_INFO_MESSAGE;
import static fittrack.messages.Messages.INVALID_WALK_AND_RUN_TIMING_MESSAGE;
import static fittrack.messages.Messages.MALE_GENDER;
import static fittrack.messages.Messages.PULL_UP_ACRONYM;
import static fittrack.messages.Messages.SHUTTLE_RUN_ACRONYM;
import static fittrack.messages.Messages.SIT_AND_REACH_ACRONYM;
import static fittrack.messages.Messages.SIT_UP_ACRONYM;
import static fittrack.messages.Messages.STANDING_BROAD_JUMP_ACRONYM;
import static fittrack.messages.Messages.WALK_AND_RUN_ACRONYM;

public class ParserExceptions extends RuntimeException {

    public static String[] parseUserInfo(String userInput) {
        if (!userInput.contains(" ")) {
            throw new IllegalArgumentException(INVALID_USER_INFO_MESSAGE);
        }
        String[] userInfo = userInput.split(" ");
        if (userInfo.length != 2) {
            throw new IllegalArgumentException(INVALID_USER_INFO_MESSAGE);
        }
        return userInfo;
    }

    public static void validUser(String gender, String age) {
        if (!(gender.equals(MALE_GENDER) || gender.equals(FEMALE_GENDER))) {
            throw new IllegalArgumentException(INVALID_USER_INFO_MESSAGE);
        }
        if (stringToValidInteger(age) < 12 || stringToValidInteger(age) > 24) {
            throw new IllegalArgumentException(INVALID_USER_INFO_MESSAGE);
        }
    }

    public static TrainingSession validSession(String description, User user) {
        if (description.trim().isEmpty()) {
            throw new IllegalArgumentException(INVALID_SESSION_NAME_MESSAGE);
        }
        return new TrainingSession(LocalDateTime.now(), description, user);
    }

    public static int validSessionIndex(String description, int sessionListSize) {
        if (description.isEmpty()) {
            throw new IllegalArgumentException(INVALID_SESSION_INDEX_MESSAGE);
        }
        int indexToDelete = stringToValidInteger(description) - 1;
        if (indexToDelete < 0 || indexToDelete >= sessionListSize) {
            throw new IllegalArgumentException(INVALID_SESSION_INDEX_MESSAGE);
        }
        return indexToDelete;
    }

    public static String[] validModifySessionDateTime(String description, int sessionListSize)
            throws DateTimeParseException {
        if (!description.contains(" ")) {
            throw new IllegalArgumentException(INVALID_MODIFY_DETAILS_MESSAGE);
        }

        String[] modifyDetails = description.split(" ", 2);

        if (modifyDetails.length != 2) {
            throw new IllegalArgumentException(INVALID_MODIFY_DETAILS_MESSAGE);
        }

        for (String editDetail : modifyDetails) {
            if (editDetail.trim().isEmpty()) {
                throw new IllegalArgumentException(INVALID_MODIFY_DETAILS_MESSAGE);
            }
        }

        int sessionIndex = stringToValidInteger(modifyDetails[0]) -1;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        try {
            LocalDateTime.parse(modifyDetails[1], formatter);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException(INVALID_MODIFY_DETAILS_MESSAGE);
        }

        if (sessionIndex < 0 || sessionIndex >= sessionListSize) {
            throw new IllegalArgumentException(INVALID_SESSION_INDEX_MESSAGE);
        }

        return modifyDetails;
    }

    public static String[] validEditDetails(String description, int sessionListSize)
            throws IllegalArgumentException, NullPointerException {
        if (!description.contains(" ")) {
            throw new IllegalArgumentException(INVALID_EXERCISE_DETAILS_MESSAGE);
        }

        String[] editDetails = description.split(" ", 3);

        if (editDetails.length != 3) {
            throw new IllegalArgumentException(INVALID_EXERCISE_DETAILS_MESSAGE);
        }

        for (String editDetail : editDetails) {
            if (editDetail.trim().isEmpty()) {
                throw new IllegalArgumentException(INVALID_EXERCISE_DETAILS_MESSAGE);
            }
        }

        int sessionIndex = stringToValidInteger(editDetails[0]) - 1;
        String exerciseAcronym = editDetails[1].trim().toUpperCase();
        String exerciseData = editDetails[2].trim();

        if (sessionIndex < 0 || sessionIndex >= sessionListSize) {
            throw new IllegalArgumentException(INVALID_SESSION_INDEX_MESSAGE);
        }

        if (!isValidExercise(exerciseAcronym)) {
            throw new IllegalArgumentException(INVALID_EXERCISE_ACRONYM_MESSAGE + exerciseAcronym);
        }

        switch (exerciseAcronym) {
        case PULL_UP_ACRONYM:
            if (stringToValidInteger(exerciseData) == -1) {
                throw new IllegalArgumentException(INVALID_PULL_UP_REPETITIONS_MESSAGE + exerciseData
                        + System.lineSeparator() + EXAMPLE_PULL_UP_REPETITIONS_FORMAT);
            }
            break;
        case SHUTTLE_RUN_ACRONYM:
            if(!exerciseData.matches("\\d+\\.\\d")){
                throw new IllegalArgumentException(INVALID_SHUTTLE_RUN_TIMING_MESSAGE + exerciseData
                        + System.lineSeparator() + EXAMPLE_SHUTTLE_RUN_TIMING_FORMAT);
            }
            break;
        case SIT_AND_REACH_ACRONYM:
            if (stringToValidInteger(exerciseData) == -1) {
                throw new IllegalArgumentException(INVALID_SIT_AND_REACH_DISTANCE_MESSAGE + exerciseData
                        + System.lineSeparator() + EXAMPLE_SIT_AND_REACH_DISTANCE_FORMAT);
            }
            break;
        case SIT_UP_ACRONYM:
            if (stringToValidInteger(exerciseData) == -1) {
                throw new IllegalArgumentException(INVALID_SIT_UP_REPETITIONS_MESSAGE + exerciseData
                        + System.lineSeparator() + EXAMPLE_SIT_UP_REPETITIONS_FORMAT);
            }
            break;
        case STANDING_BROAD_JUMP_ACRONYM:
            if (stringToValidInteger(exerciseData) == -1) {
                throw new IllegalArgumentException(INVALID_STANDING_BROAD_JUMP_DISTANCE_MESSAGE + exerciseData
                        + System.lineSeparator() + EXAMPLE_STANDING_BROAD_JUMP_DISTANCE_FORMAT);
            }
            break;
        case WALK_AND_RUN_ACRONYM:
            if(!exerciseData.matches("\\d+:[0-5]\\d")){
                throw new IllegalArgumentException(INVALID_WALK_AND_RUN_TIMING_MESSAGE + exerciseData
                        + System.lineSeparator() + EXAMPLE_WALK_AND_RUN_TIMING_FORMAT);
            }
            break;
        default:
            break;
        }
        return editDetails;
    }

    public static FoodEntry validFood(String foodDescription, int foodCalories) {
        if (foodDescription.trim().isEmpty() || foodCalories <= 0) {
            // TODO: WRITE_EXCEPTION_MESSAGE
            throw new IllegalArgumentException();
        }
        return new FoodEntry(foodDescription, foodCalories, LocalDateTime.now());
    }

    public static int stringToValidInteger(String str) {
        try {
            int result = Integer.parseInt(str);
            if(result >= 0) {
                return result;
            }
            return -1;
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}
