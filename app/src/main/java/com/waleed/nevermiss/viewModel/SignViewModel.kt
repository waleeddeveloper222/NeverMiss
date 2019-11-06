package com.waleed.nevermiss.viewModel

import android.content.Intent
import android.util.Log
import androidx.lifecycle.ViewModel
import com.waleed.nevermiss.Repo.FireBaseRepo
import com.waleed.nevermiss.ui.MainActivity
import com.waleed.nevermiss.ui.sign.ForgotPasswordActivity
import com.waleed.nevermiss.ui.sign.LoginActivity
import com.waleed.nevermiss.ui.sign.RegisterActivity
import com.waleed.nevermiss.utils.Validate


class SignViewModel : ViewModel {

    internal var fireBaseRepo: FireBaseRepo
    internal var loginActivity: LoginActivity? = null
    internal var registerActivity: RegisterActivity? = null
    internal var passwordActivity: ForgotPasswordActivity? = null
//    internal var logoutFragment: LogoutFragment? = null


    constructor(loginActivity: LoginActivity) {
        fireBaseRepo = FireBaseRepo(this)
        this.loginActivity = loginActivity
    }

    constructor(registerActivity: RegisterActivity) {
        fireBaseRepo = FireBaseRepo(this)
        this.registerActivity = registerActivity
    }

    constructor(passwordActivity: ForgotPasswordActivity) {
        fireBaseRepo = FireBaseRepo(this)
        this.passwordActivity = passwordActivity
    }
//
//    constructor(logoutFragment: LogoutFragment) {
//        fireBaseRepo = FireBaseRepo(this)
//        this.logoutFragment = logoutFragment
//    }


    fun login(email: String, password: String) {
        if (Validate.isNetworkAvailable(loginActivity!!)) {
            fireBaseRepo.login(email, password)
        } else {
            networkError()
        }

    }

    fun register(userName: String, email: String, password: String) {

        if (Validate.isNetworkAvailable(registerActivity!!)) {
            fireBaseRepo.register(userName, email, password)
        } else {
            networkError()
        }

    }

    fun sendEmail(email: String) {

        if (Validate.isNetworkAvailable(passwordActivity!!)) {
            fireBaseRepo.sendEmail(email)
        } else {
            networkError()
        }

    }

    fun signOut() {
        fireBaseRepo.signOut()
    }

    fun sendMessage() {

        if (loginActivity != null) {

            loginActivity!!.showMessage("Login successfully")
            loginActivity!!.startActivity(Intent(loginActivity, MainActivity::class.java))
            loginActivity!!.finish()

        } else if (registerActivity != null) {
            registerActivity!!.showMessage("Register successfully")
            registerActivity!!.startActivity(Intent(registerActivity, MainActivity::class.java))
            registerActivity!!.finish()

        } else if (passwordActivity != null) {
            passwordActivity!!.showMessage()
        }
//        else if (logoutFragment != null) {
//            logoutFragment!!.startActivity(
//                Intent(
//                    logoutFragment!!.getActivity(),
//                    SplashActivity::class.java
//                )
//            )
//            logoutFragment!!.getActivity().finish()
//        }

    }

    fun sendError(error: String) {

        Log.d("sendError", "sendError: error = $error")
        if (loginActivity != null) {
            loginActivity!!.showMessage("Login Failed , please try again")
        } else if (registerActivity != null) {
            registerActivity!!.showMessage("register Failed , please try again")
        } else if (passwordActivity != null) {
            passwordActivity!!.showError("Failed to send the Email , please try again")
        }
//        else if (logoutFragment != null) {
//            logoutFragment!!.showError("No user available")
//        }

    }

    fun networkError() {

        if (loginActivity != null) {
            loginActivity!!.showNetworkError("Network Error, Check your Internet Connection")
        } else if (registerActivity != null) {
            registerActivity!!.showNetworkError("Network Error, Check your Internet Connection")
        }
//        else if (passwordActivity != null) {
//            passwordActivity!!.showError("Network Error, Check your Internet Connection")
//        }

    }


}
