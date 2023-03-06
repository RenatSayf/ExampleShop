package com.renatsayf.resourses.extensions

import android.view.View
import androidx.appcompat.widget.PopupMenu

fun View.setPopUpMenu(menuResource: Int): PopupMenu {
    return PopupMenu(this.context, this).apply {
        inflate(menuResource)
    }
}