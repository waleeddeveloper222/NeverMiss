package com.waleed.nevermiss.Repo.Room

import androidx.room.*
import com.waleed.nevermiss.model.Groups
import com.waleed.nevermiss.model.MyMessage

@Dao
interface RoomDAO {


    //----------- myMessage-------------
    //@Insert can return either void, long, Long, long[], Long[] or List<Long>.
    @Insert
    fun insertMessage(myMessage: MyMessage): Long?

    //Update methods must either return void or return int (the number of updated rows).
    @Update
    fun updateMessage(myMessage: MyMessage)

    @Query("UPDATE myMessage SET smsStatus = :state WHERE smsId = :id")
    fun updateMessageState(id: String, state: String): Int


    @Delete
    fun deleteMessage(myMessage: MyMessage)

    @Query("SELECT * FROM myMessage WHERE smsId =:id  AND userID =:currentUser")
    fun getMessage(id: String, currentUser: String): MyMessage

    @Query("SELECT * FROM myMessage WHERE userID =:currentUser")
    fun getUserAllMessages(currentUser: String): List<MyMessage>


    @Query("SELECT * FROM myMessage WHERE userID = :currentUser AND smsStatus = :state")
    fun getUserMessages(currentUser: String, state: String): List<MyMessage>


    //----------groups----------

    //@Insert can return either void, long, Long, long[], Long[] or List<Long>.
    @Insert
    fun insertGroup(groups: Groups): Long?

    //Update methods must either return void or return int (the number of updated rows).
    @Update
    fun updateGroup(groups: Groups): Int

//    @Query("UPDATE groups SET state = :state WHERE id = :id")
//    fun updateTripState(id: Long, state: String): Int

    @Delete
    fun deleteGroup(groups: Groups)

    @Query("SELECT * FROM groups WHERE id = :id AND currentUser = :currentUser")
    fun getGroup(id: Long, currentUser: String): Groups

    @Query("SELECT * FROM groups WHERE currentUser = :currentUser")
    fun getUserGroups(currentUser: String): List<Groups>

}