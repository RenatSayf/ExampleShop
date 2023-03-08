package com.renatsayf.resourses.extensions

import android.net.Uri
import android.view.View
import androidx.appcompat.widget.PopupMenu
import androidx.core.net.toUri

fun View.setPopUpMenu(menuResource: Int): PopupMenu {
    return PopupMenu(this.context, this).apply {
        inflate(menuResource)
    }
}

fun String.toDeepLink(): Uri {
    return "ExampleShop://$this".toUri()
}