package com.kblack.passsecureext.bottomsheets

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.kblack.passsecureext.R
import com.kblack.passsecureext.databinding.BottomSheetHeaderBinding
import com.kblack.passsecureext.databinding.BottomSheetTestMultiPwdBinding

class TestMultiPwdBottomSheet : BottomSheetDialogFragment() {

    private var _binding: BottomSheetTestMultiPwdBinding? = null
    private val bottomSheetBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = BottomSheetTestMultiPwdBinding.inflate(inflater, container, false)
        return bottomSheetBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        BottomSheetHeaderBinding.bind(bottomSheetBinding.root).bottomSheetTitle.text = getString(R.string.test_multi_pwds)

        bottomSheetBinding.addMultiple.setOnClickListener {
            dismiss()
            AddMultiPwdBottomSheet().show(parentFragmentManager, "AddMultiplePwdBottomSheet")
        }

        val intent =
            Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
                addCategory(Intent.CATEGORY_OPENABLE)
                type = "text/plain"
            }
        bottomSheetBinding.selectFile.setOnClickListener {
            filePicker.launch(intent)
        }

    }

    private var filePicker =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->

            if (result.resultCode == Activity.RESULT_OK ) {
                val data = result.data!!
                val fileUri = data.data
            }

        }

}