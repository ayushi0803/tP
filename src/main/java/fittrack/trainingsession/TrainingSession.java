package fittrack.trainingsession;

import fittrack.enums.Exercise;
import fittrack.exercisestation.ExerciseStation;
import fittrack.exercisestation.PullUpStation;
import fittrack.exercisestation.ShuttleRunStation;
import fittrack.exercisestation.SitAndReachStation;
import fittrack.exercisestation.SitUpStation;
import fittrack.exercisestation.StandingBroadJumpStation;
import fittrack.exercisestation.WalkAndRunStation;
import fittrack.user.User;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.EnumMap;
import java.util.Map;

public class TrainingSession{

    static final int NUM_OF_EXERCISES = 6;
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

    private final LocalDateTime sessionDatetime;
    private final String sessionDescription;
    private final User user;

    private final Map<Exercise, ExerciseStation> exerciseStations = new EnumMap<>(Exercise.class);

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
            String[] minutesSeconds = reps.split(":");
            int minutesInSeconds = Integer.parseInt(minutesSeconds[0]) * 60;
            int seconds = Integer.parseInt(minutesSeconds[1]);
            return minutesInSeconds + seconds;
        default:
            return Integer.parseInt(reps);
        }
    }

    //Edits session data
    public void editExercise(Exercise exerciseType, String reps) {
        int actualReps = processReps(exerciseType, reps);
        ExerciseStation currentExercise = exerciseStations.get(exerciseType);
        currentExercise.setPerformance(actualReps);
        currentExercise.getPoints(user);
        System.out.print("Exercise edited! Here's your new input: " +
                currentExercise + System.lineSeparator());
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
        return (this.sessionDescription + " | " +
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
}
