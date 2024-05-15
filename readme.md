# Marvel-App

This is a Java application that allows users to search for information about Marvel characters and comics. It utilizes caching mechanisms for improved performance and incorporates features like report sending via email and background music control.

## Structure

The project is organized into the following directories:

- `App.java`: Main class to run the application.
- `model`: Contains classes related to data models and API interaction.
  - `AppEngine.java`: Core engine managing API requests and caching.
  - `Request.java`: Class for handling API requests.
  - `cache`: Subdirectory for caching-related classes.
    - `CacheManager.java`: Manages caching operations.
    - `CharacterCache.java`: Handles caching of character data.
    - `ComicCache.java`: Handles caching of comic data.
  - `parser`: Subdirectory for parsing-related classes.
    - `marvel`: Subdirectory for Marvel API parsing.
    - `sendgrid`: Subdirectory for SendGrid API parsing.
- `view`: Contains classes related to the user interface.
  - Various `.java` files for different UI components.

## How to Use

- **Character Search Bar**: Press ENTER for suggestions based on input. Cached characters will prompt to use cache. Entering exact character names retrieves data from the API.
- **Search Button**: Click to search for character information. Utilizes cache if available, otherwise queries the API.
- **Collection List**: Double-click to browse a collection.
- **Bread Crumb Bar**: Click crumbs to explore recent history. Scroll left/right if bar fills up.
- **Send Report Button**: Opens a form to send character information via email. All fields must be filled for submission.
- **Help Menu Bar**: Displays application details.
- **Play/Pause BGM Button**: Controls background music playback.
- **Clear Cache Button**: Clears all cached data.


# Installation Guide

Follow these steps to set up and run the Marvel-App project on your local machine:

## Prerequisites

- Java Development Kit (JDK) 17 or later installed on your system.
- Gradle build tool installed.

## Steps

1. **Clone the Repository**: Clone the Marvel-App repository to your local machine using Git.

    ```bash
    git clone <repository_url>
    ```

2. **Navigate to Project Directory**: Open a terminal and change your directory to the root of the cloned repository.

    ```bash
    cd marvel-app
    ```

3. **Configure Gradle**: Open the `build.gradle` file in a text editor and ensure that the dependencies and plugins are correctly configured as shown in the provided snippet.

4. **Build the Project**: Run the following Gradle command to build the project.

    ```bash
    gradle build
    ```

5. **Run the Application**: Execute the following Gradle command to run the application.

    ```bash
    gradle run
    ```

6. **Explore the Application**: Once the application is running, you can interact with it using the provided user interface features.

7. **(Optional) Run Tests**: If you want to run the tests, use the following Gradle command.

    ```bash
    gradle test
    ```
