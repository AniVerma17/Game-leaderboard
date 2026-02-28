# Game leaderboard

Android app to show a game leaderboard with real-time updates for players' scores and rankings.
Built for the Android Developer assignment.

## Run instructions

- Clone the repository from `https://github.com/example/Game-leaderboard.git`
- Open the project in Android Studio.
- Let Gradle sync and build finish.
- Select the `app` run configuration.
- Run the project on an emulator or a physical device.

To build the project from the command line, run the following command:

```bash
./gradlew assemble
```

## Project Modules

- `:app`: This is the application module which contains the codebase for the Android app to perform two main tasks:
  - Implement the UI to show the game leaderboard to the users.
  - Integrate the other library modules in the project for leaderboard functionality.
- `:leaderboard`: This library module has two key responsibilities: 
  - Implement the core logic to generate the leaderboard from the list of players and their scores.
  - Implement the components to fetch the list of players and receive their scores' updates in real-time.
- `:scoregen`: This library module is responsible for providing a backend to 
  - Generate the players' list and store their scores.
  - Update the scores of the players randomly at random time intervals.
  - Expose the list of players' usernames and their live score updates.

## Architectural overview

This project follows a modular, layered architecture that separates concerns and promotes code reusability. The architecture is composed of three main layers:

- **Presentation Layer**: This layer is responsible for presenting the leaderboard to the user.

- **Domain Layer**: This layer contains the business logic of the application. It is responsible for fetching the data from the data layer, processing it, and providing it to the UI layer.

- **Data Layer**: This layer is responsible for providing the data to the application.

The dependency relationships between the project modules can be shown as follows:

```
:app -> :leaderboard -> :scoregen
```

- The `:app` module contains the presentation layer entirely, built using Jetpack Compose for UI and implements MVVM pattern with Jetpack ViewModel.
  - Jetpack Compose is preferred for UI components, due to its declarative and functional state-driven UI paradigm, which enables to write more robust UI with minimal code.
  - The `LeaderboardViewModel` collects the latest leaderboard updates from the `GetLeaderboardUseCase` and exposes them to the UI as a state flow. This helps to preserve the UI state across configuration changes like screen rotation or device theme changes etc.
  - The MVVM pattern ensures that no view components are referenced in the components that can outlive them, which prevents several memory leaks.
  - To reduce unnecessary recompositions and UI performance load, the state flow collection from viewmodel is stopped when the app goes into background, and restarted when the app comes back into foreground.
  - Techniques like state hoisting, and use of stable parameters in the composables are also implemented to reduce unnecessary recompositions.
- The `:leaderboard` module contains the domain and data layers of the application.
  - The domain layer consists of
    - `RankedUser` class to represent a user domain object with its username, score and rank.
    - `UserScoreRepository` interface to define the repository type to fetch the users' data from.
    - `GetLeaderboardUseCase` class to encapsulate the logic to sort and rank users for the leaderboard, and provide the leaderboard updates by exposing a flow.
  - The data layer consists of
    - `UserScoreDto` class to represent a user DTO with its username and score.
    - `InMemoryScoregenRepository` class to provide a concrete implementation of the `UserScoreRepository` that fetches the users' data stream generated from the `:scoregen` module.
  - The logic to sort and rank the users is executed in a background thread via coroutine dispatchers (default dispatcher in this case), to avoid blocking the UI thread in case of very large list input.
- The `:scoregen` module contains the `LiveScoreDataSource` object that serves as the local backend implementation of the scoring system.
  - It contains the hardcoded mutable map of usernames and their current scores.
  - It runs an infinite loop which updates the score of a randomly selected user by 100 at random intervals between 500 and 2000 milliseconds.
  - It exposes a shared flow to emit the list of users with their live scores on each update.
  - This entire logic is executed away from the UI thread by using a custom coroutine scope with the default background dispatcher.

## Trade-offs

Following trade-offs are made in the project development due to time constraints:

- No separate UI state class was created for the leaderboard list UI. The `RankedUser` domain objects list is passed
from the viewmodel without any transformation.
- Only the core logic of the leaderboard is covered by local unit tests. No local or instrumentation tests are written as of now for other components like repositories, viewmodels or composables.
- No local file storage or database is implemented to store the users and their scores. Instead, the users' scores are started from zero in each app run, and are updated continuously until the app is terminated.

## Areas for future improvements
- Improve the handling of score updates for large number of users (10K, 100K) by sending only the data of modified scores from the data source instead of the entire list of users and their scores.
- Add more UI animations to indicate score updates when rankings remain unchanged.
- Add unit tests and UI tests to cover app components like UI composables, viewmodel etc.
- Implement persistent storage for user scores, preferably in a local SQLite database.