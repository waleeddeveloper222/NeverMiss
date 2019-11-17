package com.waleed.nevermiss.ui.addGroup

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.waleed.nevermiss.utils.Validate

import kotlinx.android.synthetic.main.activity_add_group.*
import kotlinx.android.synthetic.main.content_add_group.*
import androidx.recyclerview.widget.GridLayoutManager
import com.waleed.nevermiss.R
import com.waleed.nevermiss.model.Contact
import com.waleed.nevermiss.model.Groups
import com.waleed.nevermiss.ui.contacts.ContactsActivity
import com.waleed.nevermiss.ui.fragment.group.GroupViewModel
import com.waleed.nevermiss.utils.Utils


class AddGroupActivity : AppCompatActivity() {

    private val INTENT_REQUEST = 5555
    private var cList = ArrayList<Contact>()
    lateinit var addGroupAdapter: AddGroupAdapter
    lateinit var groupViewModel: GroupViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_group)
        setSupportActionBar(toolbar)

        groupViewModel =
            ViewModelProviders.of(this, GroupViewModelFactory(this)).get(GroupViewModel::class.java)

        val gridLayoutManager = GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false)
        peopleRecyclerView.layoutManager = gridLayoutManager
        addGroupAdapter = AddGroupAdapter()
        peopleRecyclerView.adapter = addGroupAdapter


        textViewPeople.setOnClickListener {

            var getContactsIntent = Intent(this, ContactsActivity::class.java)
            startActivityForResult(getContactsIntent, INTENT_REQUEST)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == INTENT_REQUEST) {
            if (resultCode == RESULT_OK) {

                cList = data!!.extras!!.getParcelableArrayList("contactList")!!
                addGroupAdapter.setContactList(cList)
                peopleRecyclerView.visibility = View.VISIBLE
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_save, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_done -> {

                if (isName() && cList.isNotEmpty()) {
                    // add group && finish
                    var grouping =
                        Groups(
                            Utils.getCurrentUser(),
                            textInputLayoutGroupName.editText!!.text.toString(),
                            cList
                        )
                    groupViewModel.addGroup(grouping)

                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun isName(): Boolean {

        return if (Validate.isTextNotEmpty(textInputLayoutGroupName.editText!!.text.toString())) {
            textInputLayoutGroupName.isErrorEnabled = false
            textInputLayoutGroupName.error = ""
            true
        } else {
            textInputLayoutGroupName.isErrorEnabled = true
            textInputLayoutGroupName.error = "Enter Group Name"
            false
        }
    }


    inner class GroupViewModelFactory(private val addGroupActivity: AddGroupActivity) :
        ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return GroupViewModel(addGroupActivity) as T
        }
    }

}
