package com.kblack.passsecureext.common

import android.content.Context
import com.kblack.passsecureext.databinding.FragmentTestPasswordBinding
import com.kblack.passsecureext.utils.ResultUtils
import com.nulabinc.zxcvbn.Zxcvbn

class EvaluatePassword(
    zxcvbn: Zxcvbn,
    password: CharSequence,
    fragmentBinding: FragmentTestPasswordBinding,
    context: Context,
    resultUtils: ResultUtils
) {
    init {
        val strength = zxcvbn.measure(password)
        //....
    }

}