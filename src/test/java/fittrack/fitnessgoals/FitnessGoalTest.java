package fittrack.fitnessgoals;

import fittrack.fitnessgoal.AddFitnessGoal;
import fittrack.fitnessgoal.DeleteFitnessGoal;
import fittrack.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FitnessGoalTest {

    private final String goalDescription = "Run 5km in under 30 minutes";
    private final LocalDateTime testDeadline = LocalDateTime.parse("2024-12-22T23:45:44");
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private User testUser; // Declare testUser here

    @BeforeEach.  
      public void setUp() {
          System.setOut(new PrintStream(outContent));
          testUser = new User("FEMALE", "25"); // Initialize the User instance here
          }

    // Test for adding a goal
    @Test
    public void testAddFitnessGoal() {
        AddFitnessGoal addGoal = new AddFitnessGoal(goalDescription, testDeadline);
        addGoal.addGoal(testUser); // Adding the goal to the user
        assertEquals("Added goal: " + goalDescription + System.lineSeparator(), outContent.toString());
    }

    // Test for deleting a goal
    @Test
    public void testDeleteFitnessGoal() {
        AddFitnessGoal addGoal = new AddFitnessGoal(goalDescription, testDeadline);
        addGoal.addGoal(testUser); // First, add the goal

        outContent.reset(); // Clear previous output
        DeleteFitnessGoal deleteGoal = new DeleteFitnessGoal(goalDescription);
        deleteGoal.deleteGoal(testUser); // Attempt to delete the goal

        assertEquals("Deleted goal: " + goalDescription + System.lineSeparator(), outContent.toString());
    }

    // Test for attempting to delete a non-existing goal
    @Test
    public void testDeleteNonExistingGoal() {
        DeleteFitnessGoal deleteGoal = new DeleteFitnessGoal("Non-existing goal");
        outContent.reset(); // Clear previous output
        deleteGoal.deleteGoal(testUser); // Attempt to delete a goal that doesn't exist

        assertEquals("Goal not found: Non-existing goal" + System.lineSeparator(), outContent.toString());
    }
}
