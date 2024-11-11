package fittrack.user;

import fittrack.enums.Gender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


class UserTest {

    private User user;

    @BeforeEach
    public void setUp() {
        user = new User("male", "20");
    }

    @Test
    public void constructor_initializesUserWithGenderAndAge() {
        assertEquals(Gender.MALE, user.getGender(), "User gender should be initialized to MALE");
        assertEquals(20, user.getAge(), "User age should be initialized to 20");
        assertTrue(user.getGoals().isEmpty(), "User goals list should be initially empty");
    }

    @Test
    public void setGender_changesGender() {
        user.setGender("female");
        assertEquals(Gender.FEMALE, user.getGender(), "User gender should be changed to FEMALE");
    }

    @Test
    public void setAge_changesAge() {
        user.setAge("25");
        assertEquals(25, user.getAge(), "User age should be changed to 25");
    }

    @Test
    public void toString_returnsGenderAndAge() {
        String userString = user.toString();
        assertEquals("MALE 20", userString, "toString should return 'MALE 20' for gender=MALE and age=20");
    }
}
