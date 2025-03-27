package com.kblack.passsecureext.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.textview.MaterialTextView
import com.kblack.passsecureext.R
import com.kblack.passsecureext.activities.MainActivity
import com.kblack.passsecureext.models.SupportMethod
import com.kblack.passsecureext.utils.IntentUtils.Companion.openURL

class SupportMethodItemAdapter(
    private val aListViewItems: ArrayList<SupportMethod>,
    private val mainActivity: MainActivity
): RecyclerView.Adapter<SupportMethodItemAdapter.ListViewHolder>() {

    inner class ListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val supportMethodTitle: MaterialTextView = itemView.findViewById(R.id.supportMethodTitle)
        val supportMethodQR: ShapeableImageView = itemView.findViewById(R.id.supportMethodQr)
        val supportMethodUrl: MaterialTextView = itemView.findViewById(R.id.supportMethodUrl)
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder
    = ListViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_support_methods_recycler_view, parent, false))

    override fun getItemCount(): Int = aListViewItems.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {

        val supportMethod = aListViewItems[position]

        holder.supportMethodTitle.apply {
            text = supportMethod.title
            setCompoundDrawablesWithIntrinsicBounds(supportMethod.titleIcon, 0, 0, 0)
        }

        holder.supportMethodQR.setImageResource(supportMethod.qr)

        holder.supportMethodUrl.apply {
            text = supportMethod.url
            setOnClickListener{
                openURL(mainActivity, supportMethod.url)
            }
        }

    }
}