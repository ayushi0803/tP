package fittrack.fitnessgoal;

import fittrack.user.User;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class representing the functionality to delete a fitness goal based on its description.
 */
public class DeleteFitnessGoal {

    // Logger for recording deletion events and warnings
    private static final Logger LOGGER = Logger.getLogger(DeleteFitnessGoal.class.getName());
    // Description of the fitness goal to be deleted
    private final String goalDescription;

    /**
     * Constructs a DeleteFitnessGoal instance with the specified goal description.
     *
     * @param goalDescription Description of the fitness goal to be deleted.
     */
    public DeleteFitnessGoal(String goalDescription) {
        this.goalDescription = goalDescription;
    }

    /**
     * Attempts to delete the goal from the user's list of goals.
     *
     * @param user The user from whom the goal is to be deleted.
     * @return
     */
    public boolean deleteGoal(User user) {
        // Check if the user object is null to avoid null pointer exceptions
        if (user == null) {
            LOGGER.log(Level.WARNING, "User object is null. Goal deletion cannot proceed.");
            return false;
        }

        // Attempt to delete the goal and store the result
        boolean deleted = user.deleteGoal(goalDescription);

        // Log the outcome of the deletion attempt
        if (deleted) {
            LOGGER.log(Level.INFO, "Successfully deleted goal: " + goalDescription);
        } else {
            LOGGER.log(Level.INFO, "Goal not found: " + goalDescription);
        }
        return deleted;
    }
}
