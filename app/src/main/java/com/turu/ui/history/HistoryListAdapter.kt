package com.turu.ui.history

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.turu.data.history.response.GetHistoryUserIdResponseItem
import com.turu.databinding.ItemHistoryBinding
import com.turu.ui.detaihistory.DetailHistoryActivity
import com.turu.ui.detaihistory.DetailHistoryViewModel.Companion.EXTRA_ID
import com.turu.ui.detaihistory.DetailHistoryViewModel.Companion.EXTRA_LIST_PICTURES
import com.turu.ui.detaihistory.DetailHistoryViewModel.Companion.EXTRA_TEXT


class HistoryListAdapter : PagingDataAdapter<GetHistoryUserIdResponseItem, HistoryListAdapter.ViewHolder>(DIFF_CALLBACK){

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = getItem(position)
        if (data != null) {
            holder.bind(data)
            holder.itemView.setOnClickListener{
                val intent = Intent(holder.itemView.context, DetailHistoryActivity::class.java)
                intent.putExtra(EXTRA_ID, data.id)
                intent.putExtra(EXTRA_TEXT, data.text)
                val listPictures = data.images.toTypedArray()
                intent.putExtra(EXTRA_LIST_PICTURES, listPictures)
                holder.itemView.context.startActivity(intent)
                Log.d("History", "History : ${data.id}, ${data.text}")
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemHistoryBinding.inflate(LayoutInflater.from(parent.context), parent,false)
        return ViewHolder(binding)
    }

    class ViewHolder(private val binding: ItemHistoryBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(data: GetHistoryUserIdResponseItem) {
            binding.tvItemHistory.text = data.text
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: GetHistoryUserIdResponseItem)
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<GetHistoryUserIdResponseItem>() {
            override fun areItemsTheSame(oldItem: GetHistoryUserIdResponseItem, newItem: GetHistoryUserIdResponseItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: GetHistoryUserIdResponseItem, newItem: GetHistoryUserIdResponseItem): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}