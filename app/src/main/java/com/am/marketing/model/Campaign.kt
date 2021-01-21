package com.am.marketing.model


import com.google.gson.annotations.SerializedName

data class Campaign(
    val id: String,
    val price: Int,
    val minListings: Int,
    val maxListings: Int,
    val optimizations: Int,
    val benefits: List<Int>
)