package fittrack.messages;

public class Messages {
    public static final String SEPARATOR = "__________________________________________________"
            + "__________________________________________________";

    public static final String SET_USER_COMMAND = "set";
    public static final String HELP_COMMAND = "help";
    public static final String ADD_SESSION_COMMAND = "add";
    public static final String LIST_SESSIONS_COMMAND = "list";
    public static final String VIEW_SESSION_COMMAND = "view";
    public static final String EDIT_EXERCISE_COMMAND = "edit";
    public static final String DELETE_SESSION_COMMAND = "delete";
    public static final String ADD_REMINDER_COMMAND = "remind";
    public static final String DELETE_REMINDER_COMMAND = "delete-remind";
    public static final String LIST_REMINDER_COMMAND = "list-remind";
    public static final String LIST_UPCOMING_REMINDER_COMMAND = "upcoming-remind";
    public static final String EXIT_COMMAND = "exit";
    public static final String ADD_GOAL = "add_goal";
    public static final String DELETE_GOAL = "delete_goal";
    public static final String LIST_GOAL = "LIST_goal";
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
            + System.lineSeparator() + "list                                                             list"
            + System.lineSeparator() + "view (session index)                                             view 1"
            + System.lineSeparator() + "edit (session index) (exercise index) (repetitions/time)         edit 1 1 1"
            + System.lineSeparator() + "delete (session index)                                           delete 1"
            + System.lineSeparator() + "remind (Event / Task) (deadline)                                 remind " +
            "NAPFA DD/MM/YYYY"
            + System.lineSeparator() + "list-remind                                                      list-remind "
            + System.lineSeparator() + "delete-remind (reminder index)                                   delete-remind"
            + "1"
            + System.lineSeparator() + "upcoming-remind                                                  "+
            "upcoming-remind"
            + System.lineSeparator() + "add-goal (goal name) (deadline)                                  add-goal run" +
            " 12/12/2024 14:00:00"
            + System.lineSeparator() + "delete-goal (goal index)                                         delete-goal 1"
            + System.lineSeparator() + "list-goal                                                        list-goal"
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

    public static final String NONNUMERICAL_INDEX_MESSAGE = "Please provide a valid numerical index.";
    public static final String INDEX_OUT_OF_BOUNDS_MESSAGE = "Sorry, but that index is not within the list.";
    public static final String INVALID_INPUT_MESSAGE = "I'm sorry, I don't know what that means.";
    public static final String EXIT_MESSAGE = "Bye! Hope to see you again soon!";
}
