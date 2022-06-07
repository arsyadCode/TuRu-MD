package com.turu.ui.texttoimage

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.turu.R

class TextToImageAdapter: RecyclerView.Adapter<TextToImageAdapter.ViewHolder>(){

    private var listPictures: List<String> = emptyList()

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.imageView)
    }

    fun setListPictures(newListPictures: List<String>){
        listPictures = newListPictures
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TextToImageAdapter.ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_text_to_image, parent, false))
    }

    override fun onBindViewHolder(holder: TextToImageAdapter.ViewHolder, position: Int) {
        Glide.with(holder.itemView.context)
            .load(listPictures[position])
            .into(holder.imageView)
        Log.d(TAG, listPictures[position])
    }

    override fun getItemCount(): Int = listPictures.size

    companion object {
        const val TAG = "TextToImageAdapter"
    }
}