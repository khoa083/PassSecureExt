package com.kblack.passsecureext.appmanager

import android.app.Application
import com.kblack.passsecureext.koin_di.appModule
import com.kblack.passsecureext.preferences.PreferenceManager
import com.kblack.passsecureext.preferences.PreferenceManager.Companion.THEME_PREF
import com.kblack.passsecureext.utils.UiUtils.Companion.setAppTheme
import org.koin.android.ext.android.get
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class ApplicationManager : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@ApplicationManager)
            modules(appModule)
        }
        setAppTheme(get<PreferenceManager>().getInt(THEME_PREF))
    }

}