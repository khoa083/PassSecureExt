package com.kblack.passsecureext.bottomsheets

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.kblack.passsecureext.R
import com.kblack.passsecureext.activities.MainActivity
import com.kblack.passsecureext.adapters.LicenseItemAdapter
import com.kblack.passsecureext.databinding.BottomSheetFooterBinding
import com.kblack.passsecureext.databinding.BottomSheetHeaderBinding
import com.kblack.passsecureext.databinding.BottomSheetRecyclerViewBinding
import com.kblack.passsecureext.models.License

class LicensesBottomSheet : BottomSheetDialogFragment() {

    private var _binding: BottomSheetRecyclerViewBinding? = null
    private val bottomSheetBinding get() = _binding!!

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog
        = (super.onCreateDialog(savedInstanceState) as BottomSheetDialog).apply {
            behavior.state = BottomSheetBehavior.STATE_EXPANDED
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = BottomSheetRecyclerViewBinding.inflate(inflater, container, false)
        return bottomSheetBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        BottomSheetHeaderBinding.bind(bottomSheetBinding.root).bottomSheetTitle.text = getString(R.string.third_party_licenses)

        val licensesList = arrayListOf<License>().apply {

            // zxcvbn4j
            add(License(title = getString(R.string.zxcvbn4j),
                desc = "${getString(R.string.copyright_zxcvbn4j)}\n\n${getString(R.string.mit_license)}",
                url = getString(R.string.zxcvbn4j_license_url)))

            // SecLists
            add(License(title = getString(R.string.seclists),
                desc = "${getString(R.string.copyright_seclists)}\n\n${getString(R.string.mit_license)}",
                url = getString(R.string.seclists_license_url)))

            // EFF Diceware
            add(License(title = getString(R.string.eff_diceware),
                desc = getString(R.string.cc_3_0_license),
                url = getString(R.string.eff_diceware_license_url)))

            // Fastscroll
            add(License(title = getString(R.string.fastscroll),
                desc = "${getString(R.string.copyright_fastscroll)}\n\n${getString(R.string.apache_2_0_license)}",
                url = getString(R.string.fastscroll_license_url)))

            // Liberapay
            add(License(title = getString(R.string.liberapay_icon),
                desc = getString(R.string.cc0_1_0_universal_public_domain_license),
                url = getString(R.string.liberapay_icon_license_url)))

            // PayPal
            add(License(title = getString(R.string.paypal_icon),
                desc = "",
                url = getString(R.string.paypal_icon_license_url)))

            // Ko-fi
            add(License(title = getString(R.string.kofi_icon),
                desc = "",
                url = getString(R.string.kofi_icon_license_url)))

        }

        bottomSheetBinding.licensesRecyclerView.adapter = LicenseItemAdapter( licensesList,requireActivity() as MainActivity )

        BottomSheetFooterBinding.bind(bottomSheetBinding.root).cancelBtn.setOnClickListener { dismiss() }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}