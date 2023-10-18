package com.bima.mystoryapp

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.Patterns
import androidx.appcompat.widget.AppCompatEditText

class CustomEditTextEmail : AppCompatEditText {
    private val errorMessage: String by lazy { context.getString(R.string.invalidEmail) }
    private val emailPattern: Regex by lazy { "^[_A-Za-z0-9-+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$".toRegex() }

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init()
    }

    private fun init() {
        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                if (s != null && !isEmailValid(s.toString())) {
                    error = errorMessage
                } else {
                    error = null
                }
            }
        })
    }

    fun isValid(): Boolean {
        val email = text.toString()
        return isEmailValid(email)
    }

    private fun isEmailValid(email: String): Boolean {
//        return emailPattern.matches(email)
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}