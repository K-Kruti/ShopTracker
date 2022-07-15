package com.example.myapplication.owner

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R

class TotalShopAdapter(val context: Context): RecyclerView.Adapter<TotalShopAdapter.MyViewHolder>() {
    class MyViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TotalShopAdapter.MyViewHolder {
        val view=LayoutInflater.from(context).inflate(R.layout.row_list,parent,false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: TotalShopAdapter.MyViewHolder, position: Int) {
    }

    override fun getItemCount(): Int {
        return 5
    }
}