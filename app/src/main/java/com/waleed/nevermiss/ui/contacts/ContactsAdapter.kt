package com.waleed.nevermiss.ui.contacts

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.waleed.nevermiss.R
import com.waleed.nevermiss.model.Contact

class ContactsAdapter : RecyclerView.Adapter<ContactsAdapter.ContactsViewHolder>() {

    private var contactList = ArrayList<Contact>()
    private var selectedContactList = ArrayList<Contact>()


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ContactsViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.contact_row, parent, false)
        return ContactsViewHolder(view)
    }

    override fun getItemCount(): Int {
        return if (contactList != null) contactList!!.size else 0
    }


    override fun onBindViewHolder(holder: ContactsViewHolder, position: Int) {
        holder.contactName!!.text = contactList!![position].name
        holder.contactNumber!!.text = contactList!![position].number
        holder.contactCheckBox.isChecked = contactList!![position].isSelected


    }

    fun setContactList(contactList: List<Contact>) {
        this.contactList = contactList as ArrayList<Contact>
        notifyDataSetChanged()
    }

    fun getSelectedContacts(): List<Contact> {
        return selectedContactList!!
    }


    inner class ContactsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var contactName = itemView.findViewById<TextView>(R.id.contactName)
        var contactNumber = itemView.findViewById<TextView>(R.id.contactNumber)
        var contactCheckBox = itemView.findViewById<CheckBox>(R.id.contactCheckBox)

        init {
            itemView.setOnClickListener {
                if (contactCheckBox!!.isChecked) {
                    contactList!![adapterPosition].isSelected = false
                    selectedContactList.remove(contactList!![adapterPosition])
                } else {
                    contactList!![adapterPosition].isSelected = true
                    selectedContactList.add(contactList!![adapterPosition])
                }
                notifyDataSetChanged()
            }
        }
    }

}