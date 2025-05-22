package com.example.kotlin_app_pedidosya.viewModels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class LoginState(
    val email: String = "",
    val password: String = ""
)

class LoginViewModel: ViewModel() {
    private val _login = MutableStateFlow(LoginState())

    val login: StateFlow<LoginState> get() = _login

    fun handleChange(key: String, value: String) {
        val currentState = _login.value

        val updated = when (key) {
            "email" -> currentState.copy(email = value)
            "password" -> currentState.copy(password = value)
            else -> currentState
        }
        _login.value = updated
    }

    fun validateEmail(): Boolean {
        val email = _login.value.email
        val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$".toRegex()

        return emailRegex.matches(email)
    }

    fun validatePassword(): Boolean {
        val password = _login.value.password
        val passwordRegex = "^(?!.*\\s)(.*\\d.*){6,}$".toRegex()

        return passwordRegex.matches(password)
    }
}