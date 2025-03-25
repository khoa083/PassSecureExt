package com.kblack.passsecureext.fragments.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.kblack.passsecureext.activities.MainActivity
import com.kblack.passsecureext.databinding.FragmentSettingsBinding
import com.kblack.passsecureext.preferences.PreferenceManager
import org.koin.android.ext.android.inject

class SettingsFragment : Fragment() {
    private var _binding: FragmentSettingsBinding? = null
    private val fragmentBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val prefManager by inject<PreferenceManager>()
        val mainActivity = requireActivity() as MainActivity
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}