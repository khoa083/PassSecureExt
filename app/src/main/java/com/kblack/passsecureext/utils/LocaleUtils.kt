package com.kblack.passsecureext.utils

import android.content.Context
import java.util.Locale
import java.util.ResourceBundle

class LocaleUtils {

    companion object {

        fun localizedFeedbackResourceBundle(context: Context): ResourceBundle {

            val locale = Locale.getDefault().language

            // If locale is in zxcvbn4j, use default resource bundle
            // else use custom messages.properties from res/raw
        }

    }

}