package com.am.marketing.model.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.am.marketing.model.data.TargetingSpecific

@Dao
interface TargetingSpecificDao {

    @Query("SELECT * FROM targeting_specific")
    fun getTargetingSpecifics(): List<TargetingSpecific>

    @Insert
    fun setTargetingSpecifics(targetingSpecifics: List<TargetingSpecific>)

}