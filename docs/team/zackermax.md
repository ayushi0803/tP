# Zackermax See - Project Portfolio Page

## Overview
FitTrackCLI is command-line-based chatbot to help users manage their NAPFA related exercises and goals. 
It lets users record and track training sessions, calculate their NAPFA scores and awards and set fitness goals.

## Summary of Contributions

### Code contributed:
https://nus-cs2113-ay2425s1.github.io/tp-dashboard/?search=&sort=groupTitle&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=true&checkedFileTypes=docs~functional-code~test-code~other&since=2024-09-20&tabOpen=true&tabType=authorship&tabAuthor=Zackermax&tabRepo=AY2425S1-CS2113-W13-2%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs~functional-code~test-code~other&authorshipIsBinaryFileTypeChecked=false&authorshipIsIgnoredFilesChecked=false

### Enhancements implemented:
I implemented the following classes, exceptions and functions which handle data logging and storage, user interfacing and execution of commands.

| Class            | Functionality                                                                                                |
|------------------|--------------------------------------------------------------------------------------------------------------|
| FitTrack         | Entry point of the application, manages application initialization by coordinating other classes             |
| FitTrackLogger   | Manages logging for the application, ensuring errors and important events are properly recorded              |
| User             | Records the user’s information, such as age and gender, and provides methods to modify or retrieve this data |
| Parser           | Handles parsing of user input, converting it into commands and actions                                       |
| Ui               | Handles user interaction and CLI output, printing messages and data to the console                           |
| ParserExceptions | Handles all exception handling for the Parser class. Effectively all I/O error handling.                     |

| Function                         | Purpose                                                                                                     |
|----------------------------------|-------------------------------------------------------------------------------------------------------------|
| User Configuration               | Set the age and gender of the user for use in NAPFA points calculation.                                     |
| Help Function                    | Prints a complete list of valid commands to aid the user in use of the application.                         |
| Add a Training Session           | Adds a Training Session with the specified name to allow the user to track their training progess.          |
| Modify Training Session DateTime | Modifies the DateTime of an existing Training Session.                                                      |
| List all Training Sessions       | Displays all Training Sessions the user has added.                                                          |
| View a Training Session          | View the details of a Training Session, including session name, datetime, exercise data, points and awards. |
| Edit a Training Session          | Edit the details of a training session, namely exercise and reps/time.                                      |
| Delete a Training Session        | Removes a Training Session from the list.                                                                   |
| Exit the program                 | Ends FitTrack CLI task and exits.                                                                           |

## Documentation:
### User Guide:

Documented the following features: User initialisation, Help function, Add Training Session, Modify Taining Session,
List all Training Sessions, View a Training Session, Edit a Training Session, Delete a Training Session and Exiting the program.

For each feature, I detailed its Purpose, Format, an Example and its Expected Output.

### Developer Guide:  

Documented the Project Architecture and created the Architecture Diagram.

Documented Set User, Add Training Session, Modify Training Session, Delete Training Session, List Training Session 
and View Training Session, with a sequence diagram for each.

Wrote 10 User Stories to give better perspective on project requirements and direction

## Project Management:
### Contributions to team-based tasks
Set up Gradle and Logger for team use.
Administrated the issue tracker (creating issues, assigning contributors, issue tags, etc).
Maintained user/developer docs that are not specific to a feature (Architecture, User Stories, etc).

### Review/mentoring contributions:
Responsible for reviewing and providing feedback on many issues and PRs.
