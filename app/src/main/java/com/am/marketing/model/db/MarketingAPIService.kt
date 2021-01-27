package com.am.marketing.model.db

import com.am.marketing.model.data.Marketing
import retrofit2.Call
import retrofit2.http.GET


interface MarketingAPIService {

    @GET("/b/226G")
    fun getMarketing(): Call<Marketing>

}