package com.kblack.passsecureext.utils

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.view.View
import android.widget.Toast
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.kblack.passsecureext.R
import com.kblack.passsecureext.utils.UiUtils.Companion.showSnackBar

class IntentUtils {
    companion object {
        fun openURL(
            activity: Activity,
            url: String,
            coordinatorLayout: CoordinatorLayout,
            anchorView: View?
        ) {
            try {
                activity.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
            } catch (_:ActivityNotFoundException) {
                showSnackBar(
                    coordinatorLayout,
                    activity.getString(R.string.no_browsers),
                    anchorView
                )
            }
        }
        fun openURL(
            activity: Activity,
            url: String
        ) {
            try {
                activity.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
            } catch (_:ActivityNotFoundException) {
                Toast.makeText(activity, activity.getString(R.string.no_browsers), Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }
}