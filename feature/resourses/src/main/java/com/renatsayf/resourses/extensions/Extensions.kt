package com.renatsayf.resourses.extensions

import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.view.View
import android.widget.ImageView
import androidx.appcompat.widget.PopupMenu
import androidx.core.net.toUri
import androidx.core.view.drawToBitmap
import java.io.File
import java.io.FileInputStream
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

fun ImageView.saveImageToInternalStorage(
    fileName: String,
    onSuccess: (String) -> Unit = {},
    onFailure: () -> Unit = {}
) {
    val fullFileName = "$fileName.jpeg"
    try {
        val outputStream = this.context.openFileOutput(fullFileName, Context.MODE_PRIVATE)
        val bitmap = this.drawToBitmap()
        val isCompressed = bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
        when {
            isCompressed -> {
                onSuccess.invoke(fullFileName)
            }
            else -> onFailure.invoke()
        }
    } catch (e: Exception) {
        e.printStackTrace()
        onFailure.invoke()
    }
}

fun ImageView.getImageFromInternalStorage(
    fileName: String,
    onSuccess: (Bitmap) -> Unit = {},
    onFailure: (String) -> Unit = {}
) {
    val directory = this.context.filesDir
    val file = File(directory, fileName)
    try {
        val bitmap = BitmapFactory.decodeStream(FileInputStream(file))
        onSuccess.invoke(bitmap)
    }
    catch (e: Exception) {
        e.printStackTrace()
        onFailure.invoke(e.message?: "Unknown error")
    }
}