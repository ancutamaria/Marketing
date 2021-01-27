package com.am.marketing.model.data


data class Marketing(
        val targetingSpecifics: List<TargetingSpecific>,
        val channels: List<Channel>,
        val campaignsBenefits: List<CampaignsBenefit>
)