package com.example.neweggs_ems1a

import android.os.Bundle
import android.text.InputType
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.widget.*
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.util.*
import org.json.JSONObject

class RegisterActivity : AppCompatActivity() {

    private lateinit var nationsList: Spinner
    private val nationalities = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register)

        val backBtn = findViewById<Button>(R.id.regBackBtn)
        val edtPassword = findViewById<EditText>(R.id.passReg)
        val showPasswordCheckBox = findViewById<CheckBox>(R.id.checkBoxReg)
        nationsList = findViewById(R.id.nationalityList)
        
        loadNationalities()

        backBtn.setOnClickListener {
            finish()
        }

        // Set up checkbox listener for showing/hiding the password
        showPasswordCheckBox.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                edtPassword.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                edtPassword.transformationMethod = null
            } else {
                edtPassword.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                edtPassword.transformationMethod = PasswordTransformationMethod.getInstance()
            }
            // Move the cursor to the end of the text
            edtPassword.setSelection(edtPassword.text.length)
        }
    }

    private fun loadNationalities() {
        try {
            val inputStream = resources.openRawResource(R.raw.countries)
            val json = inputStream.bufferedReader().use { it.readText() }
            val jsonObject = JSONObject(json)
            val countriesArray = jsonObject.getJSONArray("countries")

            nationalities.add("Select Nationality")
            for (i in 0 until countriesArray.length()) {
                nationalities.add(countriesArray.getString(i))
            }

            val adapter = ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item,
                nationalities
            )
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            nationsList.adapter = adapter
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun register(view: View) {
        val editTextFirstName = findViewById<EditText>(R.id.firstNameReg)
        val editTextLastName = findViewById<EditText>(R.id.lastNameReg)
        val editTextUsername = findViewById<EditText>(R.id.userReg)
        val editTextPassword = findViewById<EditText>(R.id.passReg)
        val selectedNationality = nationsList.selectedItem.toString()

        // Validate all fields
        if (editTextFirstName.text.isEmpty()) {
            editTextFirstName.error = "First name is required!"
            return
        }

        if (editTextLastName.text.isEmpty()) {
            editTextLastName.error = "Last name is required!"
            return
        }

        if (editTextUsername.text.isEmpty()) {
            editTextUsername.error = "Username is required!"
            return
        }

        if (editTextPassword.text.isEmpty()) {
            editTextPassword.error = "Password is required!"
            return
        }

        if (selectedNationality == "Select Nationality") {
            Toast.makeText(this, "Please select your nationality", Toast.LENGTH_SHORT).show()
            return
        }

        val firstName = editTextFirstName.text.toString()
        val lastName = editTextLastName.text.toString()
        val username = editTextUsername.text.toString()
        val password = editTextPassword.text.toString()
        val currentDate = Date()
        val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())

        val registrationDate = dateFormat.format(currentDate)
        val registrationTime = timeFormat.format(currentDate)

        val database = Firebase.database
        val myRef = database.getReference("E-Ms1a/$username")
        val builder = AlertDialog.Builder(this)

        myRef.get()
            .addOnSuccessListener {
                if (it.value == null) {
                    val user = MainActivity.User(
                        firstName = firstName,
                        lastName = lastName,
                        username = username,
                        password = password,
                        nationality = selectedNationality,
                        registrationDate = registrationDate,
                        registrationTime = registrationTime
                    )
                    myRef.setValue(user)

                    builder.setTitle("Registration successful!")
                    builder.setPositiveButton("Yay!") { _, _ ->
                        finish()
                    }
                    val alertDialog: AlertDialog = builder.create()
                    alertDialog.setCancelable(false)
                    alertDialog.show()
                } else {
                    builder.setTitle("This username has been taken. Please enter a new username.")
                    builder.setPositiveButton("Ok") { _, _ -> }
                    val alertDialog: AlertDialog = builder.create()
                    alertDialog.setCancelable(false)
                    alertDialog.show()
                }
            }
            .addOnFailureListener {
                builder.setTitle("Failed to connect to database. Please try again later.")
                builder.setPositiveButton("Ok") { _, _ -> }
                val alertDialog: AlertDialog = builder.create()
                alertDialog.setCancelable(false)
                alertDialog.show()
            }
    }
}