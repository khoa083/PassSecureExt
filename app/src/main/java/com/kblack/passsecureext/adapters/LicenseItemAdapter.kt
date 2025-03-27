package com.kblack.passsecureext.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textview.MaterialTextView
import com.kblack.passsecureext.R
import com.kblack.passsecureext.activities.MainActivity
import com.kblack.passsecureext.models.License

class LicenseItemAdapter(
    private val aListViewItems: ArrayList<License>,
    private val mainActivity: MainActivity
): RecyclerView.Adapter<LicenseItemAdapter.ListViewHolder>() {

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val licenseTitle: MaterialTextView = itemView.findViewById(R.id.licenseTitle)
        val licenseDesc: MaterialTextView = itemView.findViewById(R.id.licenseDesc)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListViewHolder {
        return ListViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_licenses_recycler_view, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val license = aListViewItems[position]

        holder.licenseTitle.apply {
            text = license.title
            setOnClickListener {

            }
        }
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

}