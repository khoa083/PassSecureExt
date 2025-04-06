package com.kblack.passsecureext.utils

import android.content.Context
import com.kblack.passsecureext.R

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

}