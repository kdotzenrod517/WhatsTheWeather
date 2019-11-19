package com.kdotz.whatstheweather.repository

import com.kdotz.whatstheweather.model.CurrentWeatherResponse
import com.kdotz.whatstheweather.web.WeatherAPIController
import io.reactivex.Observable

class SearchRepository(private val apiService: WeatherAPIController) {
    fun getCurrentWeather(q: String): Observable<CurrentWeatherResponse> {
        return apiService.getWeather(q)
    }
}