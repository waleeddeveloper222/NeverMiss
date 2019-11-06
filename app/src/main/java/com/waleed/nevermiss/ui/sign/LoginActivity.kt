package com.waleed.nevermiss.ui.sign

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.waleed.nevermiss.R
import com.waleed.nevermiss.viewModel.SignViewModel

class LoginActivity : AppCompatActivity() {

    private lateinit var signViewModel: SignViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        signViewModel = ViewModelProviders.of(this, SignViewModelFactory(this@LoginActivity)).get(SignViewModel::class.java!!)

    }


    fun showMessage(message: String) {
//        progressBar.setVisibility(View.GONE)
//        loginButton.setVisibility(View.VISIBLE)
        Toast.makeText(this@LoginActivity, message, Toast.LENGTH_SHORT).show()
    }

    fun showNetworkError(netWorkError: String) {
//        progressBar.setVisibility(View.GONE)
//        loginButton.setVisibility(View.VISIBLE)
        Toast.makeText(this, netWorkError, Toast.LENGTH_SHORT).show()

    }

    private fun removerError() {
//        textInputLayout_email.setErrorEnabled(false)
//        textInputLayout_email.setError("")
//
//        textInputLayout_password.setErrorEnabled(false)
//        textInputLayout_password.setError("")

    }

    internal inner class SignViewModelFactory(private val loginActivity: LoginActivity) : ViewModelProvider.Factory {


        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return SignViewModel(loginActivity) as T
        }
    }

}