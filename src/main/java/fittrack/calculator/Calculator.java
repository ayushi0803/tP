package fittrack.calculator;

import fittrack.enums.Gender;
import fittrack.exception.InvalidAgeException;
import fittrack.lookupkey.LookUpKey;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

public abstract class Calculator {

    protected static final int AGE_RANGE_LOWER_START = 12;
    protected static final int AGE_RANGE_LOWER_END = 19;
    protected static final int AGE_RANGE_UPPER_START = 20;
    protected static final int AGE_RANGE_UPPER_END = 24;

    // Utility method to find the points based on performance in the given table.
    protected static int getPointsFromTable(Map<LookUpKey, TreeMap<Integer, Integer>> pointsTable,
            Gender gender, int age, int performance, boolean reverseComparison) {

        try {
            LookUpKey key = new LookUpKey(gender, age);
            TreeMap<Integer, Integer> subTable = pointsTable.get(key);

            for (Map.Entry<Integer, Integer> entry : subTable.entrySet()) {
                if ((reverseComparison && performance <= entry.getKey()) ||
                        (!reverseComparison && performance >= entry.getKey())) {
                    return entry.getValue();
                }
            }
            return 0; // return 0 points as performance is below the minimum standard
        } catch (InvalidAgeException e) {
            System.out.println(e.getMessage());
            return 0; // return 0 points as invalid age is inputted
        }
    }

    // Utility method to add age sub-table for a specific gender
    protected static void addAgeSubTable(Map<LookUpKey, TreeMap<Integer, Integer>> pointsTable,
                                         Gender gender, int age, int[][] points, boolean reverseOrder) {
        assert age >= 12 && age <= 24 : "Age should be within 12 and 24 during table initialisation"; //
        TreeMap<Integer, Integer> ageSubTable;
        if (reverseOrder){
            ageSubTable = new TreeMap<>(Comparator.reverseOrder());
        } else{
            ageSubTable = new TreeMap<>();
        }

        for (int[] point : points) {
            assert point[0] >= 0 : "Performance metric cannot be negative";
            assert point[1] >= 0 : "Points should never be negative";
            ageSubTable.put(point[0], point[1]);
        }

        try {
            pointsTable.put(new LookUpKey(gender, age), ageSubTable);
        } catch (InvalidAgeException e) {
            System.out.println(e.getMessage());
        }
    }

    protected static void addAllTables(Map<LookUpKey, TreeMap<Integer, Integer>> pullUpTable,
                                       Gender gender, int[][][] ageTables, boolean reverseOrder) {
        // Add data for lower ages
        for (int age = AGE_RANGE_LOWER_START; age <= AGE_RANGE_LOWER_END; age++) {
            addAgeSubTable(pullUpTable, gender, age, ageTables[age - AGE_RANGE_LOWER_START], reverseOrder);
        }

        // Add data for upper ages
        for (int age = AGE_RANGE_UPPER_START; age <= AGE_RANGE_UPPER_END; age++) {
            addAgeSubTable(pullUpTable, gender, age, ageTables[ageTables.length - 1], reverseOrder); // Age 20-24 table
        }
    }
}
