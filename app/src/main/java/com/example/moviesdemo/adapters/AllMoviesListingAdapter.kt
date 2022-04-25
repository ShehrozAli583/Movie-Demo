package com.example.moviesdemo.adapters

import android.content.Context
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviesdemo.R
import com.example.moviesdemo.interfaces.ItemClickListener
import com.example.moviesdemo.utils.Constants
import com.example.moviesdemo.models.UpcomingResult
import java.util.*

class AllMoviesListingAdapter(
    private val mCtx: Context,
    private val mList: ArrayList<UpcomingResult>,
    private var onClick: ItemClickListener
) : RecyclerView.Adapter<AllMoviesListingAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.watch_frag_list_items, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val vList = mList[position]
        Glide.with(mCtx).load(Constants.IMAGE_URL + vList.backdropPath)
            .placeholder(R.drawable.kings_man_main).into(holder.imageItemWatch)

        holder.txtItemWatch.text = vList.title
        holder.txtYear.text = "Released On: " + vList.releaseDate
        holder.cardItemWatch.setOnClickListener {
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
        val cardItemWatch = itemView.findViewById(R.id.cardItemWatch) as CardView
        val imageItemWatch = itemView.findViewById(R.id.imageItemWatch) as ImageView
        val txtItemWatch = itemView.findViewById(R.id.txtItemWatch) as TextView
        val txtYear = itemView.findViewById(R.id.txtYear) as TextView
    }
}