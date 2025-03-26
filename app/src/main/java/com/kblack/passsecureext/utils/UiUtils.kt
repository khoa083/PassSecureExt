package com.kblack.passsecureext.utils

import android.os.Build
import android.view.Window
import android.view.WindowManager

class UiUtils {
    companion object {
        fun Window.setNavBarContrastEnforced() {
            if (Build.VERSION.SDK_INT >= 29) {
                isNavigationBarContrastEnforced = false
            }
        }

        fun Window.blockScreenshots(shouldBlock: Boolean) {
            if (shouldBlock) {
                setFlags(
                    WindowManager.LayoutParams.FLAG_SECURE,
                    WindowManager.LayoutParams.FLAG_SECURE)
            }
            else {
                clearFlags(WindowManager.LayoutParams.FLAG_SECURE)
            }
        }
    }
}