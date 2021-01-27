package com.am.marketing.model.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.am.marketing.model.data.Campaign

@Entity
data class Channel(
    @PrimaryKey val id: Int,
    val name: String,
    val campaigns: List<Campaign>
)