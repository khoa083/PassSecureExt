package com.kblack.passsecureext.fragments.details

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.core.view.updateLayoutParams
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment
import com.kblack.passsecureext.R
import com.kblack.passsecureext.activities.DetailsActivity
import com.kblack.passsecureext.common.EvaluatePassword
import com.kblack.passsecureext.databinding.FragmentTestPasswordBinding
import com.kblack.passsecureext.utils.ResultUtils
import com.kblack.passsecureext.utils.UiUtils.Companion.convertDpToPx
import com.nulabinc.zxcvbn.Zxcvbn
import org.koin.android.ext.android.get

class DetailFragment : Fragment() {
    private var _binding: FragmentTestPasswordBinding? = null
    private val fragmentBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTestPasswordBinding.inflate(inflater, container, false)
        return fragmentBinding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val password = (requireActivity() as DetailsActivity).passwordLine

        // Adjust UI components for edge to edge
        ViewCompat.setOnApplyWindowInsetsListener(fragmentBinding.scrollView) { v, windowInsets ->
            val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars()
                                                        or WindowInsetsCompat.Type.displayCutout())
            v.updatePadding(left = insets.left, right = insets.right, bottom = insets.bottom)
            WindowInsetsCompat.CONSUMED
        }
        ViewCompat.setOnApplyWindowInsetsListener(fragmentBinding.passwordBox) { v, windowInsets ->
            val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars()
                                                        or WindowInsetsCompat.Type.displayCutout())
            v.updatePadding(left = insets.left, right = insets.right)
            v.updateLayoutParams<ViewGroup.MarginLayoutParams> {
                topMargin = insets.top + convertDpToPx(requireContext(), 12f)
            }
            WindowInsetsCompat.CONSUMED
        }

        fragmentBinding.apply {
            lengthSubtitle.text = "\u2022 ${getString(R.string.length)}"
            uppercaseSubtitle.text = "\u2022 ${getString(R.string.uppercase)}"
            lowercaseSubtitle.text = "\u2022 ${getString(R.string.lowercase)}"
            numbersSubtitle.text = "\u2022 ${getString(R.string.numbers)}"
            specialCharsSubtitle.text = "\u2022 ${getString(R.string.special_char)}"
            spacesSubtitle.text = "\u2022 ${getString(R.string.spaces)}"
            testMultipleFab.isVisible = false
            passwordText.apply {
                setText(password)
                isFocusable = false
                isCursorVisible = false
            }
        }

        EvaluatePassword(zxcvbn = get<Zxcvbn>(),
            password = password,
            fragmentBinding = fragmentBinding,
            context = requireContext(),
            resultUtils = ResultUtils(requireContext())
        )

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}