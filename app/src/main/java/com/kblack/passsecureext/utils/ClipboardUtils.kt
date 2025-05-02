package com.kblack.passsecureext.utils

import android.content.ClipData
import android.content.ClipDescription
import android.content.ClipboardManager
import android.os.Build
import android.os.PersistableBundle
import androidx.annotation.RequiresApi
import androidx.lifecycle.LifecycleCoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ClipboardUtils {

    companion object {

        fun manageClipboard(clipboardManager: ClipboardManager, lifecycleScope: LifecycleCoroutineScope) {
            var job: Job? = null

            // Clear clipboard after 1 minute if password is copied
            clipboardManager.addPrimaryClipChangedListener {
                if (clipboardManager.hasPrimaryClip()
                    && clipboardManager.primaryClipDescription?.label == "IYPS") {
                    job?.cancel()
                    job = lifecycleScope.launch {
                        delay(60000)
                        clipboardManager.clearClipboard()
                    }
                }
            }
        }

        // Hide from revealing on copy
        // https://developer.android.com/develop/ui/views/touch-and-input/copy-paste#SensitiveContent
        @RequiresApi(Build.VERSION_CODES.N)
        fun ClipData.hideSensitiveContent() {
            description.extras = PersistableBundle().apply {
                if (Build.VERSION.SDK_INT >= 33) putBoolean(ClipDescription.EXTRA_IS_SENSITIVE, true)
                else putBoolean("android.content.extra.IS_SENSITIVE", true)
            }
        }

        // Clear password from clipboard
        fun ClipboardManager.clearClipboard() {
            when {
                Build.VERSION.SDK_INT >= 28 -> clearPrimaryClip()
                else -> setPrimaryClip(ClipData.newPlainText(null, null))
            }

        }

    }
}