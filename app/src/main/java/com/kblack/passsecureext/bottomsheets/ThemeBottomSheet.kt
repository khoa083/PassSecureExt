package com.kblack.passsecureext.bottomsheets

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.kblack.passsecureext.R
import com.kblack.passsecureext.databinding.BottomSheetFooterBinding
import com.kblack.passsecureext.databinding.BottomSheetHeaderBinding
import com.kblack.passsecureext.databinding.BottomSheetThemeBinding
import com.kblack.passsecureext.preferences.PreferenceManager
import com.kblack.passsecureext.preferences.PreferenceManager.Companion.THEME_PREF
import com.kblack.passsecureext.utils.UiUtils.Companion.setAppTheme
import org.koin.android.ext.android.inject

class ThemeBottomSheet : BottomSheetDialogFragment() {

    private var _binding: BottomSheetThemeBinding? = null
    private val bottomSheetBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = BottomSheetThemeBinding.inflate(inflater, container, false)
        return bottomSheetBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val prefManager by inject<PreferenceManager>()

        BottomSheetHeaderBinding.bind(bottomSheetBinding.root).bottomSheetTitle.text = getString(R.string.theme)

        bottomSheetBinding.followSystem.isVisible = Build.VERSION.SDK_INT >= 29

        // Radio group
        bottomSheetBinding.themeChipGroup.apply {

            // Default checked chip
            if (prefManager.getInt(THEME_PREF) == 0) {
                if (Build.VERSION.SDK_INT >= 29) {
                    prefManager.setInt(THEME_PREF, R.id.followSystem)
                }
                else {
                    prefManager.setInt(THEME_PREF, R.id.light)
                }
            }
            check(prefManager.getInt(THEME_PREF))

            // On selecting option
            setOnCheckedStateChangeListener { _, checkedIds ->
                val checkedChip = checkedIds.first()
                prefManager.setInt(THEME_PREF,checkedChip)
                setAppTheme(checkedChip)
                dismiss()
            }
        }

        BottomSheetFooterBinding.bind(bottomSheetBinding.root).cancelBtn.setOnClickListener { dismiss() }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}