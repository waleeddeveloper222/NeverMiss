package com.waleed.nevermiss.ui.sign

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.waleed.nevermiss.R
import com.waleed.nevermiss.utils.Validate
import com.waleed.nevermiss.viewModel.SignViewModel
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var signViewModel: SignViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        signViewModel = ViewModelProviders.of(this, SignViewModelFactory(this@LoginActivity))
            .get(SignViewModel::class.java!!)

    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.registerTextView -> {
                val registerIntent = Intent(this@LoginActivity, RegisterActivity::class.java)
                startActivity(registerIntent)
            }
            R.id.forgotTextView -> {
                val forgotIntent = Intent(this@LoginActivity, ForgotPasswordActivity::class.java)
                startActivity(forgotIntent)
            }

            R.id.loginButton ->

                //     startActivity(new Intent(this, MainActivity.class));

                if (Validate.isTextNotEmpty(textInputLayout_email.editText!!.text.toString())) {

                    removerError()

                    if (Validate.isTextNotEmpty(textInputLayout_password.editText!!.text.toString())) {

                        removerError()

                        if (Validate.isValidEmail(textInputLayout_email.editText!!.text.toString())) {

                            removerError()

                            progressBar.visibility = View.VISIBLE
                            loginButton.visibility = View.INVISIBLE
                            signViewModel.login(
                                textInputLayout_email.editText!!.text.toString(),
                                textInputLayout_password.editText!!.text.toString()
                            )
                        } else {
                            textInputLayout_email.isErrorEnabled = true
                            textInputLayout_email.error = "Email is not Valid! "
                        }

                    } else {
                        textInputLayout_password.isErrorEnabled = true
                        textInputLayout_password.error = "Password can not be Empty! "
                    }
                } else {
                    textInputLayout_email.isErrorEnabled = true
                    textInputLayout_email.error = "Email can not be Empty! "
                }
        }
    }


    fun showMessage(message: String) {
        progressBar.visibility = View.GONE
        loginButton.visibility = View.VISIBLE
        Toast.makeText(this@LoginActivity, message, Toast.LENGTH_SHORT).show()
    }

    fun showNetworkError(netWorkError: String) {
        progressBar.visibility = View.GONE
        loginButton.visibility = View.VISIBLE
        Toast.makeText(this, netWorkError, Toast.LENGTH_SHORT).show()

    }

    private fun removerError() {
        textInputLayout_email.isErrorEnabled = false
        textInputLayout_email.error = ""

        textInputLayout_password.isErrorEnabled = false
        textInputLayout_password.error = ""

    }

    internal inner class SignViewModelFactory(private val loginActivity: LoginActivity) :
        ViewModelProvider.Factory {


        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return SignViewModel(loginActivity) as T
        }
    }

}