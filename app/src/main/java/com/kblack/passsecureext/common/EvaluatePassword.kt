package com.kblack.passsecureext.common

import android.annotation.SuppressLint
import android.content.Context
import com.kblack.passsecureext.R
import com.kblack.passsecureext.databinding.FragmentTestPasswordBinding
import com.kblack.passsecureext.utils.FormatUtils.Companion.formatToTwoDecimalPlaces
import com.kblack.passsecureext.utils.LocaleUtils.Companion.localizedFeedbackResourceBundle
import com.kblack.passsecureext.utils.ResultUtils
import com.nulabinc.zxcvbn.Zxcvbn
import java.util.Locale
import kotlin.collections.get

class EvaluatePassword(
    zxcvbn: Zxcvbn,
    password: CharSequence,
    fragmentBinding: FragmentTestPasswordBinding,
    context: Context,
    resultUtils: ResultUtils
) {
    init {

        //Đo lường độ mạnh của mật khẩu
        val strength = zxcvbn.measure(password)
        val crackTimesDisplay = strength.crackTimesDisplay
        val crackTimeSeconds = strength.crackTimeSeconds

        //Thời gian bẻ khóa trong các kịch bản
        val tenBCrackTimeString = crackTimesDisplay.offlineFastHashing1e10PerSecond
        val tenKCrackTimeString = crackTimesDisplay.offlineSlowHashing1e4perSecond
        val tenCrackTimeString = crackTimesDisplay.onlineNoThrottling10perSecond
        val hundredCrackTimeString = crackTimesDisplay.onlineThrottling100perHour

        //Chuyển đổi thời gian sang mili giây
        val tenBCrackTimeMillis = (crackTimeSeconds.offlineFastHashing1e10PerSecond * 1000).toLong()
        val tenKCrackTimeMillis = (crackTimeSeconds.offlineSlowHashing1e4perSecond * 1000).toLong()
        val tenCrackTimeMillis = (crackTimeSeconds.onlineNoThrottling10perSecond * 1000).toLong()
        val hundredCrackTimeMillis = (crackTimeSeconds.onlineThrottling100perHour * 1000).toLong()

        // Estimated time to crack
        fragmentBinding.tenBGuessesSubtitle.text = resultUtils.replaceCrackTimeStrings(tenBCrackTimeString)
        val tenBCrackTimeScore = resultUtils.crackTimeScore(tenBCrackTimeMillis)
        resultUtils.setStrengthProgressAndText(tenBCrackTimeScore,
            fragmentBinding.tenBGuessesStrengthMeter,
            fragmentBinding.tenBGuessesStrength)

        fragmentBinding.tenKGuessesSubtitle.text = resultUtils.replaceCrackTimeStrings(tenKCrackTimeString)
        resultUtils.setStrengthProgressAndText(resultUtils.crackTimeScore(tenKCrackTimeMillis),
            fragmentBinding.tenKGuessesStrengthMeter,
            fragmentBinding.tenKGuessesStrength)

        fragmentBinding.tenGuessesSubtitle.text = resultUtils.replaceCrackTimeStrings(tenCrackTimeString)
        resultUtils.setStrengthProgressAndText(resultUtils.crackTimeScore(tenCrackTimeMillis),
            fragmentBinding.tenGuessesStrengthMeter,
            fragmentBinding.tenGuessesStrength)

        fragmentBinding.hundredGuessesSubtitle.text = resultUtils.replaceCrackTimeStrings(hundredCrackTimeString)
        resultUtils.setStrengthProgressAndText(resultUtils.crackTimeScore(hundredCrackTimeMillis),
            fragmentBinding.hundredGuessesStrengthMeter,
            fragmentBinding.hundredGuessesStrength)

        // Warning
        val localizedFeedback = strength.feedback.withResourceBundle(localizedFeedbackResourceBundle(context))
        fragmentBinding.warningSubtitle.text = resultUtils.getWarningText(localizedFeedback, tenBCrackTimeScore)

        // Suggestions
        fragmentBinding.suggestionsSubtitle.text = resultUtils.getSuggestionsText(localizedFeedback)

        // Guesses
        val guesses = strength.guesses
        fragmentBinding.guessesSubtitle.text = resultUtils.getGuessesText(guesses)

        // Order of magnitude of guesses
        fragmentBinding.orderMagnSubtitle.text = strength.guessesLog10.formatToTwoDecimalPlaces()

        // Entropy
        val statsList = resultUtils.getStatisticsCounts(password)
        @SuppressLint("SetTextI18n")
        fragmentBinding.entropySubtitle.text = "${resultUtils.getEntropyText(statsList)} ${context.getString(R.string.bits)}"

        // Match sequence
        fragmentBinding.matchSequenceSubtitle.text = resultUtils.getMatchSequenceText(strength)

        // Statistics
        fragmentBinding.lengthText.text = String.format(Locale.getDefault(), "%d", statsList[0])
        fragmentBinding.uppercaseText.text = String.format(Locale.getDefault(), "%d", statsList[1])
        fragmentBinding.lowercaseText.text = String.format(Locale.getDefault(), "%d", statsList[2])
        fragmentBinding.numbersText.text = String.format(Locale.getDefault(), "%d", statsList[3])
        fragmentBinding.specialCharsText.text = String.format(Locale.getDefault(), "%d", statsList[4])
        fragmentBinding.spacesText.text = String.format(Locale.getDefault(), "%d", statsList[5])

    }

}