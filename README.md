# Brändi Dog - SoPra FS22
Brändi Dog is an awesome board game played by 4 people and now you are able to play it online. It is exciting because not only luck but also tactics and strategy play an imporntant part. Two people form a team and play togehter with the aim to be the first who moved their marbles into their finish.

## Built with
- [SpringBoot](https://spring.io/projects/spring-boot) - Java-Framework
- [Gradle](https://gradle.org) - Build automation tool
- Java V.15

## Components
### GameService
- Role: Handles basic functions for the game like creating a game or fetching games
- Main file: [GameService.java](https://github.com/sopra-fs22-group-29/brandi-server/blob/master/src/main/java/ch/uzh/ifi/hase/soprafs22/service/GameService.java)

### GameLogicService
- Role: Handles all the Game Logic.
- Main file: [GameLogicService.java](https://github.com/sopra-fs22-group-29/brandi-server/blob/master/src/main/java/ch/uzh/ifi/hase/soprafs22/service/GameLogicService.java)

### InGameWebsocketService
- Role: Responsible for sending information about the game state to the users
- Main file: [InGameWebsocketService.java](https://github.com/sopra-fs22-group-29/brandi-server/blob/master/src/main/java/ch/uzh/ifi/hase/soprafs22/service/InGameWebsocketService.java)

### InGameWebsocketController
- Role: handles incoming in game websocket calls like when someone leaves or joins a game or makes a move
- Correlation: Uses methods of InGameWebsocketService, GameLogicService and GameService classes
- Main file: [InGameWebsocketController.java](https://github.com/sopra-fs22-group-29/brandi-server/blob/master/src/main/java/ch/uzh/ifi/hase/soprafs22/controller/InGameWebsocketController.java)


## Getting started
### Spring Boot

#### Setup with your IDE of choice

Download your IDE of choice: (e.g., [Eclipse](http://www.eclipse.org/downloads/), [IntelliJ](https://www.jetbrains.com/idea/download/)), [Visual Studio Code](https://code.visualstudio.com/) and make sure Java 15 is installed on your system (for Windows-users, please make sure your JAVA_HOME environment variable is set to the correct version of Java).

1. File -> Open... -> brandi-server
2. Accept to import the project as a `gradle project`

To build right click the `build.gradle` file and choose `Run Build`

#### VS Code
The following extensions will help you to run it more easily:
-   `pivotal.vscode-spring-boot`
-   `vscjava.vscode-spring-initializr`
-   `vscjava.vscode-spring-boot-dashboard`
-   `vscjava.vscode-java-pack`
-   `richardwillis.vscode-gradle`

**Note:** You'll need to build the project first with Gradle, just click on the `build` command in the _Gradle Tasks_ extension. Then check the _Spring Boot Dashboard_ extension if it already shows `soprafs22` and hit the play button to start the server. If it doesn't show up, restart VS Code and check again.

### Building with Gradle

You can use the local Gradle Wrapper to build the application.
-   macOS: `./gradlew`
-   Linux: `./gradlew`
-   Windows: `./gradlew.bat`

More Information about [Gradle Wrapper](https://docs.gradle.org/current/userguide/gradle_wrapper.html) and [Gradle](https://gradle.org/docs/).

#### Build

```bash
./gradlew build
```

#### Run

```bash
./gradlew bootRun
```

#### Test

```bash
./gradlew test
```

#### Development Mode

You can start the backend in development mode, this will automatically trigger a new build and reload the application
once the content of a file has been changed and you save the file.

Start two terminal windows and run:

`./gradlew build --continuous`

and in the other one:

`./gradlew bootRun`

If you want to avoid running all tests with every change, use the following command instead:

`./gradlew build --continuous -xtest`


### Debugging

If something is not working and/or you don't know what is going on. We highly recommend that you use a debugger and step
through the process step-by-step.

To configure a debugger for SpringBoot's Tomcat servlet (i.e. the process you start with `./gradlew bootRun` command),
do the following:

1. Open Tab: **Run**/Edit Configurations
2. Add a new Remote Configuration and name it properly
3. Start the Server in Debug mode: `./gradlew bootRun --debug-jvm`
4. Press `Shift + F9` or the use **Run**/Debug"Name of your task"
5. Set breakpoints in the application where you need it
6. Step through the process one step at a time

### Release
The project is automatically deployed after every change of the master branch, therefore we implemented a requirement, 
such that each change must first be reviewed and approved by at least one other developer.

## External Dependencies

The project implements the [deckofcardsapi](https://deckofcardsapi.com) to manage the cards used for the game.<br>
Find it on Github: https://github.com/crobertsbmw/deckofcards



## Roadmap
- Exchange a card with your teammate everytime you get 6 cards
- Ranking of all users and played games
- Implement the Joker card
- Alternative game modes:
    - time limited games
    - random actions on board
    - even cooler animations

## Authors
- [Elena Graf](https://github.com/ElenaGrafUZH)
- [Eric Léger](https://github.com/EriCreator)
- [Szymon Modrzynski](https://github.com/shmnrr)
- [Dominik Sidler](https://github.com/SidlerD)

## License
MIT License

Copyright (c) [2022] [Elena Graf, Eric Léger, Szymon Modrzynski, Dominik Sidler]

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.

