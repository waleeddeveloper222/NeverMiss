package com.waleed.nevermiss.model

import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "myMessage")

class MyMessage() : Parcelable {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    var smsId: Long = 0
    lateinit var smsMsg: String
    lateinit var smsDate: String
    lateinit var smsTime: String
    lateinit var smsStatus: String
    lateinit var smsType: String
    lateinit var userID: String
    lateinit var contacts: List<Contact>

    constructor(parcel: Parcel) : this() {
        smsId = parcel.readLong()
        smsMsg = parcel.readString()!!
        smsDate = parcel.readString()!!
        smsTime = parcel.readString()!!
        smsStatus = parcel.readString()!!
        smsType = parcel.readString()!!
        userID = parcel.readString()!!
        contacts = parcel.createTypedArrayList(Contact.CREATOR)!!
    }


    constructor(
        smsId: Long,
        smsMsg: String,
        smsDate: String,
        smsTime: String,
        smsStatus: String,
        smsType: String,
        userID: String,
        contacts: List<Contact>
    ) : this() {
        this.smsId = smsId
        this.smsMsg = smsMsg
        this.smsDate = smsDate
        this.smsTime = smsTime
        this.smsStatus = smsStatus
        this.smsType = smsType
        this.userID = userID
        this.contacts = contacts
    }

    constructor(
        smsMsg: String,
        smsDate: String,
        smsTime: String,
        userID: String,
        contacts: List<Contact>
    ) : this() {
        this.smsMsg = smsMsg
        this.smsDate = smsDate
        this.smsTime = smsTime
        this.userID = userID
        this.contacts = contacts
    }


    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(smsId)
        parcel.writeString(smsMsg)
        parcel.writeString(smsDate)
        parcel.writeString(smsTime)
        parcel.writeString(smsStatus)
        parcel.writeString(smsType)
        parcel.writeString(userID)
//        parcel.writeList(contacts as List<*>?)
        parcel.writeTypedList(contacts);

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MyMessage> {
        override fun createFromParcel(parcel: Parcel): MyMessage {
            return MyMessage(parcel)
        }

        override fun newArray(size: Int): Array<MyMessage?> {
            return arrayOfNulls(size)
        }
    }
}