package com.renatsayf.local.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.renatsayf.local.models.User

private const val APP_PREF = "APP_PREF"
private const val USER = "USER"

fun Context.appPref(): SharedPreferences {
    return getSharedPreferences(APP_PREF, Context.MODE_PRIVATE)
}

fun Fragment.appPref(): SharedPreferences {
    return requireContext().appPref()
}

fun Fragment.saveUserToPref(user: User, onSaved: () -> Unit = {}) {
    val json = Gson().toJson(user)
    appPref().edit().putString(USER, json).apply()
    onSaved.invoke()
}

fun Fragment.getUserFromPref(
    onSuccess: (User) -> Unit = {},
    onFailure: (String) -> Unit = {}
) {
    val string = appPref().getString(USER, null)
    try {
        val user = Gson().fromJson(string, User::class.java)
        onSuccess.invoke(user)
    } catch (e: JsonSyntaxException) {
        onFailure.invoke(e.message?: "Unknown error")
    }

}