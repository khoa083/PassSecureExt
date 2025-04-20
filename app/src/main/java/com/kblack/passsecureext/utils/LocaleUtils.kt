package com.kblack.passsecureext.utils

import android.content.Context
import java.io.InputStreamReader
import java.util.Locale
import java.util.Properties
import java.util.ResourceBundle
import com.kblack.passsecureext.R
import java.util.Collections
import java.util.Enumeration

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
                        "fa" -> loadTranslations(context, R.raw.messages_fa)
                        "pt-rBR" -> loadTranslations(context, R.raw.messages_pt_rbr)
                        "sv" -> loadTranslations(context, R.raw.messages_sv)
                        "tr" -> loadTranslations(context, R.raw.messages_tr)
                        else -> loadTranslations(context, R.raw.messages_zh)
                    }

                object : ResourceBundle() {
                    override fun handleGetObject(key: String?): Any? = properties.getProperty(key)
                    override fun getKeys(): Enumeration<String?>? = Collections.enumeration(properties.keys.map { it.toString() })
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