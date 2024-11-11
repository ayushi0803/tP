package fittrack.messages;

public class Messages {
    public static final String SEPARATOR = "__________________________________________________"
            + "__________________________________________________";

    public static final String SET_USER_COMMAND = "set";
    public static final String HELP_COMMAND = "help";
    public static final String ADD_SESSION_COMMAND = "add";
    public static final String MODIFY_SESSION_DATETIME_COMMAND = "modify";
    public static final String LIST_SESSIONS_COMMAND = "list";
    public static final String VIEW_SESSION_COMMAND = "view";
    public static final String EDIT_EXERCISE_COMMAND = "edit";
    public static final String DELETE_SESSION_COMMAND = "delete";
    public static final String GRAPH_POINTS_COMMAND = "gpoints";
    public static final String GRAPH_PERFORMANCE_COMMAND = "gperformance";
    public static final String ADD_REMINDER_COMMAND = "remind";
    public static final String DELETE_REMINDER_COMMAND = "delete-remind";
    public static final String LIST_REMINDER_COMMAND = "list-remind";
    public static final String LIST_UPCOMING_REMINDER_COMMAND = "upcoming-remind";
    public static final String EDIT_MOOD_COMMAND = "edit-mood";
    public static final String EXIT_COMMAND = "exit";
    public static final String ADD_GOAL_COMMAND = "add-goal";
    public static final String DELETE_GOAL_COMMAND = "delete-goal";
    public static final String LIST_GOAL_COMMAND = "list-goal";
    public static final String ADD_WATER_COMMAND = "add-water";
    public static final String DELETE_WATER_COMMAND = "delete-water";
    public static final String LIST_WATER_COMMAND = "list-water";
    public static final String ADD_FOOD_COMMAND = "add-food";
    public static final String DELETE_FOOD_COMMAND = "delete-food";
    public static final String LIST_FOOD_COMMAND = "list-food";
    public static final String LIST_DAILY_INTAKE_COMMAND = "list-intake";

    public static final String PULL_UP_ACRONYM = "PU";
    public static final String SHUTTLE_RUN_ACRONYM = "SR";
    public static final String SIT_AND_REACH_ACRONYM = "SAR";
    public static final String SIT_UP_ACRONYM = "SU";
    public static final String STANDING_BROAD_JUMP_ACRONYM = "SBJ";
    public static final String WALK_AND_RUN_ACRONYM = "WAR";

    public static final String INIT_SENTENCE = "Hello! I'm FitTrack."
            + System.lineSeparator() + "Please input your gender and age:";
    public static final String HELP_MESSAGE = "COMMAND                                                          EXAMPLE"
            + System.lineSeparator() + "help                                                             help"
            + System.lineSeparator() + "set (gender) (age)                                               set male 12"
            + System.lineSeparator() + "add (session name)                                               add session1"
            + System.lineSeparator() + "modify (session index) (new datetime)                            " +
            "modify 1 1986-04-08 12:30"
            + System.lineSeparator() + "list                                                             list"
            + System.lineSeparator() + "view (session index)                                             view 1"
            + System.lineSeparator() + "edit (session index) (exercise acronym) (repetitions/time)       edit 1 PU 1"
            + System.lineSeparator() + "delete (session index)                                           delete 1"
            + System.lineSeparator() + "edit-mood (session index) (mood description)                     "
            + "edit-mood 1 Sad"
            + System.lineSeparator() + "delete (session index)                                           delete 1"
            + System.lineSeparator() + "remind (Event / Task) // (deadline)                              remind " +
            "NAPFA // DD/MM/YYYY"
            + System.lineSeparator() + "list-remind                                                      list-remind "
            + System.lineSeparator() + "delete-remind (reminder index)                                   delete-remind"
            + "1"
            + System.lineSeparator() + "upcoming-remind                                                  "+
            "upcoming-remind"
            + System.lineSeparator() + "add-goal (goal name) (deadline)                                  add-goal run" +
            " 12/12/2024 14:00:00"
            + System.lineSeparator() + "delete-goal (goal index)                                         delete-goal 1"
            + System.lineSeparator() + "list-goal                                                        list-goal"
            + System.lineSeparator() + "gpoints                                                          gpoints PU"
            + System.lineSeparator() + "gperformance                                                     gperformance" +
            " WAR"
            + System.lineSeparator() + "add-water (amount)                                               add-water 500"
            + System.lineSeparator() + "delete-water (index)                                             delete-water 1"
            + System.lineSeparator() + "list-water                                                       list-water"
            + System.lineSeparator() + "add-food (food item) (calories)                                  add-food apple"
            + "100"
            + System.lineSeparator() + "delete-food (index)                                              delete-food 1"
            + System.lineSeparator() + "list-food                                                        list-food"
            + System.lineSeparator() + "list-intake                                                      list-intake"
            + System.lineSeparator() + "exit                                                             exit";
    public static final String LIST_SESSION_MESSAGE = "Here are your training sessions:";
    public static final String LIST_SESSION_EMPTY_MESSAGE = "Your session list is currently empty.";
    public static final String ADD_SESSION_MESSAGE = "Got it. I've added a new training session:";
    public static final String DELETE_SESSION_MESSAGE = "Got it. I've deleted this training session:";

