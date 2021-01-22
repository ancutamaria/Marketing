package com.am.marketing.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.am.marketing.model.Marketing
import com.am.marketing.model.MarketingRepository

class MarketingViewModel @ViewModelInject constructor(
    marketingRepository: MarketingRepository
) : ViewModel() {

    var selectedTargetings: MutableSet<Int> = mutableSetOf()
    val marketing: LiveData<Marketing> = marketingRepository.getMarketing()

    fun addSelectedTargetedSpecifics(id: Int, newSelected: Boolean) {
        if (newSelected){
            selectedTargetings.add(id)
        } else {
            selectedTargetings.remove(id)
        }
    }

}