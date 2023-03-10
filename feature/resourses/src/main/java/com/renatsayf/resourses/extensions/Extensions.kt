package com.renatsayf.resourses.extensions

import android.content.res.Resources
import android.net.Uri
import android.view.View
import androidx.appcompat.widget.PopupMenu
import androidx.core.net.toUri
import kotlin.math.roundToInt

fun View.setPopUpMenu(menuResource: Int): PopupMenu {
    return PopupMenu(this.context, this).apply {
        inflate(menuResource)
    }
}

fun String.toDeepLink(): Uri {
    return "ExampleShop://$this".toUri()
}

val Int.dp: Int
    get() = (this * Resources.getSystem().displayMetrics.density).roundToInt()

val Float.dp: Int
    get() = (this * Resources.getSystem().displayMetrics.density).roundToInt()