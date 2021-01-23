package com.am.marketing.model

data class Campaign(
    val id: Int,
    val price: Int,
    val minListings: Int,
    val maxListings: Int,
    val optimizations: Int,
    val benefits: List<Any>,
    var content: String,
    var selected: Boolean = false
)