    public static final String LIST_REMINDER_MESSAGE = "Here are your reminders:";
    public static final String LIST_REMINDER_EMPTY_MESSAGE = "Your reminder list is currently empty.";
    public static final String ADD_REMINDER_MESSAGE = "Got it. I've added a new reminder";
    public static final String DELETE_REMINDER_MESSAGE = "Got it. I've deleted this reminder:";
    public static final String NO_UPCOMING_REMINDERS = "There are no reminders due in the next week.";

    public static final String ADD_GOAL_MESSAGE = "Got it. I've added a new goal:";
    public static final String DELETE_GOALS_MESSAGE = "Got it. I've deleted this goal.";
    public static final String LIST_GOAL_MESSAGE = "Here is your goal list!";

    public static final String DEFAULT_MOOD_MSG = "No mood recorded";

    public static final String ADD_WATER_MESSAGE = "Got it. I've added <WATER_ML> of water.";
    public static final String DELETE_WATER_MESSAGE = "Got it. I've deleted <WATER_ML> of water.";
    public static final String LIST_WATER_MESSAGE = "Here is your water intake (in ml):";

    public static final String ADD_FOOD_MESSAGE = "Got it. I've added food item: ";
    public static final String DELETE_FOOD_MESSAGE = "Got it. I've deleted food item: ";
    public static final String LIST_FOOD_MESSAGE = "Here is your food intake list: ";

    public static final String LIST_INTAKE_MESSAGE = "Here is your daily intake summary: ";

    public static final String MALE_GENDER = "male";
    public static final String FEMALE_GENDER = "female";
    public static final String INVALID_USER_INFO_MESSAGE = "Please provide a valid gender and age (male/female 12-24).";
    public static final String INVALID_SESSION_NAME_MESSAGE = "Please provide a valid session name.";
    public static final String INVALID_SESSION_INDEX_MESSAGE = "Please provide a valid session index.";
    public static final String INVALID_EXERCISE_DETAILS_MESSAGE =
            "Please provide a valid session index, station and reps.";

    public static final String INVALID_DATETIME_MESSAGE = "Invalid date/time. Please enter a valid date/time using " +
            "the [DD/MM/YYYY] or [DD/MM/YYYY HH:mm] format.";
    public static final String DATE_OUT_OF_RANGE = "Date out of valid range. Please use a date between " +
            "01/01/1900 and 31/12/2100.";
    public static final String INVALID_INDEX_NON_NUMERIC_MESSAGE = "Non-numeric index provided. Please enter a " +
            "positive number corresponding to the item you want to edit/delete";
    public static final String INDEX_OUT_OF_BOUNDS_MESSAGE = "Invalid index provided. Please enter a positive number " +
            "corresponding to the existing item you want to edit/delete";

    public static final String INVALID_MODIFY_DETAILS_MESSAGE =
            "Please provide a valid session index and DateTime (e.g. 12/03/2007 10:15).";
    public static final String INVALID_EXERCISE_ACRONYM_MESSAGE = "Invalid exercise acronym: ";
    public static final String INVALID_PULL_UP_REPETITIONS_MESSAGE = "Invalid Pull Up repetitions: ";
    public static final String INVALID_SHUTTLE_RUN_TIMING_MESSAGE = "Invalid Shuttle Run timing: ";
    public static final String INVALID_SIT_AND_REACH_DISTANCE_MESSAGE = "Invalid Sit and Reach distance: ";
    public static final String INVALID_SIT_UP_REPETITIONS_MESSAGE = "Invalid Sit Up repetitions: ";
    public static final String INVALID_STANDING_BROAD_JUMP_DISTANCE_MESSAGE = "Invalid Standing Broad Jump distance: ";
    public static final String INVALID_WALK_AND_RUN_TIMING_MESSAGE = "Invalid Walk and Run timing: ";
    public static final String INVALID_DATE_FORMAT_MESSAGE = "Invalid date format.";
    public static final String INVALID_LIST_COMMAND_MESSAGE = "Invalid List Command: Please input '"
            + LIST_SESSIONS_COMMAND + "' only.";
    public static final String INVALID_VERTICAL_BAR_INPUT_MESSAGE = "Please try again without including any vertical " +
            "bar characters ('|').";

    public static final String EXAMPLE_PULL_UP_REPETITIONS_FORMAT = "Example format: 1 PU 30";
    public static final String EXAMPLE_SHUTTLE_RUN_TIMING_FORMAT = "Example format: 1 SR 10.0";
    public static final String EXAMPLE_SIT_AND_REACH_DISTANCE_FORMAT = "Example format: 1 SAR 40";
    public static final String EXAMPLE_SIT_UP_REPETITIONS_FORMAT = "Example format: 1 SU 60";
    public static final String EXAMPLE_STANDING_BROAD_JUMP_DISTANCE_FORMAT = "Example format: 1 SBJ 200";
    public static final String EXAMPLE_WALK_AND_RUN_TIMING_FORMAT = "Example format: 1 WAR 12:00";

    public static final String ADD_MOOD_MESSAGE = "Mood log added: ";
    public static final String DELETE_MOOD_MESSAGE = "Mood log deleted with ID: ";
    public static final String LIST_MOOD_MESSAGE = "Your mood logs: ";

    public static final String INVALID_INPUT_MESSAGE = "I'm sorry, I don't know what that means.";
    public static final String EXIT_MESSAGE = "Bye! Hope to see you again soon!";
}
