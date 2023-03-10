package com.renatsayf.trade.adapters

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.recyclerview.widget.DiffUtil
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import com.renatsayf.trade.databinding.ItemColorBoxBinding

class ColorListAdapter {

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

    fun productColorDelegate(onItemClick: (String) -> Unit) =
        adapterDelegateViewBinding<String, String, ItemColorBoxBinding>({layoutInflater, parent ->
            ItemColorBoxBinding.inflate(layoutInflater, parent, false)
        }) {

            bind {

                with(binding) {

                    val colorDrawable = when (item) {
                        "#ffffff" -> ColorDrawable(Color.TRANSPARENT)
                        else -> ColorDrawable(Color.parseColor(item))
                    }
                    viewColor.foreground = colorDrawable
                    viewColor.setOnClickListener {
                        onItemClick.invoke(item)
                    }
                }
            }
        }


}