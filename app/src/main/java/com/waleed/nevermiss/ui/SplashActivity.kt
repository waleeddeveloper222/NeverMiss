package com.waleed.nevermiss.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.waleed.nevermiss.R
import com.waleed.nevermiss.ui.main.MainActivity
import com.waleed.nevermiss.ui.sign.LoginActivity

class SplashActivity : AppCompatActivity() {

    private val TimeToWait = 2000

    private var firebaseUser: FirebaseUser? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_spalsh)

        firebaseUser = FirebaseAuth.getInstance().currentUser

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
        }, TimeToWait.toLong())
    }

}
