package com.example.moviesdemo.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesdemo.R
import com.example.moviesdemo.models.Genre

class GenresListingAdapter(private var mList: List<Genre>) :
    RecyclerView.Adapter<GenresListingAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.genres_list_items, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val vList = mList[position]
        holder.txtGenre.text = vList.name
//        holder.cardGenre.setCardBackgroundColor(mCtx.colorList(vList.itemColor))
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
        val txtGenre = itemView.findViewById(R.id.txtGenre) as TextView
    }


}