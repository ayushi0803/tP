package fittrack.fitnessgoals;

import fittrack.fitnessgoal.AddFitnessGoal;
import fittrack.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDateTime;

class AddFitnessGoalTest {

  private AddFitnessGoal addFitnessGoal;
  private User testUser;

  @BeforeEach
  void setUp() {
    // Provide gender and age as arguments to the User constructor
    testUser = new User("male", "25"); // Example gender and age
  }

  @Test
  void testAddGoalWithDeadline() {
    LocalDateTime deadline = LocalDateTime.of(2024, 12, 31, 23, 59);
    addFitnessGoal = new AddFitnessGoal("Run a marathon", deadline);
    addFitnessGoal.addGoal(testUser); // Assuming output is printed or handled by mock
    // Verify the goal is added (you might mock the system output to assert printed values)
  }

  @Test
  void testAddGoalWithoutDeadline() {
    addFitnessGoal = new AddFitnessGoal("Read 10 books", null);
    addFitnessGoal.addGoal(testUser); // Check output for no deadline set
    // Verify the goal is added without deadline
  }

  @Test
  void testAddGoalWithNullDescription() {
    assertThrows(IllegalArgumentException.class, () -> {
      addFitnessGoal = new AddFitnessGoal(null, LocalDateTime.of(2024, 12, 31, 23, 59));
      addFitnessGoal.addGoal(testUser);
    });
  }
}
