package com.waleed.nevermiss.utils

import android.content.Context
import android.net.ConnectivityManager
import java.util.regex.Pattern


object Validate {

    fun isTextNotEmpty(txt: String): Boolean {
        return !txt.matches("".toRegex())
    }

    fun isValidEmail(email: String): Boolean {
        val expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$"
        val pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE)
        val matcher = pattern.matcher(email)
        return matcher.matches()

    }

    fun areEqual(s1: String, s2: String): Boolean {
        return s1 == s2
    }

    fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }

}