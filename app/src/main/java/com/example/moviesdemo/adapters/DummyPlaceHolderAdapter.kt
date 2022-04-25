package com.example.moviesdemo.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesdemo.R
import com.example.moviesdemo.models.SearchItemsModel

class DummyPlaceHolderAdapter(private var mList: ArrayList<SearchItemsModel>) :
    RecyclerView.Adapter<DummyPlaceHolderAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.search_frag_list_items, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val vList = mList[position]
        holder.imageItemWatch.setImageResource(vList.itemImage)
        holder.txtItemWatch.text = vList.itemCategory
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageItemWatch = itemView.findViewById(R.id.imageItemWatch) as ImageView
        val txtItemWatch = itemView.findViewById(R.id.txtItemWatch) as TextView
    }


}