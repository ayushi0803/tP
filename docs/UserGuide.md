# FitTrackCLI User Guide

## Introduction

FitTrackCLI is command-line-based chatbot to help users manage their NAPFA related exercises and goals!
The user can:
1. Record and track training sessions.
2. Calculate NAPFA scores from training sessions.
3. Set fitness goals.
4. Visualise their training progress.

The NAPFA score sheet used for this chatbot can be found here:
This guide will bring you through the various features of FitTrackCLI, and how to utilise them!

## FitTrackCLI's Features

### 1. User initialisation: `set`

**Purpose**: Set the age and gender of the user.

**Format**: `set [gender]  [age]`
- `gender` and `age` field must be non-empty. Gender must be "male" or "female". Age must be an integer.

**Example**: `male 12`

**Expected output**:
```
____________________________________________________________________________________________________
You are a 12 year old male.
____________________________________________________________________________________________________
```

### 2. Help Function: `help`
**Purpose**: Prints a complete list of valid commands.

**Format**: `help`

**Expected output**:
```
____________________________________________________________________________________________________
COMMAND                                                          EXAMPLE
help                                                             help
set (gender) (age)                                               set male 12
add (session name)                                               add session1
list                                                             list
view (session index)                                             view 1
edit (session index) (exercise acronym) (repetitions/time)       edit 1 PU 1
delete (session index)                                           delete 1
remind (Event / Task) (deadline)                                 remind NAPFA DD/MM/YYYY
list-remind                                                      list-remind 
delete-remind (reminder index)                                   delete-remind1
upcoming-remind                                                  upcoming-remind
add-goal (goal name) (deadline)                                  add-goal run 12/12/2024 14:00:00
delete-goal (goal index)                                         delete-goal 1
list-goal                                                        list-goal
exit                                                             exit
____________________________________________________________________________________________________
```

### 3. Add a Training Session: `add`
**Purpose**: Adds a Training Session with the specified name.

**Format**: `add [session name]`
- `session name` field must be non-empty.

**Example**: `add session1`

**Expected output**:
```
____________________________________________________________________________________________________
Got it. I've added a new training session:
1. session1 | 29/10/2024 12:40
There are 1 sessions in the list.
____________________________________________________________________________________________________
```

### 4. List all Training Sessions: `list`
**Purpose**: Displays all Training Sessions the user has added.

**Format**: `list`

**Example Output**:
```
____________________________________________________________________________________________________
Here are your training sessions:
1. session1 | 29/10/2024 12:40
2. session2 | 29/10/2024 12:41
There are 2 sessions in the list.
____________________________________________________________________________________________________
```

### 5. View a Training Session: `view`
**Purpose**: View the details of a Training Session, including session name, datetime, exercise data, points and awards.

**Format**: `view [session index]`

**Example**: `view 1`

**Expected Output**:
```
____________________________________________________________________________________________________
Training Session: session1
Training Datetime: 29/10/2024 12:40
Pull Up Station | Reps: 0 | 0 points
Shuttle Run Station | Time: NA | 0 points
Sit and Reach Station | Distance: 0cm | 0 points
Sit Up Station | Reps: 0 | 0 points
Standing Broad Jump Station | Distance: 0cm | 0 points
Walk and Run Station | Time: NA | 0 points
Total points: 0
Overall Award: No Award
____________________________________________________________________________________________________
```

### 6. Edit a Training Session: `edit`
**Purpose**: Edit the details of a training session, namely exercise and reps/time.

**Format**: `edit [session index] [exercise acronym] [repetitions/time]`

**Example**: `edit 1 PU 45`

**Expected Output**:
```
1
Exercise edited! Here's your new input: Reps: 45 | 5 points
____________________________________________________________________________________________________
Training Session: session1
Training Datetime: 29/10/2024 12:40
Pull Up Station | Reps: 45 | 5 points
Shuttle Run Station | Time: NA | 0 points
Sit and Reach Station | Distance: 0cm | 0 points
Sit Up Station | Reps: 0 | 0 points
Standing Broad Jump Station | Distance: 0cm | 0 points
Walk and Run Station | Time: NA | 0 points
Total points: 5
Overall Award: No Award
____________________________________________________________________________________________________
```

### 7. Deleting a Training Session: `delete`
**Purpose**: Removes a task from your list.

**Format**: `delete [session index]`

**Example**: `delete 1`

**Expected Output**:
```
____________________________________________________________________________________________________
Got it. I've deleted this training session:session1 | 29/10/2024 12:40
There are 1 sessions in the list.
____________________________________________________________________________________________________
```

### 8. Exiting the program: `exit`
**Purpose**: Ends FitTrack CLI task and exits.

**Format**: `exit`

**Expected Output**:
```
____________________________________________________________________________________________________
Bye! Hope to see you again soon!
____________________________________________________________________________________________________
```

## FitTrackCLI's Command Summary
| Command    | Format                                                         | Example        |
|------------|----------------------------------------------------------------|----------------|
| **help**   | `help`                                                         | `help`         |
| **set**    | `set GENDER AGE`                                               | `set male 12`  |
| **add**    | `add SESSION_NAME`                                             | `add session1` |
| **list**   | `list`                                                         | `list`         |
| **view**   | `view SESSION_INDEX`                                           | `view 1`       |
| **edit**   | `edit SESSION_INDEX EXERCISE_ACRONYM REPETITION/TIME_DURATION` | `edit 1 PU 45` |
| **delete** | `delete SESSION_INDEX `                                        | `delete 1 `    |
| **exit**   | `exit`                                                         | `exit`         |

