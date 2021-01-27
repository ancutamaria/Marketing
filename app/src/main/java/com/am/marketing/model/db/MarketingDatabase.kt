package com.am.marketing.model.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.am.marketing.model.data.Campaign
import com.am.marketing.model.data.CampaignsBenefit
import com.am.marketing.model.data.Channel
import com.am.marketing.model.data.TargetingSpecific

@Database(entities = [TargetingSpecific::class, Channel::class, Campaign::class, CampaignsBenefit::class], version = 1)
@TypeConverters(Converters::class)
abstract class MarketingDatabase : RoomDatabase() {

    abstract fun targetingSpecificDao(): TargetingSpecificDao

    abstract fun channelDao(): ChannelDao

    abstract fun campaignsBenefitDao(): CampaignsBenefitDao

}