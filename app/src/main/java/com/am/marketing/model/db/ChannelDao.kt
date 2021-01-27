package com.am.marketing.model.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.am.marketing.model.data.Channel

@Dao
interface ChannelDao {

    @Query("SELECT * FROM channel")
    fun getChannels(): List<Channel>

    @Insert
    fun setChannels(campaignsBenefit: List<Channel>)

}