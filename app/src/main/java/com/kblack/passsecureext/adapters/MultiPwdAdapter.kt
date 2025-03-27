package com.kblack.passsecureext.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kblack.passsecureext.R
import com.kblack.passsecureext.models.MultiPwdItem
import me.stellarsand.android.fastscroll.PopupTextProvider

class MultiPwdAdapter(
    private val aListViewItems: List<MultiPwdItem>,
    private val clickListener: OnItemClickListener
): RecyclerView.Adapter<MultiPwdAdapter.ListViewHolder>(), PopupTextProvider {

    interface OnItemClickListener {
        fun onItemClick(p: Int)
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        val passwordLine: TextView = itemView.findViewById(R.id.password_line)

        init {
            // Handle click events of items
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val p = bindingAdapterPosition
            if(p != RecyclerView.NO_POSITION) clickListener.onItemClick(p)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_file_recycler_view, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.passwordLine.text = aListViewItems[position].passwordLine
    }

    override fun getItemCount(): Int = aListViewItems.size

    override fun getItemViewType(position: Int): Int = position

    override fun getPopupText(view: View, position: Int): CharSequence {
        return aListViewItems[position].passwordLine.first().let {
            if (it.isLowerCase()) it.uppercase()
            else it
        }.toString()
    }
}