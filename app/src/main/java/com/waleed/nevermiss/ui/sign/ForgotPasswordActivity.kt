package com.waleed.nevermiss.ui.sign

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
import kotlinx.android.synthetic.main.activity_forgot_password.*

class ForgotPasswordActivity : AppCompatActivity() {

    lateinit var signViewModel: SignViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        signViewModel =
            ViewModelProviders.of(this, SignViewModelFactory(this)).get(SignViewModel::class.java!!)

        sendEmailButton.setOnClickListener {
            if (Validate.isTextNotEmpty(textInputLayout_email.editText!!.text.toString())) {

                textInputLayout_email.setErrorEnabled(false)
                textInputLayout_email.setError(null)

                if (Validate.isValidEmail(textInputLayout_email.editText!!.text.toString())) {

                    textInputLayout_email.isErrorEnabled = false
                    textInputLayout_email.error = null

                    sendEmailButton.visibility = View.INVISIBLE
                    progressBar.visibility = View.VISIBLE
                    // send Email
                    signViewModel.sendEmail(textInputLayout_email.editText!!.text.toString())

                } else {
                    textInputLayout_email.isErrorEnabled = true
                    textInputLayout_email.error = "Email is not Valid! "
                }
            } else {
                textInputLayout_email.isErrorEnabled = true
                textInputLayout_email.error = "Email can not be Empty! "
            }
        }

    }


    fun showError(error: String) {
        progressBar.visibility = View.GONE
        sendEmailButton.visibility = View.VISIBLE
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
    }

    fun showMessage() {
        Toast.makeText(this, "Email is sent, Check Your inbox", Toast.LENGTH_SHORT).show()
        finish()

    }

    class SignViewModelFactory(private val forgotPasswordActivity: ForgotPasswordActivity) :
        ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return SignViewModel(forgotPasswordActivity) as T
        }
    }

}
