package com.kblack.passsecureext.fragments.main

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updateLayoutParams
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment
import com.google.android.material.slider.Slider
import com.kblack.passsecureext.R
import com.kblack.passsecureext.activities.MainActivity
import com.kblack.passsecureext.databinding.FragmentGeneratePassphraseBinding
import com.kblack.passsecureext.preferences.PreferenceManager
import com.kblack.passsecureext.preferences.PreferenceManager.Companion.PHRASE_CAPITALIZE
import com.kblack.passsecureext.preferences.PreferenceManager.Companion.PHRASE_SEPARATOR
import com.kblack.passsecureext.preferences.PreferenceManager.Companion.PHRASE_WORDS
import com.kblack.passsecureext.utils.ClipboardUtils.Companion.hideSensitiveContent
import com.kblack.passsecureext.utils.UiUtils.Companion.convertDpToPx
import com.kblack.passsecureext.utils.UiUtils.Companion.showSnackBar
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

        // Password length slider
        fragmentBinding.phraseWordsSlider.apply {
            value = preManager.getFloat(PHRASE_WORDS, defValue = 5f)
            fragmentBinding.wordsText.text = "${getString(R.string.words)} : ${value.toInt()}"

            addOnSliderTouchListener(object : Slider.OnSliderTouchListener {
                override fun onStartTrackingTouch(slider: Slider) {}

                override fun onStopTrackingTouch(slider: Slider) {
                    generatePassphrase()
                }
            })

            addOnChangeListener { _, value, _ ->
                fragmentBinding.wordsText.text = "${R.string.words}: ${value.toInt()}"
            }
        }

        // Separator dropdown
        fragmentBinding.separatorText.text = getString(R.string.separator).removePrefix("\u2022 ")
        fragmentBinding.separatorDropdownMenu.apply {
            setText(preManager.getString(PHRASE_SEPARATOR))
            setAdapter(ArrayAdapter(requireContext(),
                R.layout.item_dropdown_menu,
                arrayOf("-", ".", ",", getString(R.string.spaces))
                ))

            setOnItemClickListener { _, _, _, _ ->
                generatePassphrase()
            }
        }

        // Capitalize
        fragmentBinding.capitalizeSwitch.apply {
            isChecked = preManager.getBoolean(PHRASE_CAPITALIZE)
            setOnCheckedChangeListener { _, _ ->
                generatePassphrase()
            }
        }

        generatePassphrase()

        // Copy
        fragmentBinding.phraseCopyBtn.setOnClickListener {
            val clipData = ClipData.newPlainText("", fragmentBinding.phraseGeneratedTextView.text)
            if (Build.VERSION.SDK_INT >= 24) clipData.hideSensitiveContent()
            (requireContext().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager).setPrimaryClip(clipData)
            // Only show snackbar in 12L or lower to avoid duplicate notifications
            // https://developer.android.com/develop/ui/views/touch-and-input/copy-paste#duplicate-notifications
            if (Build.VERSION.SDK_INT <= 32) {
                showSnackBar(mainActivity.activityBinding.mainCoordLayout,
                    requireContext().getString(R.string.copied_to_clipboard),
                    mainActivity.activityBinding.mainBottomNav
                    )
            }
        }

        // Regenerate
        fragmentBinding.phraseRegenerateBtn.setOnClickListener {
            generatePassphrase()
        }

        // Share
        fragmentBinding.phraseShareBtn.setOnClickListener {
            startActivity(Intent.createChooser(Intent(Intent.ACTION_SEND)
                .setType("text/plain")
                .putExtra(Intent.EXTRA_TEXT, fragmentBinding.phraseGeneratedTextView.text),
                getString(R.string.share)
            ))
        }

    }

    fun generatePassphrase() {
        val numberOfWords = fragmentBinding.phraseWordsSlider.value.toInt()

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