package com.example.myapplication.login

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R
import com.example.myapplication.owner.OwnerActivity
import kotlinx.android.synthetic.main.activity_login.*

class LogInActivity:AppCompatActivity(), View.OnClickListener {

    var isEmailValid:Boolean = false
    var isPasswordValid:Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }

    override fun onClick(p0: View?) {
        if (p0 != null) {
            when(p0.id){
                R.id.btnSignIn->{
//                    SetValidation()
                    val ownerIntent= Intent(this, OwnerActivity::class.java)
                    startActivity(ownerIntent)
                }
            }
        }
    }

    fun SetValidation() {
        // Check for a valid email address.
        if (etUserName.getText().toString().isEmpty()) {
            etUserName.setError(resources.getString(R.string.email_error))
            isEmailValid = false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(etUserName.getText().toString()).matches()) {
            etUserName.setError(resources.getString(R.string.error_invalid_email))
            isEmailValid = false
        } else {
            isEmailValid = true
        }

        // Check for a valid password.
        if (etPassword.getText().toString().isEmpty()) {
            etPassword.setError(resources.getString(R.string.password_error))
            isPasswordValid = false
        } else if (etPassword.getText()!!.length < 6) {
            etPassword.setError(resources.getString(R.string.error_invalid_password))
            isPasswordValid = false
        } else {
            isPasswordValid = true
        }
        if (isEmailValid && isPasswordValid) {
            Toast.makeText(applicationContext, "Successfully", Toast.LENGTH_SHORT).show()
        }
    }
}