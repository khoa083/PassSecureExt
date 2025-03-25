package com.kblack.passsecureext.fragments.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.materialswitch.MaterialSwitch
import com.kblack.passsecureext.activities.MainActivity
import com.kblack.passsecureext.databinding.FragmentGeneratePasswordBinding
import com.kblack.passsecureext.preferences.PreferenceManager
import org.koin.android.ext.android.inject
import java.security.SecureRandom

class GeneratePasswordFragment : Fragment() {
    private var _binding: FragmentGeneratePasswordBinding? = null
    private val fragmentBinding get() = _binding!!
    private val prefManager by inject<PreferenceManager>()
    private lateinit var uppercaseSwitch: MaterialSwitch
    private lateinit var lowercaseSwitch: MaterialSwitch
    private lateinit var numbersSwitch: MaterialSwitch
    private lateinit var specialCharsSwitch: MaterialSwitch
    private lateinit var avoidAmbCharsSwitch: MaterialSwitch
    private lateinit var includeSpaceSwitch: MaterialSwitch
    private lateinit var primarySwitchesList: Array<MaterialSwitch>
    private lateinit var primarySwitchesPrefMap: Map<MaterialSwitch, String>
    private var uppercaseWithoutAmbChars = ""
    private var lowercaseWithoutAmbChars = ""
    private var numbersWithoutAmbChars = ""
    private val secureRandom by inject<SecureRandom>()

    private companion object {
        private const val UPPERCASE_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
        private const val LOWERCASE_CHARS = "abcdefghijklmnopqrstuvwxyz"
        private const val NUMBERS = "0123456789"
        private const val SPECIAL_CHARS = "!@#$%^&*+_-.="
        private const val UPPERCASE_AMB_CHARS = "ILOSBZ"
        private const val LOWERCASE_AMB_CHARS = "loz"
        private const val NUM_AMB_CHARS = "01258"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGeneratePasswordBinding.inflate(inflater, container, false)
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val mainActivity = requireActivity() as MainActivity
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}