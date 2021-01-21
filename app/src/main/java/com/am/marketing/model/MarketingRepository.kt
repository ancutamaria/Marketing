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

    fun getMarketing(): LiveData<Marketing>{
        val data = MutableLiveData<Marketing>()
        apiService.getMarketing().enqueue(object : Callback<Marketing> {
            override fun onResponse(call: Call<Marketing>, response: Response<Marketing>) {
                data.value = response.body()
            }
            override fun onFailure(call: Call<Marketing>, t: Throwable) {
                TODO()
            }
        })
        return data
    }
}