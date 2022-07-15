package com.example.myapplication.shopkeeper

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.example.myapplication.R
import kotlinx.android.synthetic.main.row_sp_branch.view.*

class BranchAdapter(val context: Context, val branchList: Array<String>): BaseAdapter() {

    val inflater  = LayoutInflater.from(context)

    override fun getCount(): Int {
        return branchList.size
    }

    override fun getItem(p0: Int): Any {
        return branchList[p0]
    }

    override fun getItemId(p0: Int): Long {
        return 0
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        val view = inflater.inflate(R.layout.row_sp_branch,null)
        view.tvBranch.text=branchList[p0]
        return view
    }
}