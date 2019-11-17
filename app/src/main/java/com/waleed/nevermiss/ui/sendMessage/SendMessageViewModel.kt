package com.waleed.nevermiss.ui.sendMessage

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModel
import com.waleed.nevermiss.Repo.Room.DataBaseRepo
import com.waleed.nevermiss.model.MyMessage
import com.waleed.nevermiss.ui.receiver.MessageReceiver
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class SendMessageViewModel : ViewModel {

    lateinit var sendMessageActivity: SendMessageActivity
    lateinit var messageReceiver: MessageReceiver
    lateinit var dataBaseRepo: DataBaseRepo

    lateinit var context: Context

    constructor(sendMessageActivity: SendMessageActivity) {
        this.sendMessageActivity = sendMessageActivity
        dataBaseRepo = DataBaseRepo(sendMessageActivity, this)

    }

    constructor(messageReceiver: MessageReceiver) {
        this.context = context
        this.messageReceiver = messageReceiver
        dataBaseRepo = DataBaseRepo(context, this)

    }

    fun saveMessage(myMessage: MyMessage) {
        dataBaseRepo.setMessage(myMessage)
    }


    fun updateMessage(myMessage: MyMessage) {
        dataBaseRepo.updateMessage(myMessage)
    }

    fun updateMessageState(id: String, state: String) {
        dataBaseRepo.updateMessageState(id, state)
    }


    fun showResult(s: String) {
        if (sendMessageActivity != null) {
            sendMessageActivity!!.showMessage(s)
        }

    }


    fun setAlarm(myMessage: MyMessage) {

        val cal = Calendar.getInstance()
        var date: Date? = null

        val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.ENGLISH)
        try {
            date = sdf.parse(myMessage.smsDate + " " + myMessage.smsTime)
            cal.time = date!!
            cal.set(Calendar.SECOND, 0)

        } catch (e: ParseException) {
            e.printStackTrace()
            Log.d("setAlarm", "exception= $e")
        }

        val alarmManager =
            sendMessageActivity!!.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val alarmIntent = Intent(sendMessageActivity, MessageReceiver::class.java)

        val bundle = Bundle()
        bundle.putParcelable("messageToAlarm", myMessage)
        alarmIntent.putExtras(bundle)

        val pendingIntent =
            PendingIntent.getBroadcast(sendMessageActivity, myMessage.smsId.toInt(), alarmIntent, 0)

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, cal.timeInMillis, pendingIntent)

        // alarmManager.setRepeating(AlarmManager.RTC_WAKEUP ,alarmTime ,AlarmManager.INTERVAL_DAY ,pendingIntent);

    }

    fun updateAlarm(myMessage: MyMessage) {

        if (sendMessageActivity != null) {
            updatePreviousAlarm(myMessage)
        }
    }

    fun updatePreviousAlarm(myMessage: MyMessage) {

        val cal = Calendar.getInstance()
        var date: Date? = null

        val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.ENGLISH)
        try {
            date = sdf.parse(myMessage.smsDate + " " + myMessage.smsTime)
            cal.time = date!!
            cal.set(Calendar.SECOND, 0)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        val alarmManager =
            sendMessageActivity!!.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val alarmIntent = Intent(sendMessageActivity, MessageReceiver::class.java)
        alarmIntent.putExtra("amMessage", myMessage)

        val pendingIntent = PendingIntent.getBroadcast(
            sendMessageActivity,
            myMessage.smsId as Int,
            alarmIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, cal.timeInMillis, pendingIntent)

    }


    fun cancelAlarm(myMessage: MyMessage) {

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, MessageReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            myMessage.smsId as Int,
            intent,
            PendingIntent.FLAG_CANCEL_CURRENT
        )
        alarmManager.cancel(pendingIntent)

    }

}