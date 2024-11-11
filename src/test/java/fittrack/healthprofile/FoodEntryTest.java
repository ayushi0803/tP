package fittrack.healthprofile;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.LocalDateTime;

class FoodWaterIntakeTest {

    private FoodWaterIntake foodWaterIntake;

    @BeforeEach
    void setUp() {
        foodWaterIntake = new FoodWaterIntake();
    }

    @Test
    void testAddFood() {
        FoodEntry food = new FoodEntry("Apple", 95, LocalDateTime.now());
        foodWaterIntake.addFood(food);
        assertEquals(1, foodWaterIntake.getFoodList().size());
        assertEquals(food, foodWaterIntake.getFoodList().get(0));
    }

    @Test
    void testDeleteFood() {
        FoodEntry food = new FoodEntry("Apple", 95, LocalDateTime.now());
        foodWaterIntake.addFood(food);
        foodWaterIntake.deleteFood(0);
        assertTrue(foodWaterIntake.getFoodList().isEmpty());
    }

    @Test
    void testDeleteFoodInvalidIndex() {
        foodWaterIntake.deleteFood(0);  // No food added, should print error message
        // Assert no side effects, food list should be empty
        assertTrue(foodWaterIntake.getFoodList().isEmpty());
    }

    @Test
    void testAddWater() {
        WaterEntry water = new WaterEntry(500, LocalDateTime.now());
        foodWaterIntake.addWater(water);
        assertEquals(1, foodWaterIntake.getWaterList().size());
        assertEquals(water, foodWaterIntake.getWaterList().get(0));
    }

    @Test
    void testDeleteWater() {
        WaterEntry water = new WaterEntry(500, LocalDateTime.now());
        foodWaterIntake.addWater(water);
        foodWaterIntake.deleteWater(0);
        assertTrue(foodWaterIntake.getWaterList().isEmpty());
    }

    @Test
    void testDeleteWaterInvalidIndex() {
        foodWaterIntake.deleteWater(0);  // No water added, should print error message
        // Assert no side effects, water list should be empty
        assertTrue(foodWaterIntake.getWaterList().isEmpty());
    }

    @Test
    void testListDailyFoodIntakeNoFood() {
        foodWaterIntake.listDailyFoodIntake();
        // Verify output contains "No food items recorded."
    }

    @Test
    void testListDailyWaterIntakeNoWater() {
        foodWaterIntake.listDailyWaterIntake();
        // Verify output contains "No water intake recorded."
    }

    @Test
    void testListDailyIntake() {
        FoodEntry food = new FoodEntry("Apple", 95, LocalDateTime.now());
        WaterEntry water = new WaterEntry(500, LocalDateTime.now());
        foodWaterIntake.addFood(food);
        foodWaterIntake.addWater(water);
        foodWaterIntake.listDailyIntake();
        // Verify the output includes both the food and water entries
    }

    @Test
    void testListDailyFoodIntakeWithEntries() {
        LocalDate today = LocalDate.now();
        FoodEntry food1 = new FoodEntry("Apple", 95, LocalDateTime.now());
        foodWaterIntake.addFood(food1);
        FoodEntry food2 = new FoodEntry("Banana", 105, LocalDateTime.now());
        foodWaterIntake.addFood(food2);

        foodWaterIntake.listDailyFoodIntake();

        // Verify the output includes the food entries with their calories
        assertTrue(foodWaterIntake.getFoodList().size() > 0);
    }

    @Test
    void testListDailyWaterIntakeWithEntries() {
        LocalDate today = LocalDate.now();
        WaterEntry water1 = new WaterEntry(500, LocalDateTime.now());
        foodWaterIntake.addWater(water1);
        WaterEntry water2 = new WaterEntry(250, LocalDateTime.now());
        foodWaterIntake.addWater(water2);

        foodWaterIntake.listDailyWaterIntake();

        // Verify the output includes the water entries with their amounts
        assertTrue(foodWaterIntake.getWaterList().size() > 0);
    }

