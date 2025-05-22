package com.example.kotlin_app_pedidosya.ui

import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.kotlin_app_pedidosya.R
import com.example.kotlin_app_pedidosya.domain.TextChangedListener
import com.example.kotlin_app_pedidosya.viewModels.LoginViewModel
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {
    private val loginViewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)

        val inputEmail = findViewById<EditText>(R.id.email)
        val inputPassword = findViewById<EditText>(R.id.password)
        val btnLogin = findViewById<Button>(R.id.btnLogin)
        val btnRegister = findViewById<Button>(R.id.btnRegister)

        val inputEmailError = findViewById<TextView>(R.id.emailError)
        val inputPasswordError = findViewById<TextView>(R.id.passwordError)

        inputEmail.addTextChangedListener(object : TextChangedListener<EditText>(inputEmail) {
            override fun onTextChanged(target: EditText, s: Editable?) {
                loginViewModel.handleChange(key= "email", value = s.toString())
            }
        })

        inputEmail.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus && !loginViewModel.validateEmail()) {
                inputEmailError.text = "Email Invalid"
            } else {
                inputEmailError.text =  ""
            }
        }

        inputPassword.addTextChangedListener(object : TextChangedListener<EditText>(inputEmail) {
            override fun onTextChanged(target: EditText, s: Editable?) {
                loginViewModel.handleChange(key= "password", value = s.toString())
            }
        })

        inputPassword.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus && !loginViewModel.validatePassword()) {
                inputPasswordError.text = "Password Invalid"
            } else {
                inputPasswordError.text = ""
            }
        }

        btnRegister.setOnClickListener {
            Log.d("click", "clickeo")
        }

        lifecycleScope.launch {
            loginViewModel.login.collect {
                val isValidEmail = loginViewModel.validateEmail()
                val isValidPassword = loginViewModel.validatePassword()
                if (isValidEmail && isValidPassword) {
                    btnLogin.isEnabled = true
                }

            }
        }
    }
}