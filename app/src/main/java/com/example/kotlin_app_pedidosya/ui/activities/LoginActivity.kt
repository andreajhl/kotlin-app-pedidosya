package com.example.kotlin_app_pedidosya.ui.activities

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.kotlin_app_pedidosya.R
import com.example.kotlin_app_pedidosya.data.SessionManager
import com.example.kotlin_app_pedidosya.ui.fragments.RegisterDialogFragment
import com.example.kotlin_app_pedidosya.ui.utils.TextChangedListener
import com.example.kotlin_app_pedidosya.viewModel.LoginViewModel
import kotlinx.coroutines.launch

fun EditText.bindToViewModel(viewModel: LoginViewModel) {
    this.addTextChangedListener(object : TextChangedListener<EditText>(this) {
        override fun onTextChanged(target: EditText, s: Editable?) {
            val key = target.tag as? String ?: return
            viewModel.updateLoginField(key, s.toString())
        }
    })
}

class LoginActivity : AppCompatActivity() {
    private val loginViewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)

        val session = SessionManager(this)

        val inputEmail = findViewById<EditText>(R.id.email)
        val inputPassword = findViewById<EditText>(R.id.password)
        val btnLogin = findViewById<Button>(R.id.btnLogin)
        val btnRegister = findViewById<Button>(R.id.btnRegister)

        val inputEmailError = findViewById<TextView>(R.id.emailError)
        val inputPasswordError = findViewById<TextView>(R.id.passwordError)

        inputEmail.bindToViewModel(loginViewModel)
        inputPassword.bindToViewModel(loginViewModel)

        inputEmail.setOnFocusChangeListener { _, hasFocus ->
            inputEmailError.text = if (!hasFocus) loginViewModel.errorMsg.value.email else ""
        }

        inputPassword.setOnFocusChangeListener { _, hasFocus ->
            inputPasswordError.text = if (!hasFocus) loginViewModel.errorMsg.value.password else ""
        }

        fun handleRedirect() {
            val intent = Intent(this, ProductListActivity::class.java)
            startActivity(intent)
            finish()
        }

        btnLogin.setOnClickListener {
            session.setLogged(true)
            handleRedirect()
        }

        btnRegister.setOnClickListener {
            val registerDialog = RegisterDialogFragment()
            registerDialog.show(supportFragmentManager, "RegisterDialog")
        }

        lifecycleScope.launch {
            if(session.isLogged()) handleRedirect()

            loginViewModel.login.collect {
                val isValidData = loginViewModel.isValidateData()
                btnLogin.isEnabled = isValidData

            }
        }
    }
}