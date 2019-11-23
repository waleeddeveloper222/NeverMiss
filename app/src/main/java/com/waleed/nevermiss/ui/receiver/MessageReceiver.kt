package com.waleed.nevermiss.ui.receiver

import android.app.Activity
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.telephony.SmsManager
import android.widget.Toast
import com.waleed.nevermiss.Repo.Room.DataBaseRepo
import com.waleed.nevermiss.model.MyMessage
import com.waleed.nevermiss.ui.service.AutoMsgService
import com.waleed.nevermiss.utils.NotificationHelper

class MessageReceiver : BroadcastReceiver() {
    private lateinit var sentPendingIntent: PendingIntent
    private lateinit var deliveredPendingIntent: PendingIntent
    private lateinit var smsSentReceiver: BroadcastReceiver
    private lateinit var smsDeliveredReceiver: BroadcastReceiver

    private lateinit var myMessage: MyMessage

    lateinit var dataBaseRepo: DataBaseRepo

    override fun onReceive(context: Context?, intent: Intent?) {

        dataBaseRepo = DataBaseRepo(context!!)

        sentPendingIntent = PendingIntent.getBroadcast(context, 0, Intent("Message sent"), 0)
        deliveredPendingIntent =
            PendingIntent.getBroadcast(context, 0, Intent("message delivered"), 0)

        myMessage = intent?.extras!!.getParcelable<MyMessage>("messageToAlarm")!!



        if (myMessage.smsType == "sms") {

            for (con in myMessage.contacts) {
                val smsManager: SmsManager = SmsManager.getDefault()
                smsManager.sendTextMessage(
                    con.number,
                    null,
                    myMessage.smsMsg,
                    sentPendingIntent,
                    deliveredPendingIntent
                )
            }
        } else if (myMessage.smsType == "whatsapp") {

            for (con in myMessage.contacts) {
                val intent = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(
                        "https://api.whatsapp.com/send?phone=" + con.number.replace(
                            "+",
                            ""
                        ) + "&text=" + myMessage.smsMsg
                    )
                )
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                context.startActivity(intent)
                context.startService(Intent(context, AutoMsgService::class.java))
            }

            updateMsg("Completed", "Sent")
            showNotification(context, "message sent")
        }




        smsSentReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, arg1: Intent) {
                when (resultCode) {
                    Activity.RESULT_OK -> {
                        Toast.makeText(context, "Message sent", Toast.LENGTH_SHORT).show()
                        updateMsg("Completed", "Sent")
                        showNotification(context, "message sent")
                    }
                    SmsManager.RESULT_ERROR_GENERIC_FAILURE -> {
                        Toast.makeText(context, "GENERIC FAILURE", Toast.LENGTH_SHORT).show()
                        updateMsg("GENERIC FAILURE", "GENERIC FAILURE")
                        showNotification(context, " GENERIC FAILURE")
                    }
                    SmsManager.RESULT_ERROR_NO_SERVICE -> {
                        Toast.makeText(context, "NO SERVICE", Toast.LENGTH_SHORT).show()
                        updateMsg("NO SERVICE", "NO SERVICE")
                        showNotification(context, " NO SERVICE")
                    }
                    SmsManager.RESULT_ERROR_NULL_PDU -> {
                        Toast.makeText(context, "NULL PDU", Toast.LENGTH_SHORT).show()
                        updateMsg("NULL PDU", "NO SERVICE")
                        showNotification(context, " NULL PDU")
                    }
                    SmsManager.RESULT_ERROR_RADIO_OFF -> {
                        Toast.makeText(context, "RADIO OFF", Toast.LENGTH_SHORT).show()
                        updateMsg("RADIO OFF", "RADIO OFF")
                        showNotification(context, " RADIO OFF")
                    }
                }
            }
        }

        smsDeliveredReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, arg1: Intent) {
                when (resultCode) {
                    Activity.RESULT_OK -> {
                        Toast.makeText(context, "message delivered", Toast.LENGTH_SHORT).show()
                        updateMsg("Completed", "Delivered")
                        showNotification(context, "message delivered")
                    }
                    Activity.RESULT_CANCELED -> {
                        Toast.makeText(context, "message not delivered", Toast.LENGTH_SHORT).show()
                        updateMsg("Not", "Not Delivered")
                        showNotification(context, "message not delivered")
                    }
                }
            }
        }

        context?.applicationContext?.registerReceiver(smsSentReceiver, IntentFilter("message sent"))
        context?.applicationContext?.registerReceiver(
            smsDeliveredReceiver,
            IntentFilter("message delivered")
        )

    }

    private fun updateMsg(smsStatus: String, smsDelivered: String) {
        myMessage.smsStatus = smsStatus
        myMessage.smsType = smsDelivered
        dataBaseRepo.updateMessage(myMessage)
    }

    private fun showNotification(context: Context?, msg: String) {
        val notificationHelper = NotificationHelper(
            context!!,
            myMessage,
            msg
        )
        val nb = notificationHelper.getChannelNotification()
        notificationHelper.getManager().notify(myMessage.smsId.toInt(), nb.build())
    }


}