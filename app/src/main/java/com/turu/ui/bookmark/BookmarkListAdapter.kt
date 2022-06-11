package com.turu.ui.bookmark

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.turu.R
import com.turu.data.bookmark.response.BookmarkResponseItem
import com.turu.databinding.ItemBookmarkBinding
import com.turu.ui.detaihistory.DetailHistoryActivity
import com.turu.ui.detaihistory.DetailHistoryViewModel
import com.turu.ui.detailbookmark.DetailBookmarkActivity
import com.turu.ui.detailbookmark.DetailBookmarkViewModel.Companion.EXTRA_ID
import com.turu.ui.detailbookmark.DetailBookmarkViewModel.Companion.EXTRA_LIST_PICTURES
import com.turu.ui.detailbookmark.DetailBookmarkViewModel.Companion.EXTRA_TEXT

class BookmarkListAdapter: PagingDataAdapter<BookmarkResponseItem, BookmarkListAdapter.ViewHolder>(DIFF_CALLBACK) {

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemBookmarkBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = getItem(position)
        if (data != null) {
            holder.bind(data)
            holder.itemView.setOnClickListener{
                val intent = Intent(holder.itemView.context, DetailBookmarkActivity::class.java)
                intent.putExtra(EXTRA_ID, data.id)
                intent.putExtra(EXTRA_TEXT, data.text)
                val listPictures = data.images.toTypedArray()
                intent.putExtra(EXTRA_LIST_PICTURES, listPictures)
                holder.itemView.context.startActivity(intent)
                Log.d("Bookmark", "Bookmark : ${data.id}, ${data.text}")
            }
        }
    }

    class ViewHolder(private val binding: ItemBookmarkBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(data: BookmarkResponseItem){
            binding.tvItemBookmark.text = data.text
        }
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