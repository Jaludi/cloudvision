package com.google.sample.cloudvision

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView


import com.bumptech.glide.Glide

import java.util.ArrayList


class RecycleViewAdapter(private val urlList: ArrayList<String>, private val context: Context) : RecyclerView.Adapter<RecycleViewAdapter.ViewHolder>() {
    private val mInflater: LayoutInflater
    private var mClickListener: ItemClickListener? = null

    init {
        this.mInflater = LayoutInflater.from(context)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecycleViewAdapter.ViewHolder {
        val view = mInflater.inflate(R.layout.grid_item, parent, false)
        val viewHolder = ViewHolder(view)
        return viewHolder
    }

    override fun onBindViewHolder(holder: RecycleViewAdapter.ViewHolder, position: Int) {
        val url = urlList[position]
        //holder.myTextView.setText(url);
        Glide
                .with(context)
                .load(urlList[position])
                .into(holder.myImageView)
    }

    override fun getItemCount(): Int {
        return urlList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        var myImageView: ImageView

        init {
            myImageView = itemView.findViewById(R.id.imageView) as ImageView
            itemView.setOnClickListener(this)
        }


        override fun onClick(view: View) {
            if (mClickListener != null) mClickListener!!.onItemClick(view, adapterPosition)
        }
    }

    fun getItem(id: Int): String {
        return urlList[id]
    }

    // allows clicks events to be caught
    fun setClickListener(itemClickListener: ItemClickListener) {
        this.mClickListener = itemClickListener
    }

    interface ItemClickListener {
        fun onItemClick(view: View, position: Int)
    }
}
