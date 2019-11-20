package com.waleed.nevermiss.ui.fragment.history

import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.waleed.nevermiss.Repo.Room.DataBaseRepo
import com.waleed.nevermiss.model.MyMessage

class HistoryViewModel : ViewModel {


    private var mutableLiveData: MutableLiveData<List<MyMessage>>? = null
    var dataBaseRepo: DataBaseRepo
    lateinit var historyFragment: HistoryFragment


    constructor(historyFragment: HistoryFragment) {
        mutableLiveData = MutableLiveData<List<MyMessage>>()
        this.historyFragment = historyFragment
        dataBaseRepo = DataBaseRepo(historyFragment.context!!, this)
    }


    fun getUserMessage(): MutableLiveData<List<MyMessage>> {
        dataBaseRepo.getAllMessages(mutableLiveData!!)
        return mutableLiveData!!
    }

    fun deleteMessage(myMessage: MyMessage) {
        dataBaseRepo.deleteMessage(myMessage)
    }


    fun showMessage(message: String) {
        Toast.makeText(historyFragment.context, message, Toast.LENGTH_SHORT).show()
    }

    fun showError(error: String) {
        Toast.makeText(historyFragment.context, error, Toast.LENGTH_SHORT).show()
    }

}