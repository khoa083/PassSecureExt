package com.kblack.passsecureext.bottomsheets

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.lifecycleScope
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.kblack.passsecureext.activities.MultiPwdActivity
import com.kblack.passsecureext.databinding.BottomSheetAddMultiPwdBinding
import com.kblack.passsecureext.databinding.BottomSheetFooterBinding
import com.kblack.passsecureext.databinding.BottomSheetHeaderBinding
import com.kblack.passsecureext.models.MultiPwdItem
import com.kblack.passsecureext.objects.MultiPwdList
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class AddMultiPwdBottomSheet : BottomSheetDialogFragment() {

    private var _binding: BottomSheetAddMultiPwdBinding? = null
    private val bottomSheetBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = BottomSheetAddMultiPwdBinding.inflate(inflater, container, false)
        return  bottomSheetBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        var job: Job? = null
        val footerBinding = BottomSheetFooterBinding.bind(bottomSheetBinding.root)

        BottomSheetHeaderBinding.bind(bottomSheetBinding.root).bottomSheetTitle.isVisible = false

        bottomSheetBinding.multiPwdText.doOnTextChanged { charSequence, _, _, _ ->
            job?.cancel()
            job = lifecycleScope.launch {
                delay(300)
                footerBinding.doneBtn.apply {
                    isEnabled = charSequence!!.isNotEmpty()
                }
            }
        }

        footerBinding.doneBtn.apply {
            isVisible = true
            setOnClickListener {
                val itemList =
                    bottomSheetBinding.multiPwdText.text!!.split("\n")
                        .filter { it.isNotEmpty() }
                        .map { MultiPwdItem(it) }
                MultiPwdList.pwdList.addAll(itemList)
                dismiss()
                startActivity(Intent(requireActivity(), MultiPwdActivity::class.java))
            }
        }

        footerBinding.cancelBtn.setOnClickListener { dismiss() }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}