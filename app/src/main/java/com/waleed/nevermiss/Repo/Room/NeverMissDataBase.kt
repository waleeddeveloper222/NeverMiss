package com.waleed.nevermiss.Repo.Room

import android.content.Context
import androidx.room.*
import com.waleed.nevermiss.model.Groups
import com.waleed.nevermiss.model.MyMessage

@Database(entities = [Groups::class, MyMessage::class], version = 1, exportSchema = false)
@TypeConverters(ListConverter::class)
abstract class NeverMissDataBase : RoomDatabase() {

    abstract fun RoomDao(): RoomDAO

    companion object {

        @Volatile
        private var INSTANCE: NeverMissDataBase? = null

        fun getDatabase(context: Context): NeverMissDataBase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NeverMissDataBase::class.java,
                    "NeverMiss_Database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}