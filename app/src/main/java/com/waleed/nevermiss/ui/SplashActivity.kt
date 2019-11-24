package com.waleed.nevermiss.ui

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.provider.Settings
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.waleed.nevermiss.R
import com.waleed.nevermiss.ui.main.MainActivity
import com.waleed.nevermiss.ui.service.AutoMsgService
import com.waleed.nevermiss.ui.sign.LoginActivity
import com.waleed.nevermiss.utils.Utils
import android.telephony.TelephonyManager
import android.util.Log
import android.os.Build
import androidx.annotation.RequiresApi


class SplashActivity : AppCompatActivity() {

    private val TimeToWait = 2000

    private var firebaseUser: FirebaseUser? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_spalsh)

    }


    override fun onResume() {
        super.onResume()

        firebaseUser = FirebaseAuth.getInstance().currentUser

        if (!Utils.isAccessibilityOn(this, AutoMsgService::class.java)) {
            val intent = Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)
            startActivity(intent)
        } else {
            checkUser()
        }

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
