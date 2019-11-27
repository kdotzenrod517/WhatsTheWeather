package com.kdotz.whatstheweather

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import com.kdotz.whatstheweather.repository.SearchRepositoryProvider
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MainActivity : AppCompatActivity() {

    private lateinit var city: EditText
    private lateinit var description: TextView
    private lateinit var temp: TextView

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        city = findViewById(R.id.editText)
        description = findViewById(R.id.description)
        temp = findViewById(R.id.temp)

        loadCurrentWeather("Minneapolis")
    }

    private fun loadCurrentWeather(city : String) {
        val res = resources
        val repository = SearchRepositoryProvider.provideSearchRepository()
        compositeDisposable.add(
            repository.getCurrentWeather(city)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({ result ->
                    description.text = result.weather.first().description.toUpperCase()
                    temp.text = String.format(res.getString(R.string.temp), result.main.temp)
                    Log.d("Result", "There current weather in Minneapolis is a high of ${result.main.temp} with ${result.weather.first().description}")
                }, { error ->
                    error.printStackTrace()
                })
        )
    }

    private fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }

    fun onClick(view: View) {
        loadCurrentWeather(city.text.toString())
        view.hideKeyboard()
    }

    override fun onDestroy() {
        compositeDisposable.clear()
        super.onDestroy()
    }
}
