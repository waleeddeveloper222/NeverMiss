package com.waleed.nevermiss.ui.fragment.message

import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.waleed.nevermiss.Repo.Room.DataBaseRepo
import com.waleed.nevermiss.model.MyMessage

class MessageViewModel : ViewModel {


    private var mutableLiveData: MutableLiveData<List<MyMessage>>? = null
    var dataBaseRepo: DataBaseRepo
    lateinit var messageFragment: MessageFragment


    constructor(messageFragment: MessageFragment) {
        mutableLiveData = MutableLiveData<List<MyMessage>>()
        this.messageFragment = messageFragment
        dataBaseRepo = DataBaseRepo(messageFragment.context!!, this)
    }


    fun getUserMessage(): MutableLiveData<List<MyMessage>> {
        dataBaseRepo.getMessages(mutableLiveData!!)
        return mutableLiveData!!
    }

    fun deleteMessage(myMessage: MyMessage) {
        dataBaseRepo.deleteMessage(myMessage)
    }


    fun showMessage(message: String) {
        Toast.makeText(messageFragment.context, message, Toast.LENGTH_SHORT).show()
    }

    fun showError(error: String) {
        Toast.makeText(messageFragment.context, error, Toast.LENGTH_SHORT).show()
    }

}
