package com.am.marketing.di

import android.content.Context
import androidx.room.Room
import com.am.marketing.model.MarketingRepository
import com.am.marketing.model.db.*
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideRetrofit(gson: Gson) : Retrofit = Retrofit.Builder()
        .baseUrl("https://jsonkeeper.com/")
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    @Provides
    fun provideAPIService(retrofit: Retrofit): MarketingAPIService = retrofit.create(
        MarketingAPIService::class.java)

    @Provides
    fun provideTargetingSpecificDao(marketingDatabase: MarketingDatabase): TargetingSpecificDao {
        return marketingDatabase.targetingSpecificDao()
    }

    @Provides
    fun provideChannelDao(marketingDatabase: MarketingDatabase): ChannelDao {
        return marketingDatabase.channelDao()
    }

    @Provides
    fun provideCampaignsBenefitDao(marketingDatabase: MarketingDatabase): CampaignsBenefitDao {
        return marketingDatabase.campaignsBenefitDao()
    }

    @Provides
    @Singleton
    fun provideMarketingDatabase(@ApplicationContext appContext: Context): MarketingDatabase {
        return Room.databaseBuilder(
            appContext,
            MarketingDatabase::class.java,
            "RssReader"
        ).allowMainThreadQueries().build()
    }

    @Singleton
    @Provides
    fun provideMarketingRepository(apiService: MarketingAPIService,
                                   targetingSpecificDao: TargetingSpecificDao,
                                   channelDao: ChannelDao,
                                   campaignsBenefitDao: CampaignsBenefitDao
    ) =
        MarketingRepository(apiService, targetingSpecificDao, channelDao, campaignsBenefitDao)

}