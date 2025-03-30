package com.kblack.passsecureext.bottomsheets

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.lifecycleScope
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.kblack.passsecureext.R
import com.kblack.passsecureext.activities.MultiPwdActivity
import com.kblack.passsecureext.databinding.BottomSheetHeaderBinding
import com.kblack.passsecureext.databinding.BottomSheetTestMultiPwdBinding
import com.kblack.passsecureext.models.MultiPwdItem
import com.kblack.passsecureext.objects.MultiPwdList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.FileNotFoundException
import java.io.IOException
import java.io.InputStreamReader

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

                lifecycleScope.launch(Dispatchers.IO) {
                    try {
                        val inputStream = requireActivity().contentResolver.openInputStream(fileUri!!)
                        val bufferedReader = BufferedReader(InputStreamReader(inputStream))
                        var lineList: Array<String>

                        MultiPwdList.pwdList.apply {
                            if (isNotEmpty()) clear()

                            while (bufferedReader.readLine().also { lineList = arrayOf(it) } != null) {
                                for (line in lineList) {
                                    if (line.isNotEmpty()) add(MultiPwdItem(passwordLine = line))
                                }
                            }
                        }

                        inputStream!!.close()
                        bufferedReader.close()
                    } catch (fileNotFoundException: FileNotFoundException) {
                        fileNotFoundException.printStackTrace()
                        withContext(Dispatchers.Main) {
                            //FIXME:
                        }
                    } catch (ioException: IOException) {
                        ioException.printStackTrace()
                        withContext(Dispatchers.Main) {
                            //FIXME:
                        }
                    }
                    withContext(Dispatchers.Main) {
                        dismiss()
                        startActivity(Intent(requireActivity(), MultiPwdActivity::class.java))
                    }
                }
            }

        }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}