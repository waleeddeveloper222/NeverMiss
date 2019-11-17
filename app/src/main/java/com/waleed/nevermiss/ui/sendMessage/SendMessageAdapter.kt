package com.waleed.nevermiss.ui.sendMessage

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.waleed.nevermiss.R
import com.waleed.nevermiss.model.Contact

class SendMessageAdapter :
    RecyclerView.Adapter<SendMessageAdapter.ContactsViewHolder>() {

    internal var contactList: ArrayList<Contact>? = null


    init {
        contactList = ArrayList<Contact>()
    }

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
        holder.textViewName!!.text = contactList!![position].name

        holder.textViewNumber!!.text = contactList!![position].number

        holder.imageViewDelete!!.setOnClickListener {
            contactList!!.remove(contactList!![position])
            notifyDataSetChanged()
        }
    }

    fun setContactList(contactList: ArrayList<Contact>) {
        this.contactList = contactList
        notifyDataSetChanged()
    }

    fun getContactList(): List<Contact> {
        return contactList!!
    }


    inner class ContactsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textViewName: TextView? = itemView.findViewById(R.id.txtName)
        var textViewNumber: TextView? = itemView.findViewById(R.id.txtNumber)
        internal var imageViewDelete: ImageView? = itemView.findViewById(R.id.imgDelete)
    }

}






