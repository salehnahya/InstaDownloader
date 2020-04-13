package com.galaxy.instadownloader.util

import android.content.ClipData
import android.content.ClipDescription
import android.content.ClipboardManager
import android.content.Context
import androidx.fragment.app.Fragment


fun Fragment.readFromClipboard(): String? {
    val clipboard: ClipboardManager? =
        requireActivity().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    if (clipboard?.hasPrimaryClip()!!) {
        val description: ClipDescription? = clipboard.primaryClipDescription
        val data: ClipData? = clipboard.primaryClip
        if (data != null && description != null && description.hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)) return data.getItemAt(
            0
        ).text.toString()
    }
    return ""


}