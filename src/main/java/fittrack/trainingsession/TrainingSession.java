package fittrack.trainingsession;

import fittrack.enums.Exercise;
import fittrack.exception.InvalidSaveDataException;
import fittrack.exercisestation.ExerciseStation;
import fittrack.exercisestation.PullUpStation;
import fittrack.exercisestation.ShuttleRunStation;
import fittrack.exercisestation.SitAndReachStation;
import fittrack.exercisestation.SitUpStation;
import fittrack.exercisestation.StandingBroadJumpStation;
import fittrack.exercisestation.WalkAndRunStation;
import fittrack.storage.Saveable;
import fittrack.user.User;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.EnumMap;
import java.util.Map;

import static fittrack.messages.Messages.DEFAULT_MOOD_MSG;
import static fittrack.storage.Storage.DATA_DELIMITER;
import static fittrack.storage.Storage.DATA_DELIMITER_REGEX;

public class TrainingSession extends Saveable {

    static final String[] EXERCISE_LIST = {"SU","SBJ", "SR", "SAR", "PU", "WAR"};
    static final int NUM_OF_EXERCISES = EXERCISE_LIST.length;

    // Number of non-exercise fields parsed from each string during initialisation
    static final int NUM_OF_NON_EXERCISE_FIELDS = 6;

    static final int MAX_POINT = 5;
    static final int GOLD_GRADE = 3;
    static final int GOLD_POINT = 21;
    static final int SILVER_GRADE = 2;
    static final int SILVER_POINT = 15;
    static final int BRONZE_GRADE = 1;
    static final int BRONZE_POINT = 6;

    static final String GOLD_STRING = "Gold";
    static final String SILVER_STRING = "Silver";
    static final String BRONZE_STRING = "Bronze";
    static final String NO_AWARD = "No Award";

    private static int longestSessionDescription = 0;

    private LocalDateTime sessionDatetime;
    private String sessionDescription;
    private User user;
    private String mood = DEFAULT_MOOD_MSG;

    private Map<Exercise, ExerciseStation> exerciseStations = new EnumMap<>(Exercise.class);


    public TrainingSession(LocalDateTime datetime, String sessionDescription, User user) {
        this.sessionDatetime = datetime;
        this.sessionDescription = sessionDescription;
        this.user = user;
        initialiseExerciseStations();
        updateSessionDescriptionLength();
    }

    private void updateSessionDescriptionLength(){
        int currentLength = this.sessionDescription.length();
        if(currentLength > longestSessionDescription){
            longestSessionDescription = currentLength;
        }
    }

    private void initialiseExerciseStations(){
        exerciseStations.put(Exercise.PULL_UP, new PullUpStation());
        exerciseStations.put(Exercise.SHUTTLE_RUN, new ShuttleRunStation());
        exerciseStations.put(Exercise.SIT_AND_REACH, new SitAndReachStation());
        exerciseStations.put(Exercise.SIT_UP, new SitUpStation());
        exerciseStations.put(Exercise.STANDING_BROAD_JUMP, new StandingBroadJumpStation());
        exerciseStations.put(Exercise.WALK_AND_RUN, new WalkAndRunStation());
    }

    private int processReps(Exercise exerciseType, String reps){
        switch (exerciseType) {
        case SHUTTLE_RUN:
            reps = reps.replace(".", "");
            return Integer.parseInt(reps);
        case WALK_AND_RUN:
            // Convert input to seconds if provided in mm:ss format
            if (reps.contains(":")) {
                String[] minutesSeconds = reps.split(":");
                int minutesInSeconds = Integer.parseInt(minutesSeconds[0]) * 60;
                int seconds = Integer.parseInt(minutesSeconds[1]);
                return minutesInSeconds + seconds;
            } else {
                return Integer.parseInt(reps); // Data can be interpreted as given in seconds
            }
        default:
            return Integer.parseInt(reps);
        }
    }

    //Edits session data
    public void editExercise(Exercise exerciseType, String reps, Boolean printConfirmation) {
        int actualReps = processReps(exerciseType, reps);
        ExerciseStation currentExercise = this.exerciseStations.get(exerciseType);
        currentExercise.setPerformance(actualReps);
        currentExercise.getPoints(user);
        if (printConfirmation) {
            System.out.print("Exercise edited! Here's your new input: " + currentExercise + System.lineSeparator());
        }
    }

    //Override method to default true value
    public void editExercise(Exercise exerciseType, String reps) {
        editExercise(exerciseType, reps, true);
    }

    public int getExercisePoints(Exercise exercise) {
        return this.exerciseStations.get(exercise).getPoints(user);
    }

    public int getExercisePerformance(Exercise exercise){
        return this.exerciseStations.get(exercise).getPerformance();
    }

    public static int getLongestSessionDescription(){
        return longestSessionDescription;
    }

    public int getTotalPoints(){
        int totalPoints = 0;
        for(Map.Entry<Exercise, ExerciseStation> entry : exerciseStations.entrySet()){
            totalPoints += entry.getValue().getPoints(user);
        }
        return totalPoints;
    }

    //Returns string for award attained
    private String award(int minPoint, int totalPoints) {
        if(minPoint >= GOLD_GRADE && totalPoints >= GOLD_POINT) {
            return GOLD_STRING;
        } else if(minPoint >= SILVER_GRADE && totalPoints >= SILVER_POINT) {
            return SILVER_STRING;
        } else if(minPoint >= BRONZE_GRADE && totalPoints >= BRONZE_POINT) {
            return BRONZE_STRING;
        } else{
            return NO_AWARD;
        }
    }

    public String getSessionDescription() {
        return this.sessionDescription;
    }

