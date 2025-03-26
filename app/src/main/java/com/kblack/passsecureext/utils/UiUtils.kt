package com.kblack.passsecureext.utils

import android.annotation.SuppressLint
import android.os.Build
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatDelegate
import com.kblack.passsecureext.R

class UiUtils {
    companion object {

        @SuppressLint("ObsoleteSdkInt")
        fun setAppTheme(selectedTheme: Int) {
            when(selectedTheme) {
                0 -> {
                    AppCompatDelegate.setDefaultNightMode(
                        if(Build.VERSION.SDK_INT >= 29) AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
                        else AppCompatDelegate.MODE_NIGHT_NO
                    )
//                    if (Build.VERSION.SDK_INT >= 29){
//                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
//                    }
//                    else {
//                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
//                    }
                }
                R.id.followSystem -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                R.id.light -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                R.id.dark -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }
        }

        @SuppressLint("ObsoleteSdkInt")
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