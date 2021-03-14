package br.com.mathsemilio.asmrcontestanttable.common

import android.content.Context
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

fun Context.showShortToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Context.showLongToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

fun Button.setDisabledState(disableText: String) {
    this.apply {
        isEnabled = false
        text = disableText
    }
}

fun Button.setEnabledState(enabledText: String) {
    this.apply {
        isEnabled = true
        text = enabledText
    }
}

fun EditText.showErrorState(message: String) {
    this.apply {
        error = message
        requestFocus()
    }
}