package com.example.myapplication.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R
import com.example.myapplication.login.LogInActivity
import com.example.myapplication.owner.OwnerActivity
import com.example.myapplication.shopkeeper.ShopkeeperActivity

class HomeActivity:AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
    }

    override fun onClick(p0: View?) {
        if (p0 != null) {
            when(p0.id){
                R.id.tvShopOwner -> {
                    Log.d("TAG", "onClick: ShopKeeper")
                    val loginIntent = Intent(this, LogInActivity::class.java)
                    startActivity(loginIntent)
                }
                R.id.tvShopKeeper->{
                    Log.e("Checking","onClick")
                    val shopkeeperIntent= Intent(this, ShopkeeperActivity::class.java)
                    startActivity(shopkeeperIntent)
                }
            }
        }
    }
}