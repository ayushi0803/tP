package fittrack.fitnessgoal;

import fittrack.user.User;

@SuppressWarnings("checkstyle:Indentation")
public class DeleteFitnessGoal {

    @SuppressWarnings("checkstyle:Indentation")
    private final String goalDescription;

    @SuppressWarnings("checkstyle:Indentation")
    public DeleteFitnessGoal(String goalDescription) {
        this.goalDescription = goalDescription;
    }

    @SuppressWarnings("checkstyle:Indentation")
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
