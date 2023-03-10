package com.renatsayf.trade.adapters

import androidx.core.net.toUri
import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.Glide
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import com.renatsayf.trade.databinding.ItemProductPhotoBinding

class ProductImageAdapter {

    companion object {

        val diffCallback = object : DiffUtil.ItemCallback<String>(){
            override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
                return oldItem.hashCode() == newItem.hashCode()
            }

            override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
                return oldItem == newItem
            }
        }
    }

    fun productImageDelegate(onItemClick: (Int) -> Unit) =
        adapterDelegateViewBinding<String, String, ItemProductPhotoBinding>({layoutInflater, parent ->
            ItemProductPhotoBinding.inflate(layoutInflater, parent, false)
        }) {

            bind {
                with(binding) {

                    Glide.with(context).load(item.toUri()).into(imgProductPhoto)
                    imgProductPhoto.setOnClickListener {
                        //onItemClick(adapterPosition)
                    }
                    //onItemClick.invoke(adapterPosition)
                }
            }
        }
}