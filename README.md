Minesweeper Game

Overview:
This is a Java implementation of the Minesweeper game that runs on the command line.
The game prompts the user for the grid size and number of mines (max 35% of total squares),
generates a board with random mine placement, and allows the user to reveal cells by entering coordinates (e.g. A1).
Cells with zero adjacent mines trigger an automatic flood fill of adjacent cells.
The game ends when the user detonates a mine or wins by revealing all non-mine cells.

Design and Assumptions:
- The solution follows object oriented design with a clear separation of concerns.
- The Board class manages grid setup, mine placement, adjacent mine calculation, and cell reveals.
- The InputParser handles conversion of user input (like "A1") into board indices.
- The MinesweeperGame class drives the game flow and user interaction.
- JUnit 5 tests cover core functionalities (board initialization, adjacent mine calculation, reveal logic, and win condition).
- SOLID principles were applied ensuring maintainability, extendability, and testability.

Environment:
- Java 8 or higher is required.
- Maven is used for build and dependency management.
- The application is platform independent (Windows, Linux, macOS).

How to Build and Run:
1. Ensure that Java (JDK 8 or above) and Maven are installed.
2. Unzip the source code to a directory.
3. Open a terminal and navigate to the project’s root directory (where pom.xml is located).
4. To compile the project, run:
   mvn clean compile
5. To run the tests, run:
   mvn test
6. To run the application, run:
   mvn exec:java -Dexec.mainClass="minesweeper.MinesweeperGame"

Alternatively, you can package the application into a jar file and run it with the java -jar command.

Thank you for reviewing the Minesweeper application.
