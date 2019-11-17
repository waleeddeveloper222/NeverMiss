package com.waleed.nevermiss.model

import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "groups")
class Groups() : Parcelable {

    override fun writeToParcel(p0: Parcel?, p1: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun describeContents(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    @PrimaryKey(autoGenerate = true)
    @NonNull
    var id: Long = 0L
    lateinit var currentUser: String
    lateinit var groupName: String

    lateinit var contacts: List<Contact>

    constructor(parcel: Parcel) : this() {
        id = parcel.readLong()
        currentUser = parcel.readString()!!
        groupName = parcel.readString()!!

    }


    constructor(currentUser: String, groupName: String, contacts: List<Contact>) : this() {
        this.currentUser = currentUser
        this.groupName = groupName
        this.contacts = contacts
    }

    constructor(
        id: Long,
        currentUser: String,
        groupName: String,
        contacts: List<Contact>
    ) : this() {
        this.id = id
        this.currentUser = currentUser
        this.groupName = groupName
        this.contacts = contacts
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