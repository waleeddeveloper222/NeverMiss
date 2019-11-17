package com.waleed.nevermiss.ui.main

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.waleed.nevermiss.R
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat


/**
 * A placeholder fragment containing a simple view.
 */
class PlaceholderFragment : Fragment() {

    private lateinit var pageViewModel: PageViewModel

    private val MY_PERMISSIONS_REQUEST = 2222
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pageViewModel = ViewModelProviders.of(this).get(PageViewModel::class.java).apply {
            setIndex(arguments?.getInt(ARG_SECTION_NUMBER) ?: 1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_main, container, false)
        val textView: TextView = root.findViewById(R.id.section_label)
        pageViewModel.text.observe(this, Observer<String> { textView.text = it })

        checkPermission()

        return root
    }

    private fun checkPermission() {

        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(activity!!, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED
            &&
            ContextCompat.checkSelfPermission(
                activity!!,
                Manifest.permission.SEND_SMS
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            ActivityCompat.requestPermissions(
                activity!!,
                arrayOf(Manifest.permission.READ_CONTACTS, Manifest.permission.SEND_SMS),
                MY_PERMISSIONS_REQUEST
            )


        } else {
            // Permission has already been granted
            getContactList()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            MY_PERMISSIONS_REQUEST -> {
                // If request is cancelled, the result arrays are empty.
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    && (grantResults.isNotEmpty() && grantResults[1] == PackageManager.PERMISSION_GRANTED)
                ) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                    getContactList()

                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    ActivityCompat.requestPermissions(
                        activity!!,
                        arrayOf(Manifest.permission.READ_CONTACTS, Manifest.permission.SEND_SMS),
                        MY_PERMISSIONS_REQUEST
                    )
                }
                return
            }

        }
    }

    private fun getContactList() {

        val cr = context!!.contentResolver

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
                        Log.d("getContactList", "Name: $name")
                        Log.d("getContactList", "Phone Number: $phoneNo")

                    }
                    pCur.close()
                }
            }
        }
        cur?.close()
    }

    companion object {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private const val ARG_SECTION_NUMBER = "section_number"

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        @JvmStatic
        fun newInstance(sectionNumber: Int): PlaceholderFragment {
            return PlaceholderFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, sectionNumber)
                }
            }
        }
    }
}