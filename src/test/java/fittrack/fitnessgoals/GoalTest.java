package fittrack.fitnessgoals;

import fittrack.fitnessgoal.Goal;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

class GoalTest {

    @Test
    void testGoalConstructorWithDeadline() {
        LocalDateTime deadline = LocalDateTime.of(2024, 12, 31, 23, 59);
        Goal goal = new Goal("Run a marathon", deadline);
        assertEquals("Run a marathon", goal.getDescription());
        assertEquals(deadline, goal.getDeadline());
    }

    @Test
    void testGoalConstructorWithoutDeadline() {
        Goal goal = new Goal("Read 10 books", null);
        assertEquals("Read 10 books", goal.getDescription());
        assertNull(goal.getDeadline());
    }

    @Test
    void testToSaveStringWithDeadline() {
        LocalDateTime deadline = LocalDateTime.of(2024, 12, 31, 23, 59);
        Goal goal = new Goal("Run a marathon", deadline);
        String saveString = goal.toSaveString();
        assertEquals("Goal | Run a marathon | 31/12/2024 23:59", saveString);
    }

    @Test
    void testToSaveStringWithoutDeadline() {
        Goal goal = new Goal("Read 10 books", null);
        String saveString = goal.toSaveString();
        assertEquals("Goal | Read 10 books | ", saveString);
    }

    @Test
    void testFromSaveStringWithDeadline() {
        String saveString = "Goal | Run a marathon | 31/12/2024 23:59";
        Goal goal = Goal.fromSaveString(saveString);
        assertEquals("Run a marathon", goal.getDescription());
        assertEquals(LocalDateTime.of(2024, 12, 31, 23, 59), goal.getDeadline());
    }

    @Test
    void testFromSaveStringWithoutDeadline() {
        String saveString = "Goal | Read 10 books | ";
        Goal goal = Goal.fromSaveString(saveString);
        assertEquals("Read 10 books", goal.getDescription());
        assertNull(goal.getDeadline());
    }

    @Test
    void testFromSaveStringInvalidFormat() {
        String saveString = "Invalid | Format | 31/12/2024";
        assertThrows(IllegalArgumentException.class, () -> {
            Goal.fromSaveString(saveString);
        });
    }

    @Test
    void testFromSaveStringInvalidDeadlineFormat() {
        String saveString = "Goal | Run a marathon | 31-12-2024 23:59";  // Invalid format
        assertNull(Goal.fromSaveString(saveString));  // Returns null because of invalid format
    }
}
