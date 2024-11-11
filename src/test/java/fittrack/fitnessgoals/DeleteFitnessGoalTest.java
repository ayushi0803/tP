package fittrack.fitnessgoals;

import fittrack.fitnessgoal.DeleteFitnessGoal;
import fittrack.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DeleteFitnessGoalTest {

    private DeleteFitnessGoal deleteFitnessGoal;
    private User testUser;

    @BeforeEach
    void setUp() {
        // Set up the User with gender "male" and age "25"
        testUser = new User("male", "25");
    }

    @Test
    void testDeleteGoalSuccess() {
        // Add a goal first
        testUser.addGoal("Run a marathon");

        // Attempt to delete the goal
        deleteFitnessGoal = new DeleteFitnessGoal("Run a marathon");
        boolean result = deleteFitnessGoal.deleteGoal(testUser);

        // Verify that the goal was successfully deleted
        assertTrue(result);
        assertFalse(testUser.getGoals().stream().anyMatch(goal -> goal.getDescription().equals("Run a marathon")));
    }

    @Test
    void testDeleteGoalFailure() {
        // Try to delete a goal that doesn't exist
        deleteFitnessGoal = new DeleteFitnessGoal("Nonexistent goal");
        boolean result = deleteFitnessGoal.deleteGoal(testUser);

        // Verify that deletion failed (goal doesn't exist)
        assertFalse(result);
    }

    @Test
    void testDeleteGoalWithNullUser() {
        deleteFitnessGoal = new DeleteFitnessGoal("Run a marathon");
        // Ensure no exception is thrown when the user is null
        boolean result = deleteFitnessGoal.deleteGoal(null);
        assertFalse(result);
    }

    @Test
    void testDeleteGoalWithNullDescription() {
        deleteFitnessGoal = new DeleteFitnessGoal(null);
        // Ensure no exception is thrown when the description is null
        boolean result = deleteFitnessGoal.deleteGoal(testUser);
        assertFalse(result);
    }
}
