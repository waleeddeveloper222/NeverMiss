package com.waleed.nevermiss.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.google.firebase.auth.FirebaseUser
import com.waleed.nevermiss.R
import com.waleed.nevermiss.ui.sign.LoginActivity

class SplashActivity : AppCompatActivity() {

    private val TIME_OUT = 2000
    internal var firebaseUser: FirebaseUser? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_spalsh)

        checkUser()
    }


    private fun checkUser() {

        Handler().postDelayed({
            val i: Intent
            if (firebaseUser != null) {
                i = Intent(this, MainActivity::class.java)
            } else {
                i = Intent(this, LoginActivity::class.java)
            }
            startActivity(i)
            finish()
        }, TIME_OUT.toLong())
    }

}
