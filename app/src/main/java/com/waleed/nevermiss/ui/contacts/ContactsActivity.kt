package com.waleed.nevermiss.ui.contacts

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.provider.ContactsContract
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import com.waleed.nevermiss.R
import com.waleed.nevermiss.model.Contact
import kotlinx.android.synthetic.main.activity_contacts.*


class ContactsActivity : AppCompatActivity() {

    lateinit var contactSet: ArrayList<Contact>
    lateinit var setTemp: HashSet<Contact>
    lateinit var contactsAdapter: ContactsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contacts)
        setSupportActionBar(toolbar)


        contactSet = ArrayList()
        setTemp = HashSet<Contact>()
        contactsAdapter = ContactsAdapter()

        val layoutManager = LinearLayoutManager(this)
        contactRecyclerView.layoutManager = layoutManager

        contactRecyclerView.adapter = contactsAdapter

        getContactList()

    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_save, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_done -> {

                if (contactsAdapter.getSelectedContacts().isNotEmpty()) {

                    intent.putParcelableArrayListExtra(
                        "contactList",
                        contactsAdapter.getSelectedContacts() as ArrayList<Parcelable>
                    )

                    setResult(Activity.RESULT_OK, intent)
                } else {
                    setResult(Activity.RESULT_CANCELED, intent)

                }
                finish()
                // contactsAdapter.getSelectedContacts()
                Log.d("selectedData", "selected data = " + contactsAdapter.getSelectedContacts())
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }


    private fun getContactList() {

        val cr = contentResolver

        val cur = cr.query(
            ContactsContract.Contacts.CONTENT_URI, null,
            null, null, null
        )

        if (cur?.count ?: 0 > 0) {
            while (cur != null && cur.moveToNext()) {
                val id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID))
                val name = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))

                if (cur.getInt(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {

                    val pCur = cr.query(
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        null,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                        arrayOf(id),
                        null
                    )

                    while (pCur!!.moveToNext()) {
                        val phoneNo =
                            pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
//                        Log.d("getContactList", "Name: $name")
//                        Log.d("getContactList", "Phone Number: $phoneNo")


                        setTemp.add(Contact(name, phoneNo, false))
                        //  contactSet.add(Contact(name, phoneNo, false))
                    }
                    pCur.close()

                    val contactSet = ArrayList<Contact>()
                    for (subset in setTemp) {
                        contactSet.add(subset)
                    }
                    contactsAdapter.setContactList(contactSet)
                }
            }
        }
        cur?.close()
    }

}
