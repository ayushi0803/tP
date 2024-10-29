package fittrack.fitnessgoal;
import fittrack.user.User;
import java.time.LocalDateTime;

public class AddFitnessGoal {

    private final Goal goal;

    public AddFitnessGoal(String description, LocalDateTime deadline) {
        this.goal = new Goal(description, deadline);
    }

    public void addGoal(User user) {
        user.addGoal(goal); // Add the goal object to the user's goal list
        // Use the correct method to get the description of the goal
        System.out.println("Added goal: " + goal.getDescription());
    }
}
