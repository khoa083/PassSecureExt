package com.kblack.passsecureext.koin_di

import android.app.Application
import com.kblack.passsecureext.R
import com.kblack.passsecureext.inputstream.ResourceFromInputStream
import com.kblack.passsecureext.preferences.PreferenceManager
import org.koin.core.qualifier.named
import org.koin.dsl.module
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream

val appModule =
    module {
        single { PreferenceManager(get()) }

        single(named("commonPasswordsResource")) {
            ResourceFromInputStream(
                ByteArrayInputStream(
                    ByteArrayOutputStream().apply {
                        get<Application>().resources.openRawResource(R.raw.top_200_2024).copyTo(this)
                        get<Application>().resources.openRawResource(R.raw.other_common_passwords).copyTo(this)
                    }.toByteArray())
                ) as com.nulabinc.zxcvbn.io.Resource
        }
}