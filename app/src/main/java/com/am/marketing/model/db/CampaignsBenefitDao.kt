package com.am.marketing.model.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.am.marketing.model.data.CampaignsBenefit

@Dao
interface CampaignsBenefitDao {

    @Query("SELECT * FROM campaigns_benefit")
    fun getCampaignsBenefit(): List<CampaignsBenefit>

    @Insert
    fun setCampaignsBenefit(campaignsBenefit: List<CampaignsBenefit>)

}