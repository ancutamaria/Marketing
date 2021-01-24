package com.am.marketing.model

sealed class MarketingResponse<out T> {

    data class OK<out V>(val data: V?): MarketingResponse<V>()
    data class Error(val message: String): MarketingResponse<Nothing>()

}