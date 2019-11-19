package com.kdotz.whatstheweather.web

import com.kdotz.whatstheweather.model.CurrentWeatherResponse
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherAPIController {

    @GET("weather?APPID=a07563fa03886ad217aa914f5460dde2&units=imperial")
    fun getWeather(@Query("q") q : String) : Observable<CurrentWeatherResponse>

    /**
     * Companion object to create the GithubApiService
     */
    companion object Factory {
        fun create(): WeatherAPIController {
            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("http://api.openweathermap.org/data/2.5/")
                .build()

            return retrofit.create(WeatherAPIController::class.java)
        }
    }
}