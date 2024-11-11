package fittrack.fitnessgoal;

import fittrack.user.User;
import java.time.LocalDateTime;

/**
 * Class representing the functionality to add a fitness goal with a description and optional deadline.
 */
public class AddFitnessGoal {

    // Description of the fitness goal
    private String goalDescription;
    // Deadline for achieving the fitness goal, can be null if no deadline is set
    private LocalDateTime deadline;

    /**
     * Constructs an AddFitnessGoal instance with the specified goal description and deadline.
     *
     * @param goalDescription Description of the fitness goal.
     * @param deadline Optional deadline for the fitness goal.
     */
    public AddFitnessGoal(String goalDescription, LocalDateTime deadline) {
        if (goalDescription == null || goalDescription.isEmpty()) {
            throw new IllegalArgumentException("Description cannot be null or empty.");
        }

        this.goalDescription = goalDescription;
        this.deadline = deadline;
    }

    /**
     * Adds the goal to the user's list of goals, displaying appropriate messages based on the presence of a deadline.
     *
     * @param user The user to whom the goal is being added.
     */
    public void addGoal(User user) {
        // Check if the goal has a deadline
        if (deadline == null) {
            // Print message if no deadline is set for the goal
            System.out.println("Goal added: " + goalDescription + ". No deadline set."
                + System.lineSeparator());
        } else {
            // Print message including the deadline if it is set
            System.out.println("Goal added: " + goalDescription + " with deadline: " + deadline
                + System.lineSeparator());
        }
    }
}
