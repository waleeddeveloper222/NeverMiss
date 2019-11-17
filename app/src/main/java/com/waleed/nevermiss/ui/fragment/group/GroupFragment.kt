package com.waleed.nevermiss.ui.fragment.group

import android.content.Intent
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

import com.waleed.nevermiss.R
import com.waleed.nevermiss.model.Groups
import com.waleed.nevermiss.ui.addGroup.AddGroupActivity
import com.waleed.nevermiss.utils.Utils
import kotlinx.android.synthetic.main.group_fragment.*

class GroupFragment : Fragment() {


    lateinit var groupAdapter: GroupAdapter
    lateinit var layoutManager: RecyclerView.LayoutManager
    lateinit var groupViewModel: GroupViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var view = inflater.inflate(R.layout.group_fragment, container, false)

        groupViewModel =
            ViewModelProviders.of(this, GroupViewModelFactory(this)).get(GroupViewModel::class.java)

        var groupRecyclerView = view.findViewById<RecyclerView>(R.id.groupRecyclerView)

        layoutManager = LinearLayoutManager(activity)
        groupAdapter = GroupAdapter(this)
        groupRecyclerView.layoutManager = layoutManager
        groupRecyclerView.adapter = groupAdapter

        val addGroupFab = view.findViewById<FloatingActionButton>(R.id.addGroupFab)
        addGroupFab.setOnClickListener {
            startActivity(
                Intent(
                    activity,
                    AddGroupActivity::class.java
                )
            )
        }

        return view
    }


    override fun onResume() {
        super.onResume()
        updateRecyclerView()
    }

    fun updateRecyclerView() {

        groupViewModel.getUserGroups(Utils.getCurrentUser()).observe(this,
            Observer<List<Groups>> { groups ->
                groupAdapter.setGroupList(groups)
            })
    }

    fun deleteGroup(groups: Groups) {
        groupViewModel.deleteGroup(groups)

    }

    inner class GroupViewModelFactory(private val groupFragment: GroupFragment) :
        ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return GroupViewModel(groupFragment) as T
        }
    }

}
