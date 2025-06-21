package com.example.neweggs_ems1a

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.Tasks
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.MutableData
import com.google.firebase.database.Transaction
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class QuizPage : AppCompatActivity() {

    private lateinit var questionText: TextView
    private lateinit var optionsGroup: RadioGroup
    private lateinit var submitBtn: Button
    private lateinit var questionCounter: TextView
    private lateinit var scoreText: TextView
    private lateinit var sharedPref: SharedPreferences

    private var currentQuestion = 0
    private var score = 0
    private lateinit var questions: List<QuizQuestion>
    private lateinit var username: String

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)

        questionText = findViewById(R.id.questionText)
        optionsGroup = findViewById(R.id.optionsGroup)
        submitBtn = findViewById(R.id.submitBtn)
        questionCounter = findViewById(R.id.questionCounter)
        scoreText = findViewById(R.id.scoreText)
        sharedPref = getSharedPreferences("QuizAttempt", MODE_PRIVATE)

        // Get username from intent or shared preferences
        username = intent.getStringExtra("USERNAME") ?:
                getSharedPreferences("Username", MODE_PRIVATE).getString("USERNAME", "") ?: ""

        // Check if user can attempt quiz today
        if (!canAttemptQuiz()) {
            Toast.makeText(this, "You can only attempt the quiz once per day. Come back tomorrow!",
                Toast.LENGTH_LONG).show()
            finish()
            return
        }

        questions = listOf(
                // Flag questions
                QuizQuestion(
                    "What does the blue color in the Malaysian flag represent?",
                    listOf(
                        "Unity of the people",
                        "The sea surrounding Malaysia",
                        "Peace and harmony",
                        "The federal government"
                    ),
                    0
                ),
                QuizQuestion(
                    "What does the yellow color in the Malaysian flag symbolize?",
                    listOf(
                        "The sun",
                        "Royalty and sovereignty",
                        "Wealth and prosperity",
                        "The golden beaches"
                    ),
                    1
                ),
                QuizQuestion(
                    "What does the 14-pointed star on the flag represent?",
                    listOf(
                        "14 major islands",
                        "14 ethnic groups",
                        "13 states + federal territories",
                        "14 founding fathers"
                    ),
                    2
                ),
                QuizQuestion(
                    "What does the crescent moon on the flag symbolize?",
                    listOf(
                        "Islam as the official religion",
                        "The new moon festival",
                        "Malaysia's tropical nights",
                        "Progress and growth"
                    ),
                    0
                ),

                // State culture / heritage questions
                QuizQuestion(
                    "Which state is known for its traditional Mak Yong theater?",
                    listOf("Kelantan", "Terengganu", "Perak", "Pahang"),
                    0
                ),
                QuizQuestion(
                    "What is the famous traditional dish from Penang?",
                    listOf("Nasi Lemak", "Char Kway Teow", "Rendang", "Satay"),
                    1
                ),
                QuizQuestion(
                    "Which state is famous for its 'longhouses'?",
                    listOf("Sabah", "Sarawak", "Johor", "Perlis"),
                    1
                ),
                QuizQuestion(
                    "What is the traditional fabric of Terengganu?",
                    listOf("Batik", "Songket", "Kain Tenun", "Pua Kumbu"),
                    1
                ),
                QuizQuestion(
                    "Which state is known for its 'Adat Perpatih' matrilineal system?",
                    listOf("Melaka", "Negeri Sembilan", "Selangor", "Perak"),
                    1
                ),
                QuizQuestion(
                    "What is the famous harvest festival celebrated in Sabah?",
                    listOf("Gawai", "Kaamatan", "Hari Raya", "Deepavali"),
                    1
                ),
                QuizQuestion(
                    "Which state is famous for its Portuguese-Eurasian community?",
                    listOf("Penang", "Melaka", "Johor", "Kedah"),
                    1
                ),
                QuizQuestion(
                    "What is the traditional musical instrument of the Orang Asli?",
                    listOf("Sape", "Gamelan", "Sompoton", "Rebana"),
                    2
                ),
                QuizQuestion(
                    "Which state is known as the 'Land of the Hornbills'?",
                    listOf("Sabah", "Sarawak", "Perak", "Pahang"),
                    1
                ),
                QuizQuestion(
                    "What is the famous traditional dance of Sarawak?",
                    listOf("Joget", "Zapin", "Ngajat", "Kuda Kepang"),
                    2
                ),
                QuizQuestion(
                    "Which state is famous for its 'Batik' industry?",
                    listOf("Kelantan", "Terengganu", "Johor", "Kedah"),
                    0
                ),
                QuizQuestion(
                    "What is the traditional house architecture of Negeri Sembilan called?",
                    listOf("Rumah Melayu", "Rumah Gadang", "Rumah Limas", "Rumah Kutai"),
                    1
                ),
                QuizQuestion(
                    "Which state is known for its 'Kuda Kepang' dance?",
                    listOf("Johor", "Kelantan", "Terengganu", "Perak"),
                    0
                ),
                QuizQuestion(
                    "What is the famous traditional food from Melaka?",
                    listOf("Nasi Lemak", "Satay Celup", "Roti Canai", "Laksa Johor"),
                    1
                ),
                QuizQuestion(
                    "Which state is known for its 'Pesta Menuai' (Harvest Festival)?",
                    listOf("Sabah", "Sarawak", "Kedah", "Perlis"),
                    0
                ),
                QuizQuestion(
                    "What is the traditional boat of Terengganu called?",
                    listOf("Perahu", "Sampan", "Jong", "Kapal"),
                    2
                )
            // ... (include all your other questions)
        )

        loadQuestion()

        submitBtn.setOnClickListener {
            if (optionsGroup.checkedRadioButtonId == -1) {
                Toast.makeText(this, "Please select an answer", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Record attempt on first question submission
            if (currentQuestion == 0) {
                recordQuizAttempt()
            }

            val selectedOption = findViewById<RadioButton>(optionsGroup.checkedRadioButtonId)
            val userAnswer = optionsGroup.indexOfChild(selectedOption)

            if (userAnswer == questions[currentQuestion].correctAnswer) {
                score++
                Toast.makeText(this, "Correct!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Wrong! Correct answer was: ${questions[currentQuestion].options[questions[currentQuestion].correctAnswer]}",
                    Toast.LENGTH_LONG).show()
            }

            saveQuizAttempt(userAnswer == questions[currentQuestion].correctAnswer)

            currentQuestion++
            if (currentQuestion < questions.size) {
                loadQuestion()
            } else {
                showResults()
            }
        }
    }

    private fun canAttemptQuiz(): Boolean {
        val today = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())

        val database = Firebase.database
        val statsRef = database.getReference("E-Ms1a/$username/quizStats/lastAttemptDate")

        try {
            val dataSnapshot = Tasks.await(statsRef.get())
            val firebaseLastAttempt = dataSnapshot.getValue(String::class.java) ?: ""
            return firebaseLastAttempt != today
        } catch (e: Exception) {
            Log.e("QuizPage", "Error checking last attempt date", e)
            return true  // Allow attempt if there's an error checking
        }
    }

    private fun recordQuizAttempt() {
        val today = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())

        // Update local storage
        with(sharedPref.edit()) {
            putString("lastAttemptDate", today)
            apply()
        }

        // Update Firebase
        val database = Firebase.database
        val statsRef = database.getReference("E-Ms1a/$username/quizStats")

        statsRef.runTransaction(object : Transaction.Handler {
            override fun doTransaction(mutableData: MutableData): Transaction.Result {
                var stats = mutableData.getValue(QuizStats::class.java) ?: QuizStats()
                stats.lastAttemptDate = today
                mutableData.value = stats
                return Transaction.success(mutableData)
            }

            override fun onComplete(error: DatabaseError?, committed: Boolean, data: DataSnapshot?) {
                if (error != null) {
                    Log.e("QuizPage", "Failed to update last attempt date", error.toException())
                }
            }
        })
    }

    private fun loadQuestion() {
        optionsGroup.clearCheck()
        questionText.text = questions[currentQuestion].question
        questionCounter.text = "Question ${currentQuestion + 1}/${questions.size}"
        scoreText.text = "Score: $score"

        for (i in 0 until optionsGroup.childCount) {
            (optionsGroup.getChildAt(i) as RadioButton).text = questions[currentQuestion].options[i]
        }
    }

    private fun showResults() {
        val intent = Intent(this, QuizResults::class.java).apply {
            putExtra("SCORE", score)
            putExtra("TOTAL", questions.size)
            putExtra("USERNAME", username)
        }
        startActivity(intent)
        finish()
    }

    private fun saveQuizAttempt(isCorrect: Boolean) {
        val database = Firebase.database
        val quizRef = database.getReference("E-Ms1a/$username/quizAttempts")
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

        val attempt = hashMapOf(
            "question" to questions[currentQuestion].question,
            "userAnswer" to findViewById<RadioButton>(optionsGroup.checkedRadioButtonId).text,
            "correctAnswer" to questions[currentQuestion].options[questions[currentQuestion].correctAnswer],
            "isCorrect" to isCorrect,
            "timestamp" to System.currentTimeMillis(),
            "attemptDate" to dateFormat.format(Date())
        )

        quizRef.push().setValue(attempt)

        // Update statistics
        val statsRef = database.getReference("E-Ms1a/$username/quizStats")
        statsRef.runTransaction(object : Transaction.Handler {
            override fun doTransaction(mutableData: MutableData): Transaction.Result {
                var stats = mutableData.getValue(QuizStats::class.java) ?: QuizStats()
                stats.totalAttempts++
                if (isCorrect) stats.correctAttempts++ else stats.incorrectAttempts++
                mutableData.value = stats
                return Transaction.success(mutableData)
            }

            override fun onComplete(error: DatabaseError?, committed: Boolean, data: DataSnapshot?) {
                if (error != null) {
                    Toast.makeText(this@QuizPage, "Failed to save stats", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
}

data class QuizQuestion(
    val question: String,
    val options: List<String>,
    val correctAnswer: Int
)

data class QuizStats(
    var totalAttempts: Int = 0,
    var correctAttempts: Int = 0,
    var incorrectAttempts: Int = 0,
    var lastAttemptDate: String = ""
)