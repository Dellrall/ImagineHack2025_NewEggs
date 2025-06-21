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

1. User opens the app and registers an account.
   ![main1](https://github.com/user-attachments/assets/20d0ed7e-a075-4f14-bfeb-52597575cb45)
2. During registration, user must provide their first and last names, a unique username and password and their nationality.
   ![reg3](https://github.com/user-attachments/assets/3e8d31eb-12fd-4138-8fab-0e02381ed528)
3. After registration, user will login with their username and password. Once a successful login takes place, the user will be guided to the main menu.
4. In the main menu, the user is able to choose between 4 options which includes "States", "Our Flag", "Quiz" and "Redeem". The user could also view the profile by pressing the Profile Icon in the top left corner.
   ![Home1](https://github.com/user-attachments/assets/0f688051-dac5-4e9f-9162-7e0da7ad81bf)
5. If the user presses "States", the user will be given a Recycler View to scroll through and choose a state to learn it's culture, favourite foods and traditions.
   ![states1](https://github.com/user-attachments/assets/be42d9d1-24f3-463c-bdb5-1a07626dcb4f)
6. If the user presses "Our Flag", the user will be able to learn the meaning behind each colour of our flag, the Jalur Gemilang.
   ![flags1](https://github.com/user-attachments/assets/4151faef-94b2-41b4-893b-d65875389e7a)
7. If the user presses "Quiz", the user will be able to test their memory and gain points.
    ![quiz1](https://github.com/user-attachments/assets/7d505bee-cca8-4ebb-9143-6185b711f734)
8. If the user presses "Redeem", the user will be able to spend their points to redeem gifts.
    ![redeem1](https://github.com/user-attachments/assets/a79a4354-eb5c-42f4-9525-692556122e0b)
9. The user will be able to directly exit the app or logout from their account in the main menu.

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
