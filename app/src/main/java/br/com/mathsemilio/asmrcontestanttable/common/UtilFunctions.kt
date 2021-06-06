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