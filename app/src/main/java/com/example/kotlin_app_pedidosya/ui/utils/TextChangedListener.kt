package com.example.kotlin_app_pedidosya.ui.utils

import android.text.Editable
import android.text.TextWatcher

abstract class TextChangedListener<T>(private val target: T) : TextWatcher {

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

    override fun afterTextChanged(s: Editable?) {
        onTextChanged(target, s)
    }

    abstract fun onTextChanged(target: T, s: Editable?)
}