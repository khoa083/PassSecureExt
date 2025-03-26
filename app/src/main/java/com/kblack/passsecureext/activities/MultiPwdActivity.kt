package com.kblack.passsecureext.activities

import android.os.Bundle
import android.os.PersistableBundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuProvider
import androidx.navigation.NavController
import com.kblack.passsecureext.objects.MultiPwdList
import com.kblack.passsecureext.preferences.PreferenceManager
import com.kblack.passsecureext.preferences.PreferenceManager.Companion.GRID_VIEW
import com.kblack.passsecureext.preferences.PreferenceManager.Companion.SORT_ASC
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
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        TODO("Not yet implemented")
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        TODO("Not yet implemented")
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