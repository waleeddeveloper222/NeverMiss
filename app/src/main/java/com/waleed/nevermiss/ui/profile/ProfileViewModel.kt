package com.waleed.nevermiss.ui.profile

import androidx.lifecycle.ViewModel
import com.waleed.nevermiss.Repo.FireBaseRepo
import com.waleed.nevermiss.model.User
import com.waleed.nevermiss.utils.Validate


class ProfileViewModel : ViewModel {
    //internal var profileFragment: ProfileFragment
    internal var fireBaseRepo: FireBaseRepo

    val userData: User get() = fireBaseRepo.getUserData!!

    constructor() {
        fireBaseRepo = FireBaseRepo(this)
    }

//    constructor(profileFragment: ProfileFragment) {
//        fireBaseRepo = FireBaseRepo(this)
//        this.profileFragment = profileFragment
//    }

//    fun updateUserName(username: String) {
//
//        if (Validate.isNetworkAvailable(profileFragment.getContext())) {
//            fireBaseRepo.updateUsername(username)
//        } else {
//            netWorkError()
//        }
//    }


    fun updateEmail(email: String) {

//        if (Validate.isNetworkAvailable(profileFragment.getContext())) {
//            fireBaseRepo.updateEmail(email)
//        } else {
//            netWorkError()
//        }
    }

    fun updatePassword(password: String) {
//        if (Validate.isNetworkAvailable(profileFragment.getContext())) {
//            fireBaseRepo.updatePassword(password)
//        } else {
//            netWorkError()
//        }
    }

    fun sendMessage(message: String) {
      //  profileFragment.showMessage(message)
    }


    private fun netWorkError() {
       // profileFragment.showMessage("Network Error, Check your Internet Connection")
    }

}