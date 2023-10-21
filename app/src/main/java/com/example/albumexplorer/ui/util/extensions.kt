package com.example.albumexplorer.ui.util

import android.content.Context
import android.content.Intent

fun Context.shareImage(url: String?) {
    val sendIntent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, "image: $url")
        type = "text/plain"
    }
    val shareIntent = Intent.createChooser(sendIntent, "Share image")
    startActivity(shareIntent)
}