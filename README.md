
# Album Explorer App

Album Explorer is an Android application that allows users to explore and view albums and images. The app consists of two main screens: the Profile screen, which displays user information and a list of albums, and the Album Details screen, which displays images in an Instagram-like grid with search functionality. Additionally, the app provides a bonus feature to open and view images in a separate image viewer with zooming and sharing capabilities.

## Features

- **Profile Screen:**
  - Display user's name and address.
  - List all albums associated with the user.
  - Fetch user data from the User API endpoint.
  - Fetch albums data by user ID from the Albums API endpoint.

- **Album Details Screen:**
  - Display images in an Instagram-like grid.
  - Filter images by title using a search bar.
  - Fetch images by album ID from the Photos API endpoint.
  - Implement smooth transitions and animations.

- **Unit Testing:**
  - Includes unit tests to ensure ViewModel logic correctness.
  - Mocks data sources to simulate API requests.
  - Utilizes testing Coroutines for asynchronous operations.

- **Error Handling:**
  - Implements appropriate error handling and UI feedback for network errors or empty responses.
  - Ensures users receive feedback in case of errors.

- **UI and User Experience:**
  - Designed and styled using Jetpack Compose to create an attractive and user-friendly interface.
  - Implements smooth transitions and animations to enhance the user experience.

- **Bonus Feature:**
  - Allows users to open images in a separate image viewer.
  - Supports zooming and sharing functionality.

## Software Stack

- Kotlin
- MVVM Architecture
- Android JetPack
- Retrofit 2
- Hilt Dagger (for dependency injection)
- Coroutines (for handling asynchronous operations)

## API Endpoints

- **Base URL:** https://jsonplaceholder.typicode.com
- **User:** GET /users (pick any user to start with)
- **Albums:** GET /albums (pass user ID as a parameter)
- **Photos:** GET /photos (pass album ID as a parameter)

## Usage

To build and run the Album Explorer app, you can follow these steps:

1. Clone the repository to your local machine.
2. Open the project in Android Studio.
3. Build and run the app on an Android emulator or physical device.

## Unit Testing

The app includes unit tests to validate the correctness of ViewModel logic and ensure proper error handling.
To run unit tests:

1. Navigate to the `app` module.
2. Right-click on the `src/test` directory and select "Run Tests in 'app'."
