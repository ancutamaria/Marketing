package com.am.marketing.model

data class Channel(
    val id: Int,
    val name: String,
    val campaigns: List<Campaign>
)