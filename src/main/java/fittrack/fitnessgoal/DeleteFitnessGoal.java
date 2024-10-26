package fittrack.fitnessgoal;

import fittrack.user.User;

public class DeleteFitnessGoal {

    private final String goalDescription;

    public DeleteFitnessGoal(String goalDescription) {
        this.goalDescription = goalDescription;
    }

    public void deleteGoal(User user) {
        boolean deleted = user.deleteGoal(
              goalDescription); // Assume this method is updated in User to find and delete by description
        if (deleted) {
            System.out.println("Deleted goal: " + goalDescription);
        } else {
            System.out.println("Goal not found: " + goalDescription);
        }
    }
}
