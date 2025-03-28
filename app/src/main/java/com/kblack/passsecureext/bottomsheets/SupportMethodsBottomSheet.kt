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
import com.kblack.passsecureext.adapters.SupportMethodItemAdapter
import com.kblack.passsecureext.databinding.BottomSheetFooterBinding
import com.kblack.passsecureext.databinding.BottomSheetHeaderBinding
import com.kblack.passsecureext.databinding.BottomSheetRecyclerViewBinding
import com.kblack.passsecureext.models.SupportMethod

class SupportMethodsBottomSheet : BottomSheetDialogFragment() {

    private var _binding : BottomSheetRecyclerViewBinding? = null
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

        BottomSheetHeaderBinding.bind(bottomSheetBinding.root).bottomSheetTitle.text = getString(R.string.support)

        val supportMethodsList = arrayListOf<SupportMethod>().apply {
            // Liberapay
            add(SupportMethod(title = getString(R.string.liberapay),
                titleIcon = R.drawable.ic_liberapay,
                qr = R.drawable.ic_liberapay_qr,
                url = getString(R.string.liberapay_url)))

            // PayPal
            add(SupportMethod(title = getString(R.string.paypal),
                titleIcon = R.drawable.ic_paypal,
                qr = R.drawable.ic_paypal_qr,
                url = getString(R.string.paypal_url)))

            // Ko-fi
            add(SupportMethod(title = getString(R.string.kofi),
                titleIcon = R.drawable.ic_kofi,
                qr = R.drawable.ic_kofi_qr,
                url = getString(R.string.kofi_url)))
        }

        bottomSheetBinding.licensesRecyclerView.adapter = SupportMethodItemAdapter(supportMethodsList, requireActivity() as MainActivity)

        BottomSheetFooterBinding.bind(bottomSheetBinding.root).cancelBtn.setOnClickListener { dismiss() }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}