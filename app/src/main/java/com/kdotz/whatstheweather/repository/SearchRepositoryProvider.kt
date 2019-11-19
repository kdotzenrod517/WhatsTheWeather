package com.kdotz.whatstheweather.repository

import com.kdotz.whatstheweather.web.WeatherAPIController

object SearchRepositoryProvider {

    fun provideSearchRepository(): SearchRepository {
        return SearchRepository(WeatherAPIController.Factory.create())
    }

}