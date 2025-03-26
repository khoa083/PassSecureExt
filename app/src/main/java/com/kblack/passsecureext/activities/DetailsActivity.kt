package com.kblack.passsecureext.activities

import android.os.Bundle
import android.os.PersistableBundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.kblack.passsecureext.R
import com.kblack.passsecureext.databinding.ActivityDetailsBinding
import com.kblack.passsecureext.fragments.details.DetailFragment
import com.kblack.passsecureext.preferences.PreferenceManager
import com.kblack.passsecureext.preferences.PreferenceManager.Companion.BLOCK_SS
import com.kblack.passsecureext.utils.UiUtils.Companion.blockScreenshots
import com.kblack.passsecureext.utils.UiUtils.Companion.setNavBarContrastEnforced
import org.koin.android.ext.android.get

class DetailsActivity : AppCompatActivity() {
    private lateinit var passwordLine : CharSequence

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        enableEdgeToEdge()
        window.setNavBarContrastEnforced()
        super.onCreate(savedInstanceState, persistentState)
        val activityBinding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(activityBinding.root)

        passwordLine = intent.getStringExtra("PwdLine")!!

        window.blockScreenshots(get<PreferenceManager>().getBoolean(BLOCK_SS))

        activityBinding.detailsBottomAppBar.apply {
            setSupportActionBar(this)
            setNavigationOnClickListener { onBackPressedDispatcher.onBackPressed() }
        }
        supportFragmentManager.beginTransaction()
            .replace(R.id.activity_host_fragment, DetailFragment())
            .commitNow()
    }
}