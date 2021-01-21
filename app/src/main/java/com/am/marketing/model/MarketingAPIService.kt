package com.am.marketing.model

import retrofit2.http.GET


interface MarketingAPIService {

    @GET("226G")
    suspend fun getMarketingResponse(): MarketingResponse

}