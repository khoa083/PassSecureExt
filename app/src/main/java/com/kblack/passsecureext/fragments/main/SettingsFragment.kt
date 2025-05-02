package com.kblack.passsecureext.fragments.main

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment
import com.kblack.passsecureext.BuildConfig
import com.kblack.passsecureext.R
import com.kblack.passsecureext.activities.MainActivity
import com.kblack.passsecureext.bottomsheets.LicensesBottomSheet
import com.kblack.passsecureext.bottomsheets.SupportMethodsBottomSheet
import com.kblack.passsecureext.bottomsheets.ThemeBottomSheet
import com.kblack.passsecureext.databinding.FragmentSettingsBinding
import com.kblack.passsecureext.preferences.PreferenceManager
import com.kblack.passsecureext.preferences.PreferenceManager.Companion.BLOCK_SS
import com.kblack.passsecureext.preferences.PreferenceManager.Companion.INCOG_KEYBOARD
import com.kblack.passsecureext.preferences.PreferenceManager.Companion.MATERIAL_YOU
import com.kblack.passsecureext.utils.IntentUtils.Companion.openURL
import org.koin.android.ext.android.inject

class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val fragmentBinding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        // Inflate the layout for this fragment
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val prefManager by inject<PreferenceManager>()
        val mainActivity = requireActivity() as MainActivity

        // Adjust scrollview for edge to edge
        ViewCompat.setOnApplyWindowInsetsListener(fragmentBinding.settingsScrollView) { v, windowInsets ->
            val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars()
                    or WindowInsetsCompat.Type.displayCutout())
            v.updatePadding(left = insets.left, top = insets.top, right = insets.right, bottom = insets.bottom)
            WindowInsetsCompat.CONSUMED
        }

        // Version
        @SuppressLint("SetTextI18n")
        fragmentBinding.version.text = "${getString(R.string.app_version)}: ${BuildConfig.VERSION_NAME}"

        // Theme
        fragmentBinding.theme.setOnClickListener {
            ThemeBottomSheet().show(parentFragmentManager, "ThemeBottomSheet")
        }

        // Material You
        fragmentBinding.materialYouSwitch.apply {
            if (Build.VERSION.SDK_INT >= 31) {
                isVisible = true
                isChecked = prefManager.getBoolean(MATERIAL_YOU, defValue = false)
                setOnCheckedChangeListener { _, isChecked ->
                    prefManager.setBoolean(MATERIAL_YOU, isChecked)
                }
            }
        }

        // Block screenshots
        fragmentBinding.blockScreenshotsSwitch.apply {
            isChecked = prefManager.getBoolean(BLOCK_SS)
            setOnCheckedChangeListener { _, isChecked ->
                prefManager.setBoolean(BLOCK_SS, isChecked)
                when (isChecked) {
                    true -> mainActivity.window.setFlags(WindowManager.LayoutParams.FLAG_SECURE,
                        WindowManager.LayoutParams.FLAG_SECURE)
                    false -> mainActivity.window.clearFlags(WindowManager.LayoutParams.FLAG_SECURE)
                }
            }
        }

        // Incognito keyboard
        fragmentBinding.incognitoKeyboardSwitch.apply {
            isChecked = prefManager.getBoolean(INCOG_KEYBOARD)
            setOnCheckedChangeListener { _, isChecked ->
                prefManager.setBoolean(INCOG_KEYBOARD, isChecked)
            }
        }

        // Privacy policy
        fragmentBinding.privacyPolicy.setOnClickListener {
            openURL(mainActivity,
                getString(R.string.iyps_privacy_policy_url),
                mainActivity.activityBinding.mainCoordLayout,
                mainActivity.activityBinding.mainBottomNav)
        }

        // Report an issue
        fragmentBinding.reportIssue.setOnClickListener {
            openURL(mainActivity,
                getString(R.string.iyps_issues_url),
                mainActivity.activityBinding.mainCoordLayout,
                mainActivity.activityBinding.mainBottomNav)
        }

        // Support
        fragmentBinding.support.setOnClickListener {
            SupportMethodsBottomSheet().show(parentFragmentManager, "SupportBottomSheet")
        }

        // View on GitHub
        fragmentBinding.viewOnGit
            .setOnClickListener {
                openURL(mainActivity,
                    getString(R.string.iyps_github_url),
                    mainActivity.activityBinding.mainCoordLayout,
                    mainActivity.activityBinding.mainBottomNav)
            }

        // Third party licenses
        fragmentBinding.licenses.setOnClickListener {
            LicensesBottomSheet().show(parentFragmentManager, "LicensesBottomSheet")
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}