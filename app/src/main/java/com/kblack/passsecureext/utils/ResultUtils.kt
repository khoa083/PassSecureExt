package com.kblack.passsecureext.utils

import android.content.Context
import com.google.android.material.progressindicator.LinearProgressIndicator
import com.google.android.material.textview.MaterialTextView
import com.kblack.passsecureext.R
import com.nulabinc.zxcvbn.Feedback
import java.util.concurrent.TimeUnit

class ResultUtils(val context: Context) {

    private val dayStringResource = context.getString(R.string.day)
    private val monthStringResource = context.getString(R.string.month)
    private val yearStringResource = context.getString(R.string.year)

    private val timeUnitsReplacementMap =
        mapOf("second" to Pair(context.getString(R.string.second), context.getString(R.string.seconds)),
            "minute" to Pair(context.getString(R.string.minute), context.getString(R.string.minutes)),
            "hour" to Pair(context.getString(R.string.hour), context.getString(R.string.hours)),
            "day" to Pair(dayStringResource, context.getString(R.string.days)),
            "month" to Pair(monthStringResource, context.getString(R.string.months)),
            "year" to Pair(yearStringResource, context.getString(R.string.years)))

    private val timeStringsReplacementMap =
        mapOf("less than a second" to context.getString(R.string.less_than_sec),
            "centuries" to context.getString(R.string.centuries))

    private val timeStringsRegex = "(\\d+)\\s+(\\w+)".toRegex() // Insert regex meme here

    // Take days in:
    // Month = 31, Year = 365
    private val threeHrsInMillis = TimeUnit.HOURS.toMillis(3)
    private val oneMonthInMillis = TimeUnit.DAYS.toMillis(31)
    private val sixMonthsInMillis = TimeUnit.DAYS.toMillis(186)
    private val fiveYrsInMillis = TimeUnit.DAYS.toMillis(1825)

    private val emptyMeterColor = context.resources.getColor(android.R.color.transparent, context.theme)
    private val worstMeterColor = context.resources.getColor(R.color.worstMeterColor, context.theme)
    private val weakMeterColor = context.resources.getColor(R.color.weakMeterColor, context.theme)
    private val mediumMeterColor = context.resources.getColor(R.color.mediumMeterColor, context.theme)
    private val strongMeterColor = context.resources.getColor(R.color.strongMeterColor, context.theme)
    private val excellentMeterColor = context.resources.getColor(R.color.excellentMeterColor, context.theme)

    private val worstString = context.getString(R.string.worst)
    private val weakString = context.getString(R.string.weak)
    private val mediumString = context.getString(R.string.medium)
    private val strongString = context.getString(R.string.strong)
    private val excellentString = context.getString(R.string.excellent)
    private val naString = context.getString(R.string.na)

    private val worstPassWarning = context.getString(R.string.worst_pass_warning)
    private val weakPassWarning = context.getString(R.string.weak_pass_warning)
    private val mediumPassWarning = context.getString(R.string.medium_pass_warning)

    private companion object {
        private const val WORST_SCORE = 1
        private const val WEAK_SCORE = 2
        private const val MEDIUM_SCORE = 3
        private const val STRONG_SCORE = 4
        private const val EXCELLENT_SCORE = 5
    }

    fun replaceCrackTimeStrings(timeToCrackString: String): String {
        var replacedString = timeToCrackString

        timeStringsReplacementMap.forEach { (key, value) ->
            if (replacedString.contains(key)) {
                return replacedString.replace(key, value)
            }
        }

        replacedString =
            timeStringsRegex.replace(replacedString) { matchResult ->
                val quantity = matchResult.groupValues[1]
                val timeUnit = matchResult.groupValues[2]
                val baseUnit = if (timeUnit.endsWith("s")) timeUnit.dropLast(1) else timeUnit
                val replacementPair = timeUnitsReplacementMap[baseUnit] ?: Pair(timeUnit, "${timeUnit}s")
                "$quantity ${if (quantity == "1") replacementPair.first else replacementPair.second}"
            }

        return  replacedString
    }

    // Custom score
    fun crackTimeScore(crackTimeMilliSeconds: Long): Int {
        return when (crackTimeMilliSeconds) {
            in 0..threeHrsInMillis -> WORST_SCORE
            in (threeHrsInMillis + 1)..oneMonthInMillis -> WEAK_SCORE
            in (oneMonthInMillis + 1)..sixMonthsInMillis -> MEDIUM_SCORE
            in (sixMonthsInMillis + 1)..fiveYrsInMillis -> STRONG_SCORE
            else -> EXCELLENT_SCORE
        }
    }

    fun setStrengthProgressAndText(
        crackTimeScore: Int,
        strengthMeter: LinearProgressIndicator,
        strengthTextView: MaterialTextView
    ) {
        val strengthProgTextMap = mapOf(
                WORST_SCORE to Triple(20, worstMeterColor, worstString),
                WEAK_SCORE to Triple(40, weakMeterColor, weakString),
                MEDIUM_SCORE to Triple(60, mediumMeterColor, mediumString),
                STRONG_SCORE to Triple(80, strongMeterColor, strongString),
                EXCELLENT_SCORE to Triple(100, excellentMeterColor, excellentString)
            )

        val (progress, indicatorColor, strengthText) =
            strengthProgTextMap[crackTimeScore] ?: Triple(0, emptyMeterColor, naString)

        strengthMeter.apply {
            setIndicatorColor(indicatorColor)
            setProgressCompat(progress, true)
        }

        strengthTextView.text = strengthText
    }

    fun getWarningText (
        localizedFeedback: Feedback,
        crackTimeScore: Int
    ): String {
        return localizedFeedback.warning
            .ifEmpty {
                when (crackTimeScore) {
                    WORST_SCORE -> worstPassWarning
                    WEAK_SCORE -> weakPassWarning
                    MEDIUM_SCORE -> mediumPassWarning
                    else -> naString
                }
            }
    }

}