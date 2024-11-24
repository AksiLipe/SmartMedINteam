package com.example.smartmed.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.smartmed.databinding.ActivityLoginBinding
import com.google.android.material.snackbar.Snackbar

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (isUserLoggedIn()) {
            startMainActivity()
            finish()
            return
        }

        setupLoginButton()
    }

    private fun setupLoginButton() {
        binding.loginButton.setOnClickListener {
            val login = binding.loginEt.text.toString()
            val password = binding.passwordEt.text.toString()

            if (validateCredentials(login, password)) {
                saveLoginState()
                startMainActivity()
                finish()
            } else {
                showError()
            }
        }
    }

    private fun validateCredentials(login: String, password: String): Boolean {
        return login.isNotEmpty() && password.isNotEmpty()
    }

    private fun saveLoginState() {
        getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)
            .edit()
            .putBoolean("is_logged_in", true)
            .apply()
    }

    private fun isUserLoggedIn(): Boolean {
        return getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)
            .getBoolean("is_logged_in", false)
    }

    private fun showError() {
        Snackbar.make(
            binding.root,
            "Неверный логин или пароль",
            Snackbar.LENGTH_SHORT
        ).show()
    }

    private fun startMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}