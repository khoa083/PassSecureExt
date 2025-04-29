package com.kblack.passsecureext.utils

import android.content.ClipData
import android.content.ClipDescription
import android.os.Build
import android.os.PersistableBundle
import androidx.annotation.RequiresApi

class ClipboardUtils {

    companion object {

        // Hide from revealing on copy
        // https://developer.android.com/develop/ui/views/touch-and-input/copy-paste#SensitiveContent
        @RequiresApi(Build.VERSION_CODES.N)
        fun ClipData.hideSensitiveContent() {
            description.extras = PersistableBundle().apply {
                if (Build.VERSION.SDK_INT >= 33) putBoolean(ClipDescription.EXTRA_IS_SENSITIVE, true)
                else putBoolean("android.content.extra.IS_SENSITIVE", true)
            }
        }

    }

}