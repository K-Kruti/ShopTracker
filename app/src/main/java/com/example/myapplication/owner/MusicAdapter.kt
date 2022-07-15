package com.example.myapplication.owner

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import kotlinx.android.synthetic.main.row_music.view.*

class MusicAdapter(var ownerActivity: OwnerActivity, val list: ArrayList<String>):RecyclerView.Adapter<MusicAdapter.MyViewHolder>() {

    class MyViewHolder(view: View): RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicAdapter.MyViewHolder {
        val itemView=LayoutInflater.from(parent.context).inflate(R.layout.row_music,parent,false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MusicAdapter.MyViewHolder, position: Int) {
        holder.itemView.tvMusicName.text=list[position]

        holder.itemView.ivDelete.setOnClickListener {
//            holder.itemView.ivDelete.setEnabled(false)
//            holder.itemView.tvMusicName.setEnabled(false)
            list.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position,list.size)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}