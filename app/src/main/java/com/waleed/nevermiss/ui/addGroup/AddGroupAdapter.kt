package com.waleed.nevermiss.ui.addGroup

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.waleed.nevermiss.R
import com.waleed.nevermiss.model.Contact


class AddGroupAdapter : RecyclerView.Adapter<AddGroupAdapter.ContactsViewHolder>() {

    private var contactList = ArrayList<Contact>()


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ContactsViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.contacts_row, parent, false)
        return ContactsViewHolder(view)
    }

    override fun getItemCount(): Int {
        return if (contactList != null) contactList!!.size else 0
    }


    override fun onBindViewHolder(holder: ContactsViewHolder, position: Int) {
        holder.txtName!!.text = contactList!![position].name
        holder.txtNumber!!.text = contactList!![position].number

    }

    fun setContactList(contactList: ArrayList<Contact>) {
        this.contactList = contactList
        notifyDataSetChanged()
    }

    fun getSelectedContacts(): List<Contact> {
        return contactList!!
    }


    inner class ContactsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var txtName = itemView.findViewById<TextView>(R.id.txtName)
        var txtNumber = itemView.findViewById<TextView>(R.id.txtNumber)
        var imgDelete = itemView.findViewById<ImageView>(R.id.imgDelete)

        init {
            imgDelete.setOnClickListener {
                contactList.remove(contactList!![adapterPosition])
                notifyDataSetChanged()
            }
        }
    }

}