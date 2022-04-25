package com.example.moviesdemo.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviesdemo.R
import com.example.moviesdemo.interfaces.ItemClickListener
import com.example.moviesdemo.models.SearchedResult
import com.example.moviesdemo.utils.Constants

class SearchResultListingAdapter(
    private var mCtx: Context,
    private var mList: ArrayList<SearchedResult>,
    private var onClick: ItemClickListener
) :
    RecyclerView.Adapter<SearchResultListingAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.search_result_list_items, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val vList = mList[position]

        Glide.with(mCtx).load(Constants.IMAGE_URL + vList.backdropPath)
            .placeholder(R.drawable.kings_man_main).into(holder.imageSearchResult)
        holder.titleSearchResult.text = vList.title
        holder.subTitleSearchResult.text = "Rating " + vList.voteAverage.toString()
        holder.relContainer.setOnClickListener {
            onClick.watchItemCLicked(vList.id)
        }
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
        val imageSearchResult = itemView.findViewById(R.id.imageSearchResult) as ImageView
        val titleSearchResult = itemView.findViewById(R.id.titleSearchResult) as TextView
        val subTitleSearchResult = itemView.findViewById(R.id.subTitleSearchResult) as TextView
        val relContainer = itemView.findViewById(R.id.relContainer) as ConstraintLayout
    }
}