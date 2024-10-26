package fittrack.exercisestation;

import fittrack.calculator.PullUpCalculator;
import fittrack.user.User;

public class PullUpStation extends ExerciseStation {
    private final String name = "Pull Up Station";
    private int reps;

    public PullUpStation() {
        this.reps = 0;
        this.points = 0;
    }

    public int getReps() {
        return reps;
    }

    @Override
    public void setPerformance(int performanceResult) {
        this.reps = performanceResult;
    }

    @Override
    public int getPoints(User user) {
        points = PullUpCalculator.calculatePoints(user.gender, user.age, reps);
        return points;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Reps: " + reps + " | " + points + " points";
    }
}
