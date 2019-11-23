package com.waleed.nevermiss.model

import android.annotation.SuppressLint
import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "groups")
class Groups() : Parcelable {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    var id: Long = 0L
    lateinit var currentUser: String
    lateinit var groupName: String
    lateinit var contacts: List<Contact>
    var isChecked: Boolean = false


    constructor(
        currentUser: String,
        groupName: String,
        contacts: List<Contact>,
        isChecked: Boolean
    ) : this() {
        this.currentUser = currentUser
        this.groupName = groupName
        this.contacts = contacts
        this.isChecked = isChecked

    }

    constructor(
        id: Long,
        currentUser: String,
        groupName: String,
        contacts: List<Contact>,
        isChecked: Boolean = false

    ) : this() {
        this.id = id
        this.currentUser = currentUser
        this.groupName = groupName
        this.contacts = contacts
        this.isChecked = isChecked
    }

    constructor(parcel: Parcel) : this() {
        id = parcel.readLong()
        currentUser = parcel.readString()!!
        groupName = parcel.readString()!!
        contacts = parcel.createTypedArrayList(Contact)!!
        isChecked = parcel.readByte() != 0.toByte()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id)
        parcel.writeString(currentUser)
        parcel.writeString(groupName)
        parcel.writeTypedList(contacts)
        parcel.writeByte(if (isChecked) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Groups> {
        override fun createFromParcel(parcel: Parcel): Groups {
            return Groups(parcel)
        }

        override fun newArray(size: Int): Array<Groups?> {
            return arrayOfNulls(size)
        }
    }
}