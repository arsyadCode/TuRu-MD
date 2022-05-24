package com.turu.ui.bookmark

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.turu.R
import com.turu.data.bookmark.BookmarkResponseItem

class BookmarkAdapter: PagingDataAdapter<BookmarkResponseItem, BookmarkAdapter.ViewHolder>(DIFF_CALLBACK) {

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_bookmark, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = getItem(position)
        if (data != null) {
            holder.tvWord.text = data.text
            holder.itemView.setOnClickListener {
                onItemClickCallback.onItemClicked(data)
            }
        }
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val tvWord : TextView = view.findViewById(R.id.tv_word)
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: BookmarkResponseItem)
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<BookmarkResponseItem>() {
            override fun areItemsTheSame(oldItem: BookmarkResponseItem, newItem: BookmarkResponseItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: BookmarkResponseItem, newItem: BookmarkResponseItem): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}