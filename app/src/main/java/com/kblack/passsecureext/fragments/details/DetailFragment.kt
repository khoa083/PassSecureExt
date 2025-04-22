package com.kblack.passsecureext.fragments.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment
import com.kblack.passsecureext.activities.DetailsActivity
import com.kblack.passsecureext.databinding.FragmentTestPasswordBinding

class DetailFragment : Fragment() {
    private var _binding: FragmentTestPasswordBinding? = null
    private val fragmentBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTestPasswordBinding.inflate(inflater, container, false)
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val password = (requireActivity() as DetailsActivity).passwordLine

        // Adjust UI components for edge to edge
        ViewCompat.setOnApplyWindowInsetsListener(fragmentBinding.scrollView) { v, windowInsets ->
            val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars()
                                                        or WindowInsetsCompat.Type.displayCutout())
            v.updatePadding(left = insets.left, right = insets.right, bottom = insets.bottom)
            WindowInsetsCompat.CONSUMED
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}