package com.am.marketing.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.am.marketing.model.data.Channel
import com.am.marketing.model.data.Marketing
import com.am.marketing.model.MarketingRepository
import com.am.marketing.model.MarketingResponse

class MarketingViewModel @ViewModelInject constructor(
    marketingRepository: MarketingRepository
) : ViewModel() {

    val marketing: LiveData<MarketingResponse<Marketing>> = marketingRepository.getMarketing()

    var selectedCampaigns: MutableLiveData<MutableMap<Int, Int>> =
        MutableLiveData<MutableMap<Int, Int>>(mutableMapOf())

    var selectedTargetings: MutableSet<Int> = mutableSetOf()
    var eligibleChannels: MutableSet<Channel> = mutableSetOf()
    var selectedChannelID: Int = -1


    fun resetAll() {
        selectedTargetings = mutableSetOf()
        selectedCampaigns = MutableLiveData<MutableMap<Int, Int>>(mutableMapOf())
        eligibleChannels = mutableSetOf()
        selectedChannelID = -1
    }

    fun addSelectedTargetedSpecifics(id: Int, newSelected: Boolean) {
        if (newSelected) {
            selectedTargetings.add(id)
        } else {
            selectedTargetings.remove(id)
        }
    }

    fun addSelectedCampaigns(id: Int, newSelected: Boolean) {

        if (newSelected) {
            var tmpSelectedCampaigns = selectedCampaigns
            tmpSelectedCampaigns.value?.set(selectedChannelID, id)
            eligibleChannels.last() { channel ->
                channel.id == selectedChannelID
            }
                ?.campaigns?.forEach { campaign ->
                    if (campaign.id == id) campaign.selected = newSelected else campaign.selected =
                        false
                }
            selectedCampaigns.value = tmpSelectedCampaigns.value
        } else {
            selectedCampaigns.value?.remove(selectedChannelID)
        }
    }

    fun generateFinalSelections(): MutableList<String> {
        var finalSelections: MutableList<String> = mutableListOf()
        when (marketing.value) {
            is MarketingResponse.OK -> {
                (marketing.value as MarketingResponse.OK<Marketing>)?.data?.targetingSpecifics
                    ?.forEach { targetingSpecific ->
                        if (targetingSpecific.selected) {
                            targetingSpecific.channels.forEach { channelID ->
                                if (selectedCampaigns.value?.containsKey(channelID)!!) {
                                    finalSelections.add(
                                        targetingSpecific.label + " - " + eligibleChannels
                                            ?.find { it.id == channelID }?.name + "\n " + (eligibleChannels
                                            ?.find { it.id == channelID }?.campaigns
                                            ?.find { it.selected }?.content
                                            ?: "")) } } } } } }
        return finalSelections
    }

    fun generateEligibleChannels(){
        eligibleChannels = mutableSetOf()
        var marketingOK = marketing.value as MarketingResponse.OK<Marketing>
        marketingOK?.data?.targetingSpecifics
            ?.filter {
                selectedTargetings.contains(it.id)
            }?.forEach { targetingSpecific ->
                targetingSpecific.channels.forEach { channelID ->
                    marketingOK?.data?.channels
                        ?.find { it.id == channelID }
                        .let { channel ->
                            channel?.campaigns?.forEach { campaign ->
                                var st = "${campaign.price} EURO"
                                if (campaign.minListings > 0) {
                                    st += if (campaign.minListings == campaign.maxListings)
                                        "\n${campaign.minListings} listings"
                                    else "\n${campaign.minListings}-${campaign.maxListings} listings"
                                }
                                if (campaign.optimizations >0) st += "\n${campaign.optimizations} optimisations"
                                campaign.benefits.forEach { benefit ->
                                    st += "\n" + marketingOK?.data?.campaignsBenefits
                                        ?.find { it.id == benefit }
                                        ?.description
                                }
                                campaign.content = st
                            }
                            if (channel != null)
                                eligibleChannels?.add(channel)

                        }
                }
            }
    }
}