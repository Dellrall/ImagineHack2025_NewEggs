package com.example.neweggs_ems1a

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class StateDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_state_details)

        val state = intent.getParcelableExtra<State>("state")
        if (state == null) {
            Toast.makeText(this, "State information not available", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        // Set up views
        findViewById<TextView>(R.id.stateName).text = state.name
        findViewById<TextView>(R.id.stateCapital).text = "Capital: ${state.capital}"
        findViewById<TextView>(R.id.stateLocation).text = "Location: ${state.location}"
        findViewById<TextView>(R.id.stateFeatures).text = "Traditions: ${state.tradition}"
        findViewById<TextView>(R.id.stateAttractions).text = "Food: ${state.food}"
        findViewById<TextView>(R.id.stateCulturalNote).text = "Cultural Note: ${state.culturalNote}"
        findViewById<ImageView>(R.id.stateImage).setImageResource(state.imageResId)
        findViewById<ImageView>(R.id.stateImage2).setImageResource(
            when (state.name) {
                "Malacca" -> R.drawable.malaccaculture
                "Kedah" -> R.drawable.kedahculture
                "Kelantan" -> R.drawable.kelantanculture
                "Pahang" -> R.drawable.pahangculture
                "Johor" -> R.drawable.johorculture
                "Negeri Sembilan" -> R.drawable.nsculture
                else -> R.drawable.malaysiaflag
            }
        )

        findViewById<Button>(R.id.backButton).setOnClickListener { finish() }
    }
}