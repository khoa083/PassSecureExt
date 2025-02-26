package com.kblack.passsecureext.activities

import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.kblack.passsecureext.databinding.ActivityMainBinding
import com.kblack.passsecureext.preferences.PreferenceManager
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {
    lateinit var activityBinding: ActivityMainBinding
    private val prefManager by inject<PreferenceManager>()
    private lateinit var navHostFragment: NavHostFragment
    private lateinit var navController: NavController
}