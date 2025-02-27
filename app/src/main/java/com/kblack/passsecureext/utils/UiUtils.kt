package com.kblack.passsecureext.utils

import android.os.Build
import android.view.Window

class UiUtils {
    companion object {
        fun Window.setNavBarContrastEnforced() {
            if (Build.VERSION.SDK_INT >= 29) {
                isNavigationBarContrastEnforced = false
            }
        }
    }
}