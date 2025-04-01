package com.kblack.passsecureext.activities

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.kblack.passsecureext.R
import com.kblack.passsecureext.databinding.ActivityMultiPwdBinding
import com.kblack.passsecureext.objects.MultiPwdList
import com.kblack.passsecureext.preferences.PreferenceManager
import com.kblack.passsecureext.preferences.PreferenceManager.Companion.BLOCK_SS
import com.kblack.passsecureext.preferences.PreferenceManager.Companion.GRID_VIEW
import com.kblack.passsecureext.preferences.PreferenceManager.Companion.SORT_ASC
import com.kblack.passsecureext.utils.UiUtils.Companion.blockScreenshots
import com.kblack.passsecureext.utils.UiUtils.Companion.setNavBarContrastEnforced
import org.koin.android.ext.android.inject

class MultiPwdActivity : AppCompatActivity(), MenuProvider {

    private lateinit var navController: NavController
    private val prefManager by inject<PreferenceManager>()
    private var isGridView = false
    private var isAscSort = false

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        window.setNavBarContrastEnforced()
        super.onCreate(savedInstanceState)
        addMenuProvider(this)
        onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
        val activityBinding = ActivityMultiPwdBinding.inflate(layoutInflater)
        setContentView(activityBinding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.multi_pwd_nav_host) as NavHostFragment
        navController = navHostFragment.navController
        isGridView = prefManager.getBoolean(GRID_VIEW, defValue = false)
        isAscSort = prefManager.getBoolean(SORT_ASC)

        // Disable screenshots and screen recordings
        window.blockScreenshots(prefManager.getBoolean(BLOCK_SS))

        activityBinding.multiPwdBottomAppBar.apply {
            setSupportActionBar(this)
            setNavigationOnClickListener { onBackPressedDispatcher.onBackPressed() }
        }
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.menu_multi_pwd, menu)
        menu.findItem(R.id.menu_view).apply {
            if (!isGridView) setIcon(R.drawable.ic_view_grid)
            else setIcon(R.drawable.ic_view_list)
        }
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {

        when (menuItem.itemId) {

            R.id.menu_view -> {
                isGridView = !isGridView
                if (!isGridView) menuItem.setIcon(R.drawable.ic_view_grid)
                else menuItem.setIcon(R.drawable.ic_view_list)
                navController.navigate(R.id.action_multiPwdFragment_self)
            }

            R.id.menu_sort -> {
                isAscSort = !isAscSort
                navController.navigate(R.id.action_multiPwdFragment_self)
            }

        }
        return true

    }

    private val onBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            finish()
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        prefManager.apply {
            setBoolean(GRID_VIEW, isGridView)
            setBoolean(SORT_ASC, isAscSort)
        }
        MultiPwdList.pwdList.clear()
    }

}