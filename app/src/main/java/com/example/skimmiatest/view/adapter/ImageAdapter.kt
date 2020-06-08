package com.example.skimmiatest.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.skimmiatest.R
import com.example.skimmiatest.databinding.ItemGalleryImageBinding
import com.example.skimmiatest.interfaces.ImageClickListener
import com.example.skimmiatest.model.Image

class ImageAdapter(private val itemList: List<Image>) : RecyclerView.Adapter<ImageAdapter.ViewHolder>() {
    private var context: Context? = null
    var listener: ImageClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val itemGalleryImageBinding = DataBindingUtil
            .inflate<ItemGalleryImageBinding>(LayoutInflater.from(parent.context), R.layout.item_gallery_image, parent, false)
        return ViewHolder(itemGalleryImageBinding)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val image = itemList[position]
        holder.itemGalleryImageBinding.image = image
        holder.bind()
    }

    inner class ViewHolder(val itemGalleryImageBinding: ItemGalleryImageBinding) : RecyclerView.ViewHolder(itemGalleryImageBinding.root) {

        fun bind() {
            // adding click or tap handler for our image layout
            itemView.setOnClickListener {
                listener?.onClick(adapterPosition)
            }
        }

    }

}