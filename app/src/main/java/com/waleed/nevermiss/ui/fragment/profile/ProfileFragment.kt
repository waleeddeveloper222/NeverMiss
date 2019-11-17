package com.waleed.nevermiss.ui.fragment.profile

import android.app.Dialog
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.textfield.TextInputLayout

import com.waleed.nevermiss.R
import com.waleed.nevermiss.model.User
import com.waleed.nevermiss.utils.Validate
import kotlinx.android.synthetic.main.profile_fragment.*

class ProfileFragment : Fragment() {


    private lateinit var profileViewModel: ProfileViewModel
    lateinit var user: User

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.profile_fragment, container, false)

        profileViewModel = ViewModelProviders.of(this, ProfileViewModelFactory(this)).get(
            ProfileViewModel::class.java
        )

        var editTextDisplayName = view.findViewById<EditText>(R.id.editTextDisplayName)
        var editTextEmail = view.findViewById<EditText>(R.id.editTextEmail)

        if (profileViewModel.userData != null) {
            user = profileViewModel.userData
            editTextDisplayName.setText(user.name)
            editTextEmail.setText(user.email)
        }


        return view
    }


//    private fun showPasswordDialog() {
//
//        val dialog = Dialog(activity!!)
//        dialog.setContentView(R.layout.password_layout)
//
//        val textInputLayout_password =
//            dialog.findViewById<TextInputLayout>(R.id.textInputLayout_password)
//        val textInputLayout_re_password =
//            dialog.findViewById<TextInputLayout>(R.id.textInputLayout_re_password)
//
//        val savePasswordButton = dialog.findViewById<Button>(R.id.savePasswordButton)
//
//        val cancelButton = dialog.findViewById<Button>(R.id.cancelButton)
//        cancelButton.setOnClickListener { dialog.dismiss() }
//
//        dialog.show()
//    }
//
//    private fun checkPassword(): Boolean {
//        return true
//    }
//
//    private fun updateUserData() {
//
//        if (Validate.isTextNotEmpty(editTextDisplayName.text.toString())) {
//            removerError()
//
//            if (Validate.isTextNotEmpty(editTextEmail.text.toString())) {
//                removerError()
//
//                if (Validate.isValidEmail(editTextEmail.text.toString())) {
//                    removerError()
//
//                    // compare username to previous
//                    if (!Validate.areEqual(
//                            editTextDisplayName.text.toString(),
//                            user.name
//                        )
//                    ) {
//                        // showProgress();
//                        profileViewModel.updateUserName(editTextDisplayName.text.toString())
//                    }
//
//                    // compare email to previous
//                    if (!Validate.areEqual(editTextEmail.text.toString(), user.email)) {
//                        // showProgress();
//                        profileViewModel.updateEmail(editTextEmail.text.toString())
//                    }
//
//                } else {
//                    editTextEmail.error = "invalid Email"
//                }
//            } else {
//                editTextEmail.error = "Email can't be Empty !"
//            }
//        } else {
//            editTextDisplayName.error = "Username can't be Empty !"
//        }
//    }
//
//    private fun removerError() {
//        editTextDisplayName.error = null
//        editTextEmail.error = null
//    }
//
////    private void showProgress() {
////        profileProgressBar.setVisibility(View.VISIBLE);
////        saveUserData.setVisibility(View.GONE);
////    }
////
////    private void hideProgress() {
////        profileProgressBar.setVisibility(View.GONE);
////        saveUserData.setVisibility(View.VISIBLE);
////    }


    fun showMessage(message: String) {
        //hideProgress();
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()

    }


    internal inner class ProfileViewModelFactory(private val profileFragment: ProfileFragment) :
        ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return ProfileViewModel(profileFragment) as T
        }
    }

}
