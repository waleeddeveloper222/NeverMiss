package com.waleed.nevermiss.utils

import android.telephony.SmsManager
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlin.math.log
import android.R.attr.phoneNumber
import android.annotation.SuppressLint
import android.telephony.SubscriptionInfo
import android.telephony.SubscriptionManager
import android.app.Activity
import android.os.Build
import android.content.Intent
import android.app.PendingIntent
import androidx.core.content.ContextCompat.startActivity
import android.telephony.PhoneNumberUtils
import android.content.ComponentName
import android.content.Context
import android.provider.ContactsContract
import androidx.room.util.CursorUtil.getColumnIndexOrThrow
import android.net.Uri.withAppendedPath
import android.content.ContentResolver
import android.net.Uri
import androidx.core.content.ContextCompat.startActivity
import android.provider.Settings.Secure
import android.provider.Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES
import android.text.TextUtils
import android.provider.Settings.SettingNotFoundException
import android.provider.Settings.Secure.ACCESSIBILITY_ENABLED
import android.accessibilityservice.AccessibilityService
import android.provider.Settings


object Utils {

//    fun sendSMS(phoneNo: String, msg: String) {
//        try {
//            val smsManager = SmsManager.getDefault()
//            smsManager.sendTextMessage(phoneNo, null, msg, null, null)
//
//            // to send to the second sim
//            //SmsManager.getSmsManagerForSubscriptionId(1).sendTextMessage(phoneNo, null, msg, null, null)
//
//            Log.d("SMSSEND", "sendSMS successfully")
//        } catch (ex: Exception) {
//            ex.printStackTrace()
//            Log.d("SMSSEND", "error ex= $ex")
//
//        }
//    }


    fun getCurrentUser(): String {
        return FirebaseAuth.getInstance().currentUser!!.uid

    }


    fun sendTextMsgOnWhatsApp(sContactNo: String, sMessage: String, context: Context) {
        var toNumber = sContactNo // contains spaces, i.e., example +91 98765 43210
        toNumber = toNumber.replace("+", "").replace(" ", "")

        /*this method contactIdByPhoneNumber() will get unique id for given contact,
        if this return's null then it means that you don't have any contact save with this mobile no.*/
        val sContactId = contactIdByPhoneNumber(toNumber, context)

        if (sContactId != null && sContactId.length > 0) {

            /*
             * Once We get the contact id, we check whether contact has a registered with WhatsApp or not.
             * this hasWhatsApp(hasWhatsApp) method will return null,
             * if contact doesn't associate with whatsApp services.
             * */
            val sWhatsAppNo = hasWhatsApp(sContactId, context)

            if (sWhatsAppNo != null && sWhatsAppNo.length > 0) {
                val sendIntent = Intent("android.intent.action.MAIN")
                sendIntent.putExtra("jid", "$toNumber@s.whatsapp.net")
                sendIntent.putExtra(Intent.EXTRA_TEXT, sMessage)
                sendIntent.action = Intent.ACTION_SEND
                sendIntent.setPackage("com.whatsapp")
                sendIntent.type = "text/plain"
                context.startActivity(sendIntent)
            } else {
                // this contact does not exist in any WhatsApp application
                Log.d("sendWhatapp", "Contact not found in WhatsApp !!")
            }
        } else {
            // this contact does not exist in your contact
            Log.d("sendWhatapp", "create contact for $toNumber")

        }
    }

    private fun contactIdByPhoneNumber(phoneNumber: String?, context: Context): String? {
        var contactId: String? = null
        if (phoneNumber != null && phoneNumber.isNotEmpty()) {
            val contentResolver = context.contentResolver
            val uri = Uri.withAppendedPath(
                ContactsContract.PhoneLookup.CONTENT_FILTER_URI,
                Uri.encode(phoneNumber)
            )
            val projection = arrayOf(ContactsContract.PhoneLookup._ID)

            val cursor = contentResolver.query(uri, projection, null, null, null)

            if (cursor != null) {
                while (cursor!!.moveToNext()) {
                    contactId =
                        cursor!!.getString(cursor!!.getColumnIndexOrThrow(ContactsContract.PhoneLookup._ID))
                }
                cursor!!.close()
            }
        }
        return contactId
    }

    fun hasWhatsApp(contactID: String, context: Context): String? {
        var rowContactId: String? = null
        val hasWhatsApp: Boolean

        val projection = arrayOf(ContactsContract.RawContacts._ID)
        val selection =
            ContactsContract.RawContacts.CONTACT_ID + " = ? AND " + ContactsContract.RawContacts.ACCOUNT_TYPE + " = ?"
        val selectionArgs = arrayOf(contactID, "com.whatsapp")
        val cursor = context.getContentResolver().query(
            ContactsContract.RawContacts.CONTENT_URI,
            projection,
            selection,
            selectionArgs,
            null
        )
        if (cursor != null) {
            hasWhatsApp = cursor!!.moveToNext()
            if (hasWhatsApp) {
                rowContactId = cursor!!.getString(0)
            }
            cursor!!.close()
        }
        return rowContactId
    }


    fun isAccessibilityOn(
        context: Context,
        clazz: Class<out AccessibilityService>
    ): Boolean {
        var accessibilityEnabled = 0
        val service = context.packageName + "/" + clazz.canonicalName
        try {
            accessibilityEnabled = Secure.getInt(
                context.applicationContext.contentResolver,
                ACCESSIBILITY_ENABLED
            )
        } catch (ignored: SettingNotFoundException) {
        }

        val colonSplitter = TextUtils.SimpleStringSplitter(':')

        if (accessibilityEnabled == 1) {
            val settingValue = Secure.getString(
                context.applicationContext.contentResolver,
                ENABLED_ACCESSIBILITY_SERVICES
            )
            if (settingValue != null) {
                colonSplitter.setString(settingValue)
                while (colonSplitter.hasNext()) {
                    val accessibilityService = colonSplitter.next()

                    if (accessibilityService.equals(service, ignoreCase = true)) {
                        return true
                    }
                }
            }
        }

        return false
    }
}