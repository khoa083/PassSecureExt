package com.kblack.passsecureext.activities

import android.os.Bundle
import android.os.PersistableBundle
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.color.DynamicColors
import com.kblack.passsecureext.R
import com.kblack.passsecureext.appmanager.ApplicationManager
import com.kblack.passsecureext.databinding.ActivityMainBinding
import com.kblack.passsecureext.objects.AppState
import com.kblack.passsecureext.preferences.PreferenceManager
import com.kblack.passsecureext.preferences.PreferenceManager.Companion.MATERIAL_YOU
import com.kblack.passsecureext.utils.UiUtils.Companion.setNavBarContrastEnforced
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {
    lateinit var activityBinding: ActivityMainBinding
    private val prefManager by inject<PreferenceManager>()
    private lateinit var navHostFragment: NavHostFragment
    private lateinit var navController: NavController
    private lateinit var viewsToAnimate: Array<ViewGroup>
    private var selectedItem = 0

    private companion object {
        private val SHOW_ANIM_INTERPOLATOR = DecelerateInterpolator()
        private val HIDE_ANIM_INTERPOLATOR = AccelerateInterpolator()
    }

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        installSplashScreen()
        if (prefManager.getBoolean (MATERIAL_YOU, defValue = false)) {
            DynamicColors.applyToActivityIfAvailable(this)
            DynamicColors.applyToActivitiesIfAvailable(applicationContext as ApplicationManager) // For other activities
        }
        enableEdgeToEdge()
        window.setNavBarContrastEnforced()

        super.onCreate(savedInstanceState, persistentState)

        navHostFragment = supportFragmentManager.findFragmentById(R.id.main_nav_host) as NavHostFragment
        navController = navHostFragment.navController
    }

    override fun onDestroy() {
        super.onDestroy()
        AppState.isAppOpen = false
    }

}