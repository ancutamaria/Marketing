package com.am.marketing.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.am.marketing.model.Marketing
import com.am.marketing.model.MarketingRepository

class TargetingSpecificsViewModel @ViewModelInject constructor(
    marketingRepository: MarketingRepository
) : ViewModel() {

    val targetingSpecific: LiveData<Marketing> = marketingRepository.getMarketing()

}