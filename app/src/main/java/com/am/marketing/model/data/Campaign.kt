package com.am.marketing.model.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Campaign(
    @PrimaryKey val id: Int,
    val price: Int,
    val minListings: Int,
    val maxListings: Int,
    val optimizations: Int,
    val benefits: List<String>,
    var content: String,
    var selected: Boolean = false
)