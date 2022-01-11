/*
Copyright 2021 Matheus Menezes

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */

package br.com.mathsemilio.asmrcontestanttable.common

import android.text.*
import android.widget.*
import android.content.Context

fun Context.showShortToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Context.showLongToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

fun Button.setDisabledState(disabledText: String) {
    this.apply {
        isEnabled = false
        text = disabledText
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

inline fun EditText.onAfterTextChangedListener(
    crossinline onAfterTextChanged: (Editable?) -> Unit
) {
    this.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        override fun afterTextChanged(editable: Editable?) = onAfterTextChanged(editable)
    })
}
