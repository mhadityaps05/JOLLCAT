package com.example.finalprojectmcs

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.finalprojectmcs.databinding.ActivityRegisterBinding
import com.example.finalprojectmcs.datalink.User
import java.util.*

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var databaseHelper: DataBaseHelper
    private lateinit var sharedPreferences: SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        databaseHelper = DataBaseHelper(this)
        sharedPreferences = getSharedPreferences("user_session", MODE_PRIVATE)

        usernameValidate()
        passwordValidate()
        phoneValidate()

        binding.RegisterBtn.setOnClickListener { BtnRegister() }
    }

    private fun BtnRegister() {
        binding.UsernameContainer.helperText = validName()
        binding.PasswordContainer.helperText = validPassword()
        binding.PhoneContainer.helperText = validPhone()

        val validName = binding.UsernameContainer.helperText == null
        val validPassword = binding.PasswordContainer.helperText == null
        val validPhone = binding.PhoneContainer.helperText == null

        if (validName && validPassword && validPhone) {
            val username = binding.UsernameText.text.toString()
            val password = binding.PasswordTextSigin.text.toString()
            val phoneNumber = binding.PhoneText.text.toString()

            val existingUser = databaseHelper.getUserByUsername(username)
            if (existingUser != null) {
                Toast.makeText(this, "Already", Toast.LENGTH_SHORT).show()
            } else {
                val userId = UUID.randomUUID().toString()
                val newUser = User(userId, username, password, phoneNumber)
                val result = databaseHelper.addUser(newUser)

                if (result != -1L) {
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                } else {
                    invalidForm()
                }
            }
        }
    }

    private fun invalidForm() {
        var message = ""
        if (binding.UsernameContainer.helperText != null)
            message += "\nUsername: " + binding.UsernameContainer.helperText
        if (binding.PasswordContainer.helperText != null)
            message += "\nPassword: " + binding.PasswordContainer.helperText
        if (binding.PhoneContainer.helperText != null)
            message += "\nPhone: " + binding.PhoneContainer.helperText

        Toast.makeText(this, "Invalid:$message", Toast.LENGTH_LONG).show()
    }

    private fun usernameValidate() {
        binding.UsernameText.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                binding.UsernameContainer.helperText = validName()
            }
        }
    }

    private fun validName(): String? {
        val s = binding.UsernameText.text.toString()
        if (s.length < 8) {
            return "Username must be at least 8 characters"
        }
        if (!s.matches("[a-zA-Z0-9]+".toRegex())) {
            return "Username must consist only of alphanumeric characters."
        }
        return null
    }

    private fun passwordValidate() {
        binding.PasswordTextSigin.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                binding.PasswordContainer.helperText = validPassword()
            }
        }
    }

    private fun validPassword(): String? {
        val p = binding.PasswordTextSigin.text.toString()
        if (p.length < 5) {
            return "Password must be at least five characters"
        }
        if (!p.matches(".*[a-z].*".toRegex())) {
            return "Password must consist of at least 1 letter"
        }
        if (!p.matches(".*[0-9].*".toRegex())) {
            return "Password must consist of at least 1 number"
        }
        if (!p.matches(".*[@#!%^&+-].*".toRegex())) {
            return "Password must consist of at least 1 symbol"
        }
        return null
    }

    private fun phoneValidate() {
        binding.PhoneText.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                binding.PhoneContainer.helperText = validPhone()
            }
        }
    }

    private fun validPhone(): String? {
        val phone = binding.PhoneText.text.toString()
        if (phone.length < 8 || phone.length > 20) {
            return "Phone must be 8-20 digits"
        }
        if (!phone.startsWith("0") && !phone.startsWith("+")) {
            return "Phone number must start with '0' or '+'"
        }
        if (!phone.substring(1).matches("[0-9]+".toRegex())) {
            return "Phone must consist only of digits after the first character"
        }
        return null
    }
}
