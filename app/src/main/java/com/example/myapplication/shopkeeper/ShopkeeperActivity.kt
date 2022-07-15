package com.example.myapplication.shopkeeper

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R
import com.example.myapplication.display.DisplayActivity
import kotlinx.android.synthetic.main.activity_shopkeeper.*

class ShopkeeperActivity:AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {

        val branchList= arrayOf("Select your Branch","Varachha","Adajan","Vesu")

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shopkeeper)

        spinnerBranch.onItemSelectedListener
        val branchAdapter=BranchAdapter(this,branchList)
        spinnerBranch.adapter=branchAdapter
    }

    override fun onClick(p0: View?) {
        if (p0 != null) {
            when(p0.id){
                R.id.btnCheckIn->{
                    Log.e("checking","Sign In Onclick")
                    val displayIntent=Intent(this,DisplayActivity::class.java)
                    startActivity(displayIntent)
                }
            }
        }
    }
}