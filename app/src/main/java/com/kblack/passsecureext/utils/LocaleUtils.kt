package com.kblack.passsecureext.utils

import android.content.Context
import java.io.InputStreamReader
import java.util.Locale
import java.util.Properties
import java.util.ResourceBundle

class LocaleUtils {

    companion object {

        fun localizedFeedbackResourceBundle(context: Context): ResourceBundle {

            val locale = Locale.getDefault().language

            // If locale is in zxcvbn4j, use default resource bundle
            // else use custom messages.properties from res/raw
            return if (locale !in setOf("fa", "pt-rBR", "sv", "tr", "zh")) {
                ResourceBundle.getBundle("com/nulabinc/zxcvbn/messages")
            }
            else {
                val properties =
                    when(locale) {
                        "fa" -> load
                    }
            }
        }

        private fun loadTranslations(context: Context, resId: Int): Properties {
            return Properties().apply {
                load(InputStreamReader(context.resources.openRawResource(resId), Charsets.UTF_8))
            }
        }

    }

}