package com.kdotz.whatstheweather

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import com.kdotz.whatstheweather.repository.SearchRepositoryProvider
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MainActivity : AppCompatActivity() {

    private lateinit var city: EditText
    private lateinit var description: TextView

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        city = findViewById(R.id.editText)
        description = findViewById(R.id.description)

        loadCurrentWeather("Minneapolis")
    }

    private fun loadCurrentWeather(city : String) {
        val repository = SearchRepositoryProvider.provideSearchRepository()
        compositeDisposable.add(
            repository.getCurrentWeather(city)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({ result ->
                    description.text = result.weather.first().description.toUpperCase()
                    Log.d("Result", "There current weather in Minneapolis is a high of ${result.main.temp} with ${result.weather.first().description}")
                }, { error ->
                    error.printStackTrace()
                })
        )
    }

    fun onClick(view: View) {
        loadCurrentWeather(city.text.toString())
    }

    override fun onDestroy() {
        compositeDisposable.clear()
        super.onDestroy()
    }
}
