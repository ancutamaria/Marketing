package com.am.marketing.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class MarketingRepository @Inject constructor(
    val apiService: MarketingAPIService
){

    fun getMarketing(): LiveData<MarketingResponse<Marketing>>{
        val marketingResponse = MutableLiveData<MarketingResponse<Marketing>>()
        apiService.getMarketing().enqueue(object : Callback<Marketing> {
            override fun onResponse(call: Call<Marketing>, response: Response<Marketing>) {
                val responseTmp: Marketing? = response.body()
                marketingResponse.value = MarketingResponse.OK(responseTmp)
            }
            override fun onFailure(call: Call<Marketing>, t: Throwable) {
                marketingResponse.value = MarketingResponse.Error("Error on trying to access REST: \n ${t.message}")
            }
        })
        return marketingResponse
    }
}