package com.waleed.nevermiss.ui.sign

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.waleed.nevermiss.R
import com.waleed.nevermiss.viewModel.SignViewModel
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {

    lateinit var signViewModel: SignViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        signViewModel =
            ViewModelProviders.of(this, SignViewModelFactory(this)).get(SignViewModel::class.java)

    }

    private fun removerError() {
        textInputLayout_userName.isErrorEnabled = false
        textInputLayout_userName.error = ""

        textInputLayout_email.isErrorEnabled = false
        textInputLayout_email.error = ""

        textInputLayout_password.isErrorEnabled = false
        textInputLayout_password.error = ""

        textInputLayout_re_password.isErrorEnabled = false
        textInputLayout_re_password.error = ""
    }

    fun showMessage(message: String) {
        progressBar.visibility = View.GONE
        registerButton.visibility = View.VISIBLE
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    fun showNetworkError(netWorkError: String) {
        progressBar.visibility = View.GONE
        registerButton.visibility = View.VISIBLE
        Toast.makeText(this, netWorkError, Toast.LENGTH_SHORT).show()

    }

    inner class SignViewModelFactory(private val registerActivity: RegisterActivity) :
        ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return SignViewModel(registerActivity) as T
        }
    }
}
