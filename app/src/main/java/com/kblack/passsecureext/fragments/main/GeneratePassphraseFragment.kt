package com.kblack.passsecureext.fragments.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.kblack.passsecureext.databinding.FragmentGeneratePassphraseBinding
import com.kblack.passsecureext.preferences.PreferenceManager
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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