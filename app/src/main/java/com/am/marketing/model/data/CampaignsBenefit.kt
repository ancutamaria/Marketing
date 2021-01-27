package com.am.marketing.model.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "campaigns_benefit")
data class CampaignsBenefit(
    @PrimaryKey val id: String,
    val description: String
)