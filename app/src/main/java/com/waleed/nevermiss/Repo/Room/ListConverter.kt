package com.waleed.nevermiss.Repo.Room

import androidx.room.TypeConverter
import com.google.common.reflect.TypeToken
import com.waleed.nevermiss.model.Contact
import com.google.gson.Gson


class ListConverter {

    @TypeConverter
    fun restoreList(listOfString: String): List<Contact> {
        return Gson().fromJson(listOfString, object : TypeToken<List<Contact>>() {
        }.type)
    }

    @TypeConverter
    fun saveList(listOfContact: List<Contact>): String {
        return Gson().toJson(listOfContact)
    }
}




