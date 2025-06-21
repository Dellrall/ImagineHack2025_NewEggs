package com.example.neweggs_ems1a

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.neweggs_ems1a.databinding.ActivityRedeemBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class RedeemPage : AppCompatActivity() {

    private lateinit var binding: ActivityRedeemBinding
    private var currentPoints = 0
    private val giftList = mutableListOf<GiftItem>()
    private var username: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRedeemBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Get username from intent or shared preferences
        username = getUsernameFromPreviousScreen() // Implement this based on your app flow

        // Initialize gift items
        initializeGifts()

        // Set up RecyclerView
        val adapter = GiftAdapter(giftList) { gift ->
            if (currentPoints >= gift.pointsRequired) {
                redeemGift(gift)
            } else {
                Toast.makeText(this, "Not enough points!", Toast.LENGTH_SHORT).show()
            }
        }
        val exitButton = findViewById<Button>(R.id.buttonExit)
        exitButton.setOnClickListener {
            finish()
        }

        binding.redeemRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.redeemRecyclerView.adapter = adapter

        // Load user data from Firebase
        loadUserData()
    }

    private fun getUsernameFromPreviousScreen(): String {
        // Option 1: Get from Intent extras
        return intent.getStringExtra("USERNAME") ?: "defaultUser"

        // Option 2: Get from SharedPreferences (if you stored it earlier)
        // val prefs = getSharedPreferences("AppPrefs", MODE_PRIVATE)
        // return prefs.getString("username", "defaultUser") ?: "defaultUser"
    }

    private fun initializeGifts() {
        giftList.apply {
            add(GiftItem("Coffee Voucher", 100, R.drawable.coffeecup))
            add(GiftItem("Movie Ticket", 250, R.drawable.tickets))
            add(GiftItem("Amazon Gift Card", 500, R.drawable.redeem))
            add(GiftItem("Wireless Earbuds", 1000, R.drawable.headphones))
            add(GiftItem("Smart Watch", 2000, R.drawable.smartwatch))
        }
    }

    private fun loadUserData() {
        val databaseRef = FirebaseDatabase.getInstance().getReference("E-Ms1a/$username")

        databaseRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                // Get both username and points in one query
                currentPoints = snapshot.child("totalPoints").getValue(Int::class.java) ?: 0
                binding.pointsTextView.text = "Your Points: $currentPoints"

                // If you want to verify the username exists
                if (!snapshot.exists()) {
                    Toast.makeText(this@RedeemPage, "User data not found", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@RedeemPage, "Failed to load data: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun redeemGift(gift: GiftItem) {
        val databaseRef = FirebaseDatabase.getInstance().getReference("E-Ms1a/$username")

        val newPoints = currentPoints - gift.pointsRequired

        databaseRef.child("totalPoints").setValue(newPoints)
            .addOnSuccessListener {
                recordRedemption(gift)
                currentPoints = newPoints
                binding.pointsTextView.text = "Your Points: $currentPoints"
                Toast.makeText(this, "Redeemed ${gift.name}!", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Failed: ${it.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun recordRedemption(gift: GiftItem) {
        val redemptionRef = FirebaseDatabase.getInstance()
            .getReference("E-Ms1a/$username/redemptions")
            .push()

        redemptionRef.setValue(mapOf(
            "gift" to gift.name,
            "points" to gift.pointsRequired,
            "date" to System.currentTimeMillis()
        ))
    }
}

data class GiftItem(
    val name: String,
    val pointsRequired: Int,
    val imageRes: Int
)