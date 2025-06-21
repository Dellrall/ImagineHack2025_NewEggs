# E-Ms1a

Malaysian Culture digitalized — promoting our proudest local Malaysian heritage through a mobile application (Android).

## Team Members
- Lye Wei Lun
- Koh Huai Yu
- Sarvez Elangovan

## Challenge
**Challenge 1: Digital Heritage & Tourism**

## Tech Stack

- **Android Studio** — Application development environment.
- **Kotlin** — Main programming language for app logic.
- **Firebase** — Backend for authentication, real-time database, and cloud storage.
- **Canva** — UI/UX and visual content design.
- **DeepSeek R1 (Generative AI)** — Likely for content generation.
- **IbisPaintX** — Logo and graphic design.
- **Github** — Version control and collaboration.

## Main Features (from Kotlin Code)

### 1. **User Profile & Authentication**
- User profile information is stored and managed with Firebase Realtime Database.
- User registration date/time and points history are displayed in the ProfilePage.
- SharedPreferences is used for storing user-specific data locally (e.g., quiz stats).

### 2. **Home & Navigation**
- The `HomeActivity` acts as the central hub, navigating to different pages (Profile, State Info, Flags, Quiz, Redeem Points).
- Displays a personalized welcome message using user's first and last name.

### 3. **Quiz Functionality**
- The `QuizPage` and `QuizResults` manage quiz flow, scoring, and result display.
- Quiz questions cover Malaysian culture, heritage, and local knowledge.
- Users can only attempt the quiz once per day, enforced via SharedPreferences.
- Points are calculated based on correct answers and saved to Firebase.
- Quiz statistics (total attempts, correct/incorrect attempts, last attempt date) are tracked both locally and in the cloud.
- Results and points history, including timestamps, are stored for each user.

### 4. **Malaysian States Information**
- The `MalaysianStates` activity contains data models representing each Malaysian state, including cultural highlights, popular foods, and traditions.
- State data is encapsulated in the `State` Kotlin data class (using `@Parcelize` for easy passing between activities).

### 5. **Redeem & Rewards**
- Users can redeem points for rewards, tracked in the app (details likely in `RedeemPage`).

### 6. **Data Models**
- `State`: Represents states with fields for name, capital, location, tradition, food, cultural note, and image.
- `QuizQuestion` & `QuizStats`: Encapsulate quiz content and user performance.

### 7. **Tech Stack in the Build System**
- Uses `build.gradle.kts` (Kotlin DSL) for project configuration.
- Integrates libraries for AndroidX, Material Components, Firebase, and testing (JUnit, Espresso).
- Enables ViewBinding and DataBinding for robust UI development.

## Usage Instructions

> **Note:** Please refer to the application or contact the project team for more detailed usage instructions as the current README does not include step-by-step setup or usage details.

---

## Example Snippets

**Quiz Question Example:**
```kotlin
QuizQuestion(
    "Which state is known for its traditional Mak Yong theater?",
    listOf("Kelantan", "Terengganu", "Perak", "Pahang"),
    0 // Correct: Kelantan
)
```

**Profile Data Fetch:**
```kotlin
val database = Firebase.database
val getUser = database.getReference("E-Ms1a/$username")
getUser.get().addOnSuccessListener { ... }
```

**Quiz Results Saving:**
```kotlin
userRef.child("pointsHistory").push().setValue(pointsData)
userRef.child("totalPoints").runTransaction(object : Transaction.Handler { ... })
```

---

## Contact
For contributions, suggestions, or inquiries, please open an issue or contact the team via GitHub.