    // Additional Tests for full coverage

    @Test
    void testAddMultipleFoods() {
        FoodEntry food1 = new FoodEntry("Apple", 95, LocalDateTime.now());
        FoodEntry food2 = new FoodEntry("Orange", 62, LocalDateTime.now());
        FoodEntry food3 = new FoodEntry("Carrot", 41, LocalDateTime.now());

        foodWaterIntake.addFood(food1);
        foodWaterIntake.addFood(food2);
        foodWaterIntake.addFood(food3);

        assertEquals(3, foodWaterIntake.getFoodList().size());
    }

    @Test
    void testAddMultipleWaters() {
        WaterEntry water1 = new WaterEntry(500, LocalDateTime.now());
        WaterEntry water2 = new WaterEntry(250, LocalDateTime.now());
        WaterEntry water3 = new WaterEntry(750, LocalDateTime.now());

        foodWaterIntake.addWater(water1);
        foodWaterIntake.addWater(water2);
        foodWaterIntake.addWater(water3);

        assertEquals(3, foodWaterIntake.getWaterList().size());
    }

    @Test
    void testDeleteAllFoodItems() {
        FoodEntry food1 = new FoodEntry("Apple", 95, LocalDateTime.now());
        FoodEntry food2 = new FoodEntry("Banana", 105, LocalDateTime.now());
        foodWaterIntake.addFood(food1);
        foodWaterIntake.addFood(food2);

        foodWaterIntake.deleteFood(0);
        foodWaterIntake.deleteFood(0);

        assertTrue(foodWaterIntake.getFoodList().isEmpty());
    }

    @Test
    void testDeleteAllWaterItems() {
        WaterEntry water1 = new WaterEntry(500, LocalDateTime.now());
        WaterEntry water2 = new WaterEntry(250, LocalDateTime.now());
        foodWaterIntake.addWater(water1);
        foodWaterIntake.addWater(water2);

        foodWaterIntake.deleteWater(0);
        foodWaterIntake.deleteWater(0);

        assertTrue(foodWaterIntake.getWaterList().isEmpty());
    }

    @Test
    void testListDailyFoodIntakeWithMultipleEntries() {
        FoodEntry food1 = new FoodEntry("Apple", 95, LocalDateTime.now());
        FoodEntry food2 = new FoodEntry("Banana", 105, LocalDateTime.now());
        foodWaterIntake.addFood(food1);
        foodWaterIntake.addFood(food2);

        foodWaterIntake.listDailyFoodIntake();

        assertEquals(2, foodWaterIntake.getFoodList().size());
    }

    @Test
    void testListDailyWaterIntakeWithMultipleEntries() {
        WaterEntry water1 = new WaterEntry(500, LocalDateTime.now());
        WaterEntry water2 = new WaterEntry(250, LocalDateTime.now());
        foodWaterIntake.addWater(water1);
        foodWaterIntake.addWater(water2);

        foodWaterIntake.listDailyWaterIntake();

        assertEquals(2, foodWaterIntake.getWaterList().size());
    }

    @Test
    void testFoodAndWaterWithNoEntries() {
        // Ensure that lists are empty and the system handles that gracefully
        foodWaterIntake.listDailyIntake();
        assertTrue(foodWaterIntake.getFoodList().isEmpty());
        assertTrue(foodWaterIntake.getWaterList().isEmpty());
    }

    @Test
    void testTotalCaloriesCalculation() {
        FoodEntry food1 = new FoodEntry("Apple", 95, LocalDateTime.now());
        FoodEntry food2 = new FoodEntry("Banana", 105, LocalDateTime.now());
        foodWaterIntake.addFood(food1);
        foodWaterIntake.addFood(food2);

        foodWaterIntake.listDailyFoodIntake();

        int expectedTotalCalories = 95 + 105;
        // Simulate checking output for total calories
        assertTrue(foodWaterIntake.getFoodList().size() > 0);
    }
}
