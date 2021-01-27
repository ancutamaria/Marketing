package com.am.marketing.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.am.marketing.model.data.Marketing
import com.am.marketing.model.db.CampaignsBenefitDao
import com.am.marketing.model.db.ChannelDao
import com.am.marketing.model.db.MarketingAPIService
import com.am.marketing.model.db.TargetingSpecificDao
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class MarketingRepository @Inject constructor(
        private val apiService: MarketingAPIService,
        private val targetingSpecificDao : TargetingSpecificDao,
        private val channelDao: ChannelDao,
        private val campaignsBenefitDao: CampaignsBenefitDao

){

    fun getMarketing(): LiveData<MarketingResponse<Marketing>>{
        val cachedTargetingSpecifics = targetingSpecificDao.getTargetingSpecifics()
        if (cachedTargetingSpecifics.isNotEmpty()) {
            var marketing = Marketing(cachedTargetingSpecifics, channelDao.getChannels(),
                    campaignsBenefitDao.getCampaignsBenefit())
            return MutableLiveData(MarketingResponse.OK(marketing))
        }
        val marketingResponse = MutableLiveData<MarketingResponse<Marketing>>()

        apiService.getMarketing().enqueue(object : Callback<Marketing> {
            override fun onResponse(call: Call<Marketing>, response: Response<Marketing>) {
                val responseTmp: Marketing? = response.body()
                marketingResponse.value = MarketingResponse.OK(responseTmp)
                if (responseTmp != null) {
                    targetingSpecificDao.setTargetingSpecifics(responseTmp.targetingSpecifics)
                    channelDao.setChannels(responseTmp.channels)
                    campaignsBenefitDao.setCampaignsBenefit(responseTmp.campaignsBenefits)
                }
            }
            override fun onFailure(call: Call<Marketing>, t: Throwable) {
                marketingResponse.value =
                        MarketingResponse.Error("Error on trying to access REST: \n ${t.message}")
            }
        })
        return marketingResponse
    }
}
