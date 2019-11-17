package com.waleed.nevermiss.ui.fragment.group

import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.waleed.nevermiss.Repo.Room.DataBaseRepo
import com.waleed.nevermiss.model.Groups
import com.waleed.nevermiss.ui.addGroup.AddGroupActivity

class GroupViewModel : ViewModel {

    private var mutableLiveData: MutableLiveData<List<Groups>>? = null
    var dataBaseRepo: DataBaseRepo
    lateinit var groupFragment: GroupFragment
    lateinit var addGroupActivity: AddGroupActivity


    constructor(addGroupActivity: AddGroupActivity) {
        mutableLiveData = MutableLiveData<List<Groups>>()
        this.addGroupActivity = addGroupActivity
        dataBaseRepo = DataBaseRepo(addGroupActivity, this)
    }

    constructor(groupFragment: GroupFragment) {
        mutableLiveData = MutableLiveData<List<Groups>>()
        this.groupFragment = groupFragment
        dataBaseRepo = DataBaseRepo(groupFragment.context!!, this)
    }

    fun addGroup(group: Groups) {
        dataBaseRepo.insertGroup(group)
    }

    fun updateGroup(group: Groups) {
        dataBaseRepo.updateGroup(group)
    }

    fun deleteGroup(group: Groups) {
        dataBaseRepo.deleteGroup(group)
    }

    fun getUserGroups(currentUser: String): MutableLiveData<List<Groups>> {
        dataBaseRepo.getUserGroups(mutableLiveData!!, currentUser)
        return mutableLiveData!!
    }


    fun showMessage(message: String) {
        Toast.makeText(addGroupActivity, message, Toast.LENGTH_SHORT).show()
        addGroupActivity.finish()
    }

    fun showError(error: String) {
        Toast.makeText(addGroupActivity, error, Toast.LENGTH_SHORT).show()
    }

}
