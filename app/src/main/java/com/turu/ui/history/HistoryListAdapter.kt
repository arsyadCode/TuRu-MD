package com.turu.ui.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.turu.data.history.response.GetHistoryUserIdResponseItem
import com.turu.databinding.ItemHistoryBinding

class HistoryListAdapter :
    PagingDataAdapter<GetHistoryUserIdResponseItem, HistoryListAdapter.ViewHolder>(DIFF_CALLBACK){

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = getItem(position)
        if (data != null) {
            holder.bind(data)
            holder.itemView.setOnClickListener{
                onItemClickCallback.onItemClicked(data)
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