package com.kblack.passsecureext.fragments.main

import android.content.ClipboardManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.kblack.passsecureext.activities.MainActivity
import com.kblack.passsecureext.databinding.FragmentTestPasswordBinding

class TestPasswordFragment : Fragment() {
    private var _binding: FragmentTestPasswordBinding? = null
    private val fragmentBinding get() = _binding!!
    private lateinit var mainActivity: MainActivity
    private lateinit var naString: String
    private var emptyMeterColor = 0
    private lateinit var clipboardManager: ClipboardManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTestPasswordBinding.inflate(inflater, container, false)
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        clipboardManager.apply {
            if( hasPrimaryClip() && primaryClipDescription?.label == "IYPS") null
        }
        _binding = null
    }

}