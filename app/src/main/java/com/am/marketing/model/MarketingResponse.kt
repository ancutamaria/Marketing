package com.am.marketing.model


data class MarketingResponse(
        val targetingSpecifics: List<TargetingSpecific>,
        val channels: List<Channel>,
        val campaignsBenefits: List<CampaignsBenefit>
)