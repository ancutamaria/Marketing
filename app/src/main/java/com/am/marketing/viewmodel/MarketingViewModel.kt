package com.am.marketing.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.am.marketing.model.Channel
import com.am.marketing.model.Marketing
import com.am.marketing.model.MarketingRepository

class MarketingViewModel @ViewModelInject constructor(
    marketingRepository: MarketingRepository
) : ViewModel() {

    val marketing: LiveData<Marketing> = marketingRepository.getMarketing()

    var selectedTargetings: MutableSet<Int> = mutableSetOf()
    var selectedCampaigns: MutableMap<Int, Int> = mutableMapOf()
    var eligibleChannels: MutableSet<Channel> = mutableSetOf()
    var selectedChannelID: Int = -1

    fun addSelectedTargetedSpecifics(id: Int, newSelected: Boolean) {
        if (newSelected){
            selectedTargetings.add(id)
        } else {
            selectedTargetings.remove(id)
        }
    }

    fun addSelectedCampaigns(id: Int, newSelected: Boolean) {
        if (newSelected){
            selectedCampaigns[selectedChannelID] = id
        } else {
            selectedCampaigns.remove(selectedChannelID)
        }
    }

    fun generateFinalSelections(): MutableList<String> {
        var finalSelections: MutableList<String> = mutableListOf()
        marketing.value?.targetingSpecifics?.forEach { targetingSpecific ->
            if (targetingSpecific.selected){
                targetingSpecific.channels.forEach{ channelID ->
                    if (selectedCampaigns.containsKey(channelID)){
                        finalSelections.add(targetingSpecific.label + " - " + eligibleChannels
                            ?.find { it.id == channelID }?.name + "\n " + (eligibleChannels
                            ?.find { it.id == channelID }?.campaigns
                            ?.find { it.selected }?.content
                            ?: ""))
                    }
                }
            }
        }
        return finalSelections
    }

    fun generateEligibleChannels(){
        eligibleChannels = mutableSetOf()
        marketing.value?.targetingSpecifics
            ?.filter {
                selectedTargetings.contains(it.id)
            }?.forEach { targetingSpecific ->
                targetingSpecific.channels.forEach { channelID ->
                    marketing.value!!.channels
                        .find { it.id == channelID }
                        .let { channel ->
                            if (channel != null) {
                                channel.campaigns.forEach { campaign ->
                                    var st = "${campaign.price} EURO"
                                    if (campaign.minListings > 0) {
                                        st += if (campaign.minListings == campaign.maxListings)
                                            "\n${campaign.minListings} listings"
                                        else "\n${campaign.minListings}-${campaign.maxListings} listings"
                                    }
                                    if (campaign.optimizations >0) st += "\n${campaign.optimizations} optimisations"
                                    campaign.benefits.forEach { benefit ->
                                        st += "\n" + marketing.value!!.campaignsBenefits
                                            .find { it.id == benefit }
                                            ?.description
                                    }
                                    campaign.content = st
                                }
                                eligibleChannels?.add(channel)

                            }
                        }
                }
            }
    }
}