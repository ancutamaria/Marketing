package com.am.marketing.model.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "targeting_specific")
data class TargetingSpecific(
    @PrimaryKey val id: Int,
    val label: String,
    val channels: List<Int>,
    var selected: Boolean = false
)