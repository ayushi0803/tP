package fittrack.user;
import fittrack.enums.Gender;
import fittrack.fitnessgoal.Goal;
import java.util.ArrayList;

public class User {

    public Gender gender;
    public int age;
    private final ArrayList<Goal> goals;

    public User(String gender, String age) {
        this.gender = Gender.valueOf(gender.toUpperCase());
        this.age = Integer.parseInt(age);
        this.goals = new ArrayList<>();
    }

    public void setGender(String gender) {
        this.gender = Gender.valueOf(gender.toUpperCase());
    }

    public void setAge(String age) {
        this.age = Integer.parseInt(age);
    }

    public Gender getGender() {
        return gender;
    }

    public int getAge() {
        return age;
    }

    public void addGoal(Goal goal) {
        goals.add(goal);
    }

    public boolean deleteGoal(String goalDescription) {
        return goals.removeIf(goal -> goal.getDescription().equals(goalDescription));
    }

    public ArrayList<Goal> getGoals() {
        return goals;
    }
}
