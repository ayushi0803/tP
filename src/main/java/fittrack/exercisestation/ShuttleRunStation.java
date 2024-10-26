package fittrack.exercisestation;

import fittrack.calculator.ShuttleRunCalculator;
import fittrack.user.User;

public class ShuttleRunStation extends ExerciseStation {
    private final String name = "Shuttle Run Station";
    private int time;

    public ShuttleRunStation() {
        time = INVALID_TIME;
        points = DEFAULT_POINT;
    }

    public String getTime() {
        if(time == INVALID_TIME) {
            return "NA";
        }
        String timeString = Integer.toString(time);
        return timeString.substring(0, timeString.length() - 1) + "." +
                timeString.substring(timeString.length() - 1) + "s";
    }

    @Override
    public void setPerformance(int performanceResult) {
        this.time = performanceResult;
    }

    @Override
    public int getPoints(User user) {
        if(time == INVALID_TIME) {
            return DEFAULT_POINT;
        }
        points = ShuttleRunCalculator.calculatePoints(user.gender, user.age, time);
        return points;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Time: " + getTime() + " | " + points + " points";
    }
}
