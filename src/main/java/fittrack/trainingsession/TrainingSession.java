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

public class TrainingSession extends Saveable {

    static final String[] EXERCISE_LIST = {"SU","SBJ", "SR", "SAR", "PU", "WAR"};
    static final int NUM_OF_EXERCISES = EXERCISE_LIST.length;
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


    private LocalDateTime sessionDatetime;
    private String sessionDescription;
    private User user;

    private Map<Exercise, ExerciseStation> exerciseStations = new EnumMap<>(Exercise.class);

    public TrainingSession(LocalDateTime datetime, String sessionDescription, User user) {
        this.sessionDatetime = datetime;
        this.sessionDescription = sessionDescription;
        this.user = user;
        initialiseExerciseStations();
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
            }
        default:
            return Integer.parseInt(reps);
        }
    }

    //Edits session data
    public void editExercise(Exercise exerciseType, String reps, Boolean printConfirmation) {
        int actualReps = processReps(exerciseType, reps);
        ExerciseStation currentExercise = exerciseStations.get(exerciseType);
        currentExercise.setPerformance(actualReps);
        currentExercise.getPoints(user);
        if (printConfirmation) {
            System.out.print("Exercise edited! Here's your new input: " + currentExercise + System.lineSeparator());
        }
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
        return (this.sessionDescription + "|" +
                this.sessionDatetime.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
    }

    public void printSessionDescription() {
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

    @Override
    public String toSaveString(){

        String sessionInfo = this.sessionDescription;
        String sessionDateTime = this.sessionDatetime.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));

        // Collect information for each exercise type
        String PUInfo = exerciseStations.get(Exercise.PULL_UP).getSaveStringInfo();
        String SBJInfo = exerciseStations.get(Exercise.STANDING_BROAD_JUMP).getSaveStringInfo();
        String SRInfo = exerciseStations.get(Exercise.SHUTTLE_RUN).getSaveStringInfo();
        String SARInfo = exerciseStations.get(Exercise.SIT_AND_REACH).getSaveStringInfo();
        String SUInfo = exerciseStations.get(Exercise.SIT_UP).getSaveStringInfo();
        String WARInfo = exerciseStations.get(Exercise.WALK_AND_RUN).getSaveStringInfo();

        return "TrainingSession" + "|" + sessionInfo + "|" + sessionDateTime + "|" + PUInfo +  "|" + SBJInfo +  "|"
                + SRInfo + "|" + SARInfo + "|" + SUInfo + "|" + WARInfo;
    }

    public static TrainingSession fromSaveString(String saveString) throws InvalidSaveDataException {
        String[] stringData = saveString.split("\\|");

        // Check for all exercise data is present (including Item-Type/description/DateTime information)
        if (stringData.length < (NUM_OF_EXERCISES+3)) {
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

        // TODO: Implement proper user data loading.
        User user = new User("male", "19");

        // Create new TrainingSession item to be added to load list at startup
        TrainingSession loadedSession = new TrainingSession(sessionDatetime,sessionDescription, user);

        // Load exercise data
        for (int i = 0; i < NUM_OF_EXERCISES; i++) {
            String repsData = stringData[3 + i];  // Start reading exercise data from index 3 onward
            Exercise exerciseType = Exercise.fromUserInput(EXERCISE_LIST[i]);

            // If exercise data-value is same as default value, no need to update exercise.
            if (repsData.equals("0") || repsData.equals("-1")) continue;

            loadedSession.editExercise(exerciseType, repsData, false);
        }

        return loadedSession;
    }

}
