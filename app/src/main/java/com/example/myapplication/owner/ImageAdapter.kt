package com.example.myapplication.owner

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import kotlinx.android.synthetic.main.bottomsheet_image_row.view.*
import kotlinx.android.synthetic.main.row_slider_images.view.ivRow

class ImageAdapter(val context: Context, val images: ArrayList<Int>):RecyclerView.Adapter<ImageAdapter.MyViewHolder>() {
    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageAdapter.MyViewHolder {
        val view= LayoutInflater.from(context).inflate(R.layout.bottomsheet_image_row,parent,false)
        return ImageAdapter.MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageAdapter.MyViewHolder, position: Int) {
//        holder.itemView.ivRow.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.chanel))
        holder.itemView.ivRow.setImageResource(images[position])
        holder.itemView.ivDelete.setOnClickListener {
            images.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position,images.size)
        }
    }

    override fun getItemCount(): Int {
        return images.size
    }

}
