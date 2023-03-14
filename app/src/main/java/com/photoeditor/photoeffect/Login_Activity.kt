package com.photoeditor.photoeffect

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.photoeditor.photoeffect.databinding.ActivityLoginBinding
import com.photoeditor.photoeffect.databinding.ActivitySignUpBinding

class Login_Activity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var fireBaseAuth: FirebaseAuth

    // Regular expression to check if the password meets the required restrictions
    private val PASSWORD_PATTERN: Regex = Regex("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[$@$!%*?&])[A-Za-z\\d$@$!%*?&]{8,}")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        fireBaseAuth = FirebaseAuth.getInstance()

        // Click listener for the "Sign Up" text view to open the sign up activity
        binding.text1.setOnClickListener {
            val intent = Intent(this, SignUp_activity::class.java)
            startActivity(intent)
        }

        // Click listener for the login button to sign in the user with their email and password
        binding.button.setOnClickListener {
            val email = binding.editTextTextEmailAddress2.text.toString()
            val pass = binding.editTextNumberPassword.text.toString()
            if (email.isNotEmpty() && pass.isNotEmpty()) {
                // Check if the password meets the required restrictions
                if (PASSWORD_PATTERN.matches(pass)) {
                    fireBaseAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener {
                        if (it.isSuccessful) {
                            val intent = Intent(this, MainActivity::class.java)
                            startActivity(intent)
                        }
                    }
                } else {
                    // Display an error message if the password does not meet the required restrictions
                    Toast.makeText(this, "Password must have at least 8 characters, contain at least one uppercase letter, one lowercase letter, one number, and one special character.", Toast.LENGTH_LONG).show()
                }
            } else {
                // Display an error message if the user does not provide an email or password
                Toast.makeText(this, "Please fill both the required fields.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        if (fireBaseAuth.currentUser != null) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}
