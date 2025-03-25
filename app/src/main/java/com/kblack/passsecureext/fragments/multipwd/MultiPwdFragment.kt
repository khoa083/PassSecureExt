package com.kblack.passsecureext.fragments.multipwd

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.kblack.passsecureext.databinding.FragmentMultiPwdBinding
import com.kblack.passsecureext.models.MultiPwdItem

class MultiPwdFragment : Fragment() {
    private var _binding: FragmentMultiPwdBinding? = null
    private val fragmentBinding get() = _binding!!
    private lateinit var multiplePwdList: List<MultiPwdItem>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMultiPwdBinding.inflate(inflater, container, false)
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}