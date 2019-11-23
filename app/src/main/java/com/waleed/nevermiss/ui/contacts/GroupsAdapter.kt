package com.waleed.nevermiss.ui.contacts

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.waleed.nevermiss.R
import com.waleed.nevermiss.model.Contact
import com.waleed.nevermiss.model.Groups

class GroupsAdapter : RecyclerView.Adapter<GroupsAdapter.GroupsViewHolder>() {

    private var groupsList = ArrayList<Groups>()
    private var selectedContactList = ArrayList<Contact>()


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): GroupsViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.contact_row, parent, false)
        return GroupsViewHolder(view)
    }

    override fun getItemCount(): Int {
        return if (groupsList != null) groupsList!!.size else 0
    }


    override fun onBindViewHolder(holder: GroupsViewHolder, position: Int) {
        holder.groupName!!.text = groupsList!![position].groupName
        holder.groupCheckBox.isChecked = groupsList!![position].isChecked


    }

    fun setGroupsList(groupsList: List<Groups>) {
        this.groupsList = groupsList as ArrayList<Groups>
        notifyDataSetChanged()
    }

    fun getSelectedContacts(): List<Contact> {
        return selectedContactList!!
    }


    inner class GroupsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var groupName = itemView.findViewById<TextView>(R.id.contactName)
        var groupCheckBox = itemView.findViewById<CheckBox>(R.id.contactCheckBox)

        init {
            itemView.setOnClickListener {
                if (groupCheckBox!!.isChecked) {
                    groupsList!![adapterPosition].isChecked = false
                    selectedContactList.removeAll(groupsList!![adapterPosition].contacts)
                } else {
                    groupsList!![adapterPosition].isChecked = true
                    selectedContactList.addAll(groupsList!![adapterPosition].contacts)
                }
                notifyDataSetChanged()
            }
        }
    }

}