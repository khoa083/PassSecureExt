package com.kblack.passsecureext.fragments.main

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updateLayoutParams
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment
import com.kblack.passsecureext.activities.MainActivity
import com.kblack.passsecureext.databinding.FragmentGeneratePassphraseBinding
import com.kblack.passsecureext.preferences.PreferenceManager
import com.kblack.passsecureext.utils.UiUtils.Companion.convertDpToPx
import org.koin.android.ext.android.inject

class GeneratePassphraseFragment : Fragment() {

    private var _binding: FragmentGeneratePassphraseBinding? = null
    private val fragmentBinding get() = _binding!!
    private val preManager by inject<PreferenceManager>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGeneratePassphraseBinding.inflate(inflater, container, false)
        return fragmentBinding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val mainActivity = requireActivity() as MainActivity

        // Adjust scrollview for edge to edge
        ViewCompat.setOnApplyWindowInsetsListener(fragmentBinding.phraseScrollView) { v, windowInsets ->
            val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars()
                                                        or WindowInsetsCompat.Type.displayCutout())
            v.updatePadding(left = insets.left, top = insets.top, right = insets.right)
            v.updateLayoutParams<ViewGroup.MarginLayoutParams> {
                bottomMargin = insets.bottom + convertDpToPx(requireContext(), 64f)
            }
            WindowInsetsCompat.CONSUMED
        }

    }

    override fun onPause() {
        super.onPause()
        preManager.apply {

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}