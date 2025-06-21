package com.example.neweggs_ems1a

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.MutableData
import com.google.firebase.database.Transaction
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.util.*

class QuizResults : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_results)

        val score = intent.getIntExtra("SCORE", 0)
        val total = intent.getIntExtra("TOTAL", 10)
        val username = intent.getStringExtra("USERNAME") ?: ""
        val points = score * 2

        // Display results
        findViewById<TextView>(R.id.scoreText).text =
            "You scored $score out of $total\n" +
                    "Points earned: $points"

        // Save points to Firebase
        savePointsToFirebase(username, points)

        findViewById<Button>(R.id.retryBtn).setOnClickListener {
            val intent = Intent(this, QuizPage::class.java).apply {
                putExtra("USERNAME", username)
            }
            startActivity(intent)
            finish()
        }

        findViewById<Button>(R.id.homeBtn).setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java).apply {
                putExtra("USERNAME", username)
                flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            }
            startActivity(intent)
            finish()
        }
    }

    private fun savePointsToFirebase(username: String, points: Int) {
        if (username.isEmpty()) return

        val database = Firebase.database
        val userRef = database.getReference("E-Ms1a/$username")

        // Get current date
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val currentDate = dateFormat.format(Date())

        // Save points with timestamp
        val pointsData = hashMapOf(
            "points" to points,
            "date" to currentDate,
            "timestamp" to System.currentTimeMillis()
        )

        // Update total points
        userRef.child("pointsHistory").push().setValue(pointsData)

        userRef.child("totalPoints").runTransaction(object : Transaction.Handler {
            override fun doTransaction(mutableData: MutableData): Transaction.Result {
                val currentPoints = mutableData.getValue(Int::class.java) ?: 0
                mutableData.value = currentPoints + points
                return Transaction.success(mutableData)
            }

            override fun onComplete(error: DatabaseError?, committed: Boolean, data: DataSnapshot?) {
                if (error != null) {
                    // Handle error if needed
                }
            }
        })
    }
}