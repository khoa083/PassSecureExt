package com.kblack.passsecureext.fragments.multipwd

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.kblack.passsecureext.activities.DetailsActivity
import com.kblack.passsecureext.activities.MultiPwdActivity
import com.kblack.passsecureext.adapters.MultiPwdAdapter
import com.kblack.passsecureext.databinding.FragmentMultiPwdBinding
import com.kblack.passsecureext.models.MultiPwdItem
import com.kblack.passsecureext.objects.MultiPwdList
import com.kblack.passsecureext.utils.UiUtils.Companion.convertDpToPx
import me.stellarsand.android.fastscroll.FastScrollerBuilder

class MultiPwdFragment : Fragment(), MultiPwdAdapter.OnItemClickListener {

    private var _binding: FragmentMultiPwdBinding? = null
    private val fragmentBinding get() = _binding!!
    private lateinit var multiplePwdList: List<MultiPwdItem>

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        // Inflate the layout for this fragment
        _binding = FragmentMultiPwdBinding.inflate(inflater, container, false)
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val multiPwdActivity = requireActivity() as MultiPwdActivity
        multiplePwdList =
            if (multiPwdActivity.isAscSort) MultiPwdList.pwdList.sortedBy { it.passwordLine.lowercase() }
            else MultiPwdList.pwdList.sortedByDescending { it.passwordLine.lowercase() }

        fragmentBinding.recyclerView.apply {
            // Adjust recyclerview for edge to edge
            ViewCompat.setOnApplyWindowInsetsListener(this) { v, windowInsets ->
                val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars()
                        or WindowInsetsCompat.Type.displayCutout())
                v.updatePadding(left = insets.left,
                    top = insets.top + convertDpToPx(requireContext(), 10f),
                    right = insets.right,
                    bottom = insets.bottom + convertDpToPx(requireContext(), 10f))
                WindowInsetsCompat.CONSUMED
            }

            adapter = MultiPwdAdapter(multiplePwdList, this@MultiPwdFragment)
            layoutManager =
                if (!multiPwdActivity.isGridView) LinearLayoutManager(requireContext())
                else StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            FastScrollerBuilder(this).build()
        }
    }

    // On click
    override fun onItemClick(position: Int) {
        startActivity(Intent(requireContext(), DetailsActivity::class.java)
            .putExtra("PwdLine", multiplePwdList[position].passwordLine))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}