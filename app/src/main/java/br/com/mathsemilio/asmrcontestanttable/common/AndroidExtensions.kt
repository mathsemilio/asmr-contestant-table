package br.com.mathsemilio.asmrcontestanttable.common

import android.content.Context
import android.content.SharedPreferences
import android.widget.Toast

fun Context.showShortToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Context.showLongToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

inline fun <reified T> SharedPreferences.getValue(key: String, defaultValue: T): T {
    return when (T::class) {
        Int::class -> this.getInt(key, defaultValue as Int) as T
        Long::class -> this.getLong(key, defaultValue as Long) as T
        else -> throw IllegalArgumentException()
    }
}

inline fun <reified T> SharedPreferences.Editor.putValue(key: String, value: T) {
    when (T::class) {
        Int::class -> this.putInt(key, value as Int)
        Long::class -> this.putLong(key, value as Long)
        else -> throw IllegalArgumentException()
    }
}