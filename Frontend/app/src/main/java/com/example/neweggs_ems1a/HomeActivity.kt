package com.example.neweggs_ems1a

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class HomeActivity : AppCompatActivity() {
    @SuppressLint("WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home)
        val exitButton = findViewById<Button>(R.id.home_exit)
        val logout = findViewById<Button>(R.id.logoutBtn)
        val profilebtn = findViewById<ImageButton>(R.id.homeprofile_btn)
        val statesbtn = findViewById<ImageButton>(R.id.statesButton)
        val flagsbtn = findViewById<ImageButton>(R.id.FlagsButton)
        val quizbtn = findViewById<ImageButton>(R.id.QuizButton)
        val redeembtn = findViewById<ImageButton>(R.id.RedeemButton)

        exitButton.setOnClickListener {
            finishAffinity()
        }

        profilebtn.setOnClickListener(){
            val intent = Intent(this, ProfilePage::class.java)
            startActivity(intent)
        }

        statesbtn.setOnClickListener(){
            val intent = Intent(this, MalaysianStates::class.java)
            startActivity(intent)
        }

        flagsbtn.setOnClickListener(){
            val intent = Intent(this, FlagPage::class.java)
            startActivity(intent)
        }

        quizbtn.setOnClickListener(){
            val intent = Intent(this, QuizPage::class.java)
            startActivity(intent)
        }

        redeembtn.setOnClickListener(){
            val intent = Intent(this, RedeemPage::class.java)
            startActivity(intent)
        }

        val fname = intent.getStringExtra("FIRST_NAME") ?: ""
        val lname = intent.getStringExtra("LAST_NAME") ?: ""

        val welcomeMessage = "Welcome, $fname $lname"
        findViewById<TextView>(R.id.mainmenutext).text = welcomeMessage

        // Get username from intent or shared preferences
        val username = intent.getStringExtra("USERNAME") ?:
        getSharedPreferences("Username", MODE_PRIVATE).getString("USERNAME", "") ?: ""

        // Pass username to quiz activity
        quizbtn.setOnClickListener {
            val intent = Intent(this, QuizPage::class.java).apply {
                putExtra("USERNAME", username)
            }
            startActivity(intent)
        }

        redeembtn.setOnClickListener {
            val intent = Intent(this, RedeemPage::class.java).apply {
                putExtra("USERNAME", username)
            }
            startActivity(intent)
        }

        logout.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            finish()
        }
    }

}