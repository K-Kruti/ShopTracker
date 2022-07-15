package com.example.myapplication.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R
import com.example.myapplication.home.HomeActivity
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val spannableString= SpannableString(tvAppName.text.toString())
        spannableString.setSpan(ForegroundColorSpan(resources.getColor(R.color.colorLightGreen)),5,spannableString.length,SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE)
        tvAppName.setText(spannableString)

        Handler().postDelayed({
            val homeIntent=Intent(this,HomeActivity::class.java)
            startActivity(homeIntent)
        },3000)
    }
}