package com.example.neweggs_ems1a

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class FlagPage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flags)

        val exitButton = findViewById<Button>(R.id.exitFlagBtn)
        exitButton.setOnClickListener {
            finish()
        }

        val flagElements = listOf(
            FlagElement(
                "Red",
                "Symbolizes courage and bravery of Malaysians",
                R.drawable.flag_red,
                R.color.flag_red
            ),
            FlagElement(
                "Blue",
                "Represents unity among Malaysian people",
                R.drawable.flag_blue,
                R.color.flag_blue
            ),
            FlagElement(
                "Yellow",
                "Signifies royal sovereignty and constitutional monarchy",
                R.drawable.flag_yellow,
                R.color.flag_yellow
            ),
            FlagElement(
                "White",
                "Denotes purity and cleanliness",
                R.drawable.flag_white,
                R.color.flag_white
            ),
            FlagElement(
                "Crescent",
                "Represents Islam as the official religion",
                R.drawable.flag_crescent,
                R.color.flag_white
            ),
            FlagElement(
                "14-Pointed Star",
                "Symbolizes unity of 13 states and federal territories",
                R.drawable.flag_star,
                R.color.flag_yellow
            )
        )

        val recyclerView = findViewById<RecyclerView>(R.id.flagRecyclerView)
        recyclerView.layoutManager = GridLayoutManager(this, 2)
        recyclerView.adapter = FlagAdapter(flagElements)

    }

}

data class FlagElement(
    val name: String,
    val meaning: String,
    val imageResId: Int,
    val colorResId: Int
)