    public String getSessionDatetime(){
        return this.sessionDatetime.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
    }

    public void setSessionDateTime(LocalDateTime newDateTime) {
        this.sessionDatetime = newDateTime;
    }

    public void printSessionInformation() {
        System.out.print(this.sessionDescription + " | " +
                this.sessionDatetime.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")) + System.lineSeparator());
    }

    //Print out all exercise data, including the total points and award given
    public void viewSession() {
        int totalPoints = 0;
        int minPoint = MAX_POINT;
        int exercisePoint;

        System.out.print("Training Session: " + this.sessionDescription + System.lineSeparator() +
                "Training Datetime: " + this.sessionDatetime.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))
                + System.lineSeparator());

        System.out.print("Mood: " + this.mood + System.lineSeparator());

        for(ExerciseStation exercise : exerciseStations.values()) {
            exercisePoint = exercise.getPoints(user);
            totalPoints += exercisePoint;
            if(minPoint > exercisePoint) {
                minPoint = exercisePoint;
            }
            System.out.print(exercise.getName() + " | " +
                    exercise + System.lineSeparator());
        }

        System.out.print("Total points: " + totalPoints + System.lineSeparator() +
                "Overall Award: " + award(minPoint, totalPoints) + System.lineSeparator());
    }

    public void setMood(String mood) {
        this.mood = mood;
    }
    /**
     * Serializes this `TrainingSession` object into a formatted string suitable for saving to storage.
     * The format includes the session description, session date-time, and data for each exercise type.
     * <p>
     * Format: {@code "TrainingSession" | sessionDescription | sessionDateTime | User Sex | User age | Mood data
     * | SU data | SBJ data | SR data | SAR data | PU data | WAR data}
     *
     * <p>
     * The deadline format is expected to be "dd/MM/yyyy HH:mm" or "dd/MM/yyyy".
     *
     * @return A formatted string representing this `TrainingSession`, including the session details and
     *         exercise performance data for each type.
     */
    @Override
    public String toSaveString(){

        String sessionInfo = this.sessionDescription;
        String sessionDateTime = this.sessionDatetime.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
        String userSex = user.getGender().toString();
        String userAge = String.valueOf(user.getAge());
        String moodInfo = this.mood;

        // Collect information for each exercise type
        String infoPU = exerciseStations.get(Exercise.PULL_UP).getSaveStringInfo();
        String infoSBJ = exerciseStations.get(Exercise.STANDING_BROAD_JUMP).getSaveStringInfo();
        String infoSR = exerciseStations.get(Exercise.SHUTTLE_RUN).getSaveStringInfo();
        String infoSAR = exerciseStations.get(Exercise.SIT_AND_REACH).getSaveStringInfo();
        String infoSU = exerciseStations.get(Exercise.SIT_UP).getSaveStringInfo();
        String infoWAR = exerciseStations.get(Exercise.WALK_AND_RUN).getSaveStringInfo();

        return "TrainingSession" + DATA_DELIMITER + sessionInfo + DATA_DELIMITER + sessionDateTime + DATA_DELIMITER
                + userSex + DATA_DELIMITER + userAge + DATA_DELIMITER + moodInfo + DATA_DELIMITER + infoPU
                + DATA_DELIMITER + infoSBJ + DATA_DELIMITER + infoSR + DATA_DELIMITER + infoSAR + DATA_DELIMITER
                + infoSU + DATA_DELIMITER + infoWAR;
    }

    /**
     * Deserializes a string representation of a TrainingSession and creates a new TrainingSession object.
     * The input string is expected to contain session description, date-time, and exercise data.
     *
     * @param saveString The string to deserialize, which should follow the format produced by toSaveString().
     *                   The format must include session details and exercise data separated by "|" symbols.
     * @return A TrainingSession object constructed from the provided string.
     * @throws InvalidSaveDataException If the input string is incomplete, improperly formatted, or contains
     *                                  invalid date-time data.
     */
    public static TrainingSession fromSaveString(String saveString) throws InvalidSaveDataException {
        String[] stringData = saveString.split(DATA_DELIMITER_REGEX);

        // Check for all exercise data is present (including Item-Type/description/DateTime/Mood information)
        if (stringData.length < (NUM_OF_EXERCISES+NUM_OF_NON_EXERCISE_FIELDS)) {
            throw new InvalidSaveDataException("Data missing from TrainingSession-apparent string");
        }

        // Parse session description and date-time from their respective indices
        String sessionDescription = stringData[1];
        LocalDateTime sessionDatetime;
        try {
            sessionDatetime = LocalDateTime.parse(stringData[2], DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
        } catch (Exception e) {
            throw new InvalidSaveDataException("Invalid date-time format in TrainingSession string.");
        }

        // Parse user data
        String userSex = stringData[3];
        String userAge = stringData[4];
        User sessionUser = new User(userSex, userAge);

        // Create new TrainingSession item to be added to load list at startup
        TrainingSession loadedSession = new TrainingSession(sessionDatetime,sessionDescription, sessionUser);

        // Parse and load mood data
        String sessionMood = stringData[5];
        loadedSession.setMood(sessionMood);

        // Parse and load exercise data
        for (int i = 0; i < NUM_OF_EXERCISES; i++) {
            // Start reading exercise data
            String repsData = stringData[NUM_OF_NON_EXERCISE_FIELDS + i];
            Exercise exerciseType = Exercise.fromUserInput(EXERCISE_LIST[i]);

            // If exercise data-value is same as default value, skip updating exercise.
            if (repsData.equals("0") || repsData.equals("-1")) {
                continue;
            }

            // Else, update exercise data
            loadedSession.editExercise(exerciseType, repsData, false);

        }

        return loadedSession;
    }
}
