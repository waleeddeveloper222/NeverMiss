package com.waleed.nevermiss.ui.sendMessage

import android.Manifest
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.waleed.nevermiss.R
import com.waleed.nevermiss.model.Contact
import com.waleed.nevermiss.model.MyMessage
import com.waleed.nevermiss.ui.contacts.ContactsActivity
import com.waleed.nevermiss.utils.Utils
import com.waleed.nevermiss.utils.Validate

import kotlinx.android.synthetic.main.activity_send_message.*
import kotlinx.android.synthetic.main.content_send_message.*
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class SendMessageActivity : AppCompatActivity() {

    private val MY_PERMISSIONS_REQUEST = 2222

    private var dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)
    private var timeFormat = SimpleDateFormat("HH:mm")

    private lateinit var currentDate: Date
    private lateinit var selectedDate: Date
    lateinit var currentTime: Date
    lateinit var selectedTime: Date

    lateinit var sendMessageAdapter: SendMessageAdapter
    private var cList = ArrayList<Contact>()

    private val INTENT_REQUEST = 5544
    lateinit var myMessage: MyMessage

    lateinit var sendMessageViewModel: SendMessageViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_send_message)
        setSupportActionBar(toolbar)

        checkPermission()

        sendMessageViewModel =
            ViewModelProviders.of(this, SendViewModelFactory(this))
                .get(SendMessageViewModel::class.java!!)

        myMessage = MyMessage()
        val gridLayoutManager = GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false)
        contactsRecyclerView.layoutManager = gridLayoutManager
        sendMessageAdapter = SendMessageAdapter()
        contactsRecyclerView.adapter = sendMessageAdapter


        texViewContacts.setOnClickListener {
            var getContactsIntent = Intent(this, ContactsActivity::class.java)
            startActivityForResult(getContactsIntent, INTENT_REQUEST)
        }

        textViewDate.setOnClickListener { messageDate() }
        textViewTime.setOnClickListener { messageTime() }


        sendMessageButton.setOnClickListener {

            if (isContacts() && isDate() && isTime() && isMessage()) {
                myMessage.userID = Utils.getCurrentUser()
                myMessage.smsStatus = "pending"
                myMessage.smsType = "sms"
                sendMessageViewModel.saveMessage(myMessage)
            }


            //Utils.sendTextMsgOnWhatsApp("01016968592", "Hi Hossam", this)

        }

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == INTENT_REQUEST) {
            if (resultCode == RESULT_OK) {

                cList = data!!.extras!!.getParcelableArrayList("contactList")!!
                sendMessageAdapter.setContactList(cList)
                contactsRecyclerView.visibility = View.VISIBLE
            }
        }
    }

    private fun checkPermission() {

        if (
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_CONTACTS
            ) != PackageManager.PERMISSION_GRANTED
            &&
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.SEND_SMS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.READ_CONTACTS, Manifest.permission.SEND_SMS),
                MY_PERMISSIONS_REQUEST
            )


        } else {
            getContactList()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
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
                        this,
                        arrayOf(Manifest.permission.READ_CONTACTS, Manifest.permission.SEND_SMS),
                        MY_PERMISSIONS_REQUEST
                    )
                }
                return
            }

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
                        Log.d("getContactList", "Name: $name")
                        Log.d("getContactList", "Phone Number: $phoneNo")
                    }
                    pCur.close()
                }
            }
        }
        cur?.close()
    }

    private fun messageDate() {

        val c = Calendar.getInstance()
        val mYear = c.get(Calendar.YEAR)
        val mMonth = c.get(Calendar.MONTH)
        val mDay = c.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this,
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                textViewDate.text = dayOfMonth.toString() + "/" + (monthOfYear + 1) + "/" + year
            }, mYear, mMonth, mDay
        )
        datePickerDialog.datePicker.minDate = System.currentTimeMillis()
        datePickerDialog.show()
    }

    private fun messageTime() {

        val c = Calendar.getInstance()
        val mHour = c.get(Calendar.HOUR_OF_DAY)
        val mMinute = c.get(Calendar.MINUTE)

        val timePickerDialog = TimePickerDialog(
            this,
            TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                textViewTime.text = " $hourOfDay:$minute"
            }, mHour, mMinute, false
        )

        timePickerDialog.show()
    }


    //-------- validation--------

    private fun isContacts(): Boolean {

        return if (cList.isEmpty()) {
            texViewContacts.error = "Select People"
            false
        } else {
            texViewContacts.error = null
            myMessage.contacts = cList
            true
        }
    }

    private fun isMessage(): Boolean {

        return if (Validate.isTextNotEmpty(textInputLayout_message.editText!!.text.toString())) {
            textInputLayout_message.isErrorEnabled = false
            textInputLayout_message.error = ""
            myMessage.smsMsg = textInputLayout_message.editText!!.text.toString()

            true
        } else {
            textInputLayout_message.isErrorEnabled = true
            textInputLayout_message.error = "Enter your MyMessage"
            false
        }
    }

    private fun isDate(): Boolean {
        if (Validate.isTextNotEmpty(textViewDate.text.toString())) {

            try {

                val todayDate = dateFormat.format(Calendar.getInstance().time)

                currentDate = dateFormat.parse(todayDate)

                selectedDate = dateFormat.parse(textViewDate.text.toString())

                if (selectedDate.before(currentDate)) {

                    textViewDate.error = "please select a valid date"
                    return false

                } else {
                    textViewDate.error = null
                    myMessage.smsDate = textViewDate.text.toString()
                    return true
                }
            } catch (e: ParseException) {
                e.printStackTrace()
                textViewDate.error = "please select a valid date"
                return false
            }

        } else {
            textViewDate.error = "please select the trip date"
            return false
        }

    }

    private fun isTime(): Boolean {

        if (Validate.isTextNotEmpty(textViewTime.text.toString())) {

            try {
                val todayTime = timeFormat.format(Calendar.getInstance().time)
                currentTime = timeFormat.parse(todayTime)

                selectedTime = timeFormat.parse(textViewTime.text.toString())

                if (selectedDate.compareTo(currentDate) == 0) {

                    if (selectedTime.before(currentTime)) {

                        textViewTime.error = "please select a valid time"
                        return false
                    } else {
                        myMessage.smsTime = textViewTime.text.toString()
                        textViewTime.error = null
                        return true
                    }
                } else {
                    myMessage.smsTime = textViewTime.text.toString()
                    textViewTime.error = null
                    return true

                }


            } catch (e: ParseException) {
                e.printStackTrace()
                textViewTime.error = "please select the trip time"
                return false
            }

        } else {
            textViewTime.error = "please select the trip time"
            return false
        }

    }

    fun showMessage(s: String) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show()
        finish()
    }


    inner class SendViewModelFactory(private val sendMessageActivity: SendMessageActivity) :
        ViewModelProvider.Factory {


        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return SendMessageViewModel(sendMessageActivity) as T
        }
    }

}
