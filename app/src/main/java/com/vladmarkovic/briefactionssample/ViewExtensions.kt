package com.vladmarkovic.briefactionssample

import android.view.KeyEvent
import android.widget.Button
import android.widget.EditText


fun EditText.setOnEnterListener(action: () -> Unit) {
    setOnKeyListener { v, keyCode, event ->
        // If the event is a key-down event on the "enter" button
        if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
            action()
            true
        } else {
            false
        }
    }
}

fun setOnClickListener(button: Button, editText: EditText, action: () -> Unit) {
    button.setOnClickListener { action() }
    editText.setOnEnterListener(action)
}