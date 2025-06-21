package com.example.neweggs_ems1a

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.Firebase
import com.google.firebase.database.database



class ProfilePage : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.profile_page)

        val exitButton = findViewById<Button>(R.id.profile_backbtn)
        val welcometxt = findViewById<TextView>(R.id.profile_username)
        val RegistrationDatetxt = findViewById<TextView>(R.id.profile_datereg)
        val RegistrationTimetxt = findViewById<TextView>(R.id.profile_timereg)


        fun loadStatistics() {
            val sharedUser = getSharedPreferences("Username", Context.MODE_PRIVATE)
            val username = sharedUser.getString("USERNAME", "") ?: ""

            val m_sharedPreferences = getSharedPreferences("Maths Quiz Stats", Context.MODE_PRIVATE)
            val m_totalAttempts = m_sharedPreferences.getInt("${username}_Attempts", 0)
            val m_correctCount = m_sharedPreferences.getInt("${username}_Correct_Attempts", 0)
            val m_incorrectCount = m_sharedPreferences.getInt("${username}_Incorrect_Attempts", 0)

            val s_sharedPreferences = getSharedPreferences("Science Quiz Stats", Context.MODE_PRIVATE)
            val s_totalAttempts = s_sharedPreferences.getInt("${username}_Attempts", 0)
            val s_correctCount = s_sharedPreferences.getInt("${username}_Correct_Attempts", 0)
            val s_incorrectCount = s_sharedPreferences.getInt("${username}_Incorrect_Attempts", 0)


        }

        loadStatistics()

        exitButton.setOnClickListener {
            finish()
        }
        

        val sharedUser = getSharedPreferences("Username", Context.MODE_PRIVATE)
        val username = sharedUser.getString("USERNAME", "")
        val welcomeMessage = "Welcome to your profile page, $username!"
        welcometxt.append(welcomeMessage)


        if (username != null && username.isNotEmpty()) {
            val database = Firebase.database
            val getUser = database.getReference("E-Ms1a/$username")

            getUser.get()
                .addOnSuccessListener { dataSnapshot ->
                    if (dataSnapshot.exists()) {
                        val user = dataSnapshot.getValue(MainActivity.User::class.java)
                        if (user != null) {
                            RegistrationDatetxt.text = "${user.registrationDate}"
                            RegistrationTimetxt.text = "${user.registrationTime}"
                        }
                    }
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Failed to load user data!", Toast.LENGTH_SHORT).show()
                }
        }


    }


}


