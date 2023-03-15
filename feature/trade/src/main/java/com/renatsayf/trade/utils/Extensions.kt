package com.renatsayf.trade.utils

import androidx.fragment.app.Fragment

fun <T>Fragment.fromJson(
    json: String?,
    clazz: Class<T>,
    onSuccess: (T) -> Unit = {},
    onFailure: (String) -> Unit = {}
) {
    com.renatsayf.resourses.extensions.fromJson(json, clazz, onSuccess, onFailure)
}