package com.waleed.nevermiss.ui.fragment.profile

import androidx.lifecycle.ViewModel
import com.waleed.nevermiss.Repo.FireBaseRepo
import com.waleed.nevermiss.model.User
import com.waleed.nevermiss.utils.Validate


class ProfileViewModel : ViewModel {
    lateinit var profileFragment: ProfileFragment
    var fireBaseRepo: FireBaseRepo

    val userData: User get() = fireBaseRepo.getUserData!!

    constructor() {
        fireBaseRepo = FireBaseRepo(this)
    }

    constructor(profileFragment: ProfileFragment) {
        fireBaseRepo = FireBaseRepo(this)
        this.profileFragment = profileFragment
    }

    fun updateUserName(username: String) {
        if (Validate.isNetworkAvailable(profileFragment.context!!)) {
            fireBaseRepo.updateUsername(username)
        } else {
            netWorkError()
        }
    }


    fun updateEmail(email: String) {
        if (Validate.isNetworkAvailable(profileFragment.context!!)) {
            fireBaseRepo.updateEmail(email)
        } else {
            netWorkError()
        }
    }

    fun updatePassword(password: String) {
        if (Validate.isNetworkAvailable(profileFragment.context!!)) {
            fireBaseRepo.updatePassword(password)
        } else {
            netWorkError()
        }
    }

    fun sendMessage(message: String) {
        profileFragment.showMessage(message)
    }


    private fun netWorkError() {
        profileFragment.showMessage("Network Error, Check your Internet Connection")
    }

}