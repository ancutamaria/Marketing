package com.am.marketing.model

import com.am.marketing.model.Campaign


data class Channel(
    val id: Int,
    val name: String,
    val campaigns: List<Campaign>
)