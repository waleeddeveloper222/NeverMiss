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
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var signViewModel: SignViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        signViewModel =
            ViewModelProviders.of(this, SignViewModelFactory(this)).get(SignViewModel::class.java)

    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.back_to_login -> finish()

            R.id.registerButton ->

                if (Validate.isTextNotEmpty(textInputLayout_userName.editText!!.text.toString())) {

                    removerError()

                    if (Validate.isTextNotEmpty(textInputLayout_email.editText!!.text.toString())) {

                        removerError()

                        if (Validate.isTextNotEmpty(textInputLayout_password.editText!!.text.toString())) {

                            removerError()

                            if (Validate.isTextNotEmpty(textInputLayout_re_password.editText!!.text.toString())) {
                                removerError()

                                if (Validate.areEqual(
                                        textInputLayout_password.editText!!.text.toString(),
                                        textInputLayout_re_password.editText!!.text.toString()
                                    )
                                ) {

                                    removerError()

                                    if (Validate.isValidEmail(textInputLayout_email.editText!!.text.toString())) {

                                        removerError()
                                        progressBar.visibility = View.VISIBLE
                                        registerButton.visibility = View.INVISIBLE
                                        signViewModel.register(
                                            textInputLayout_userName.editText!!.text.toString(),
                                            textInputLayout_email.editText!!.text.toString(),
                                            textInputLayout_password.editText!!.text.toString()
                                        )

                                    } else {
                                        textInputLayout_email.isErrorEnabled = true
                                        textInputLayout_email.error = "Email is not Valid! "
                                    }

                                } else {
                                    textInputLayout_password.isErrorEnabled = true
                                    textInputLayout_password.error = "Error Password"

                                    textInputLayout_re_password.isErrorEnabled = true
                                    textInputLayout_re_password.error = "Error Password"
                                }

                            } else {
                                textInputLayout_re_password.isErrorEnabled = true
                                textInputLayout_re_password.error = "this Field can not be Empty! "
                            }

                        } else {
                            textInputLayout_password.isErrorEnabled = true
                            textInputLayout_password.error = "Password can not be Empty! "
                        }
                    } else {
                        textInputLayout_email.isErrorEnabled = true
                        textInputLayout_email.error = "Email can not be Empty! "
                    }
                } else {
                    textInputLayout_userName.isErrorEnabled = true
                    textInputLayout_userName.error = "UserName can not be Empty! "
                }
        }
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
