package com.example.robodate.api

import com.example.robodate.utils.Constants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.util.concurrent.TimeUnit

object RetrofitClient {

    private val loggingInterceptor = HttpLoggingInterceptor { message ->
        Timber.tag("OkHttp").d(message)
    }.apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .build()

    private fun createRetrofit(baseUrl: String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val agifyApi: AgifyApi by lazy {
        createRetrofit(Constants.AGIFY_BASE_URL).create(AgifyApi::class.java)
    }

    val dadJokeApi: DadJokeApi by lazy {
        createRetrofit(Constants.DAD_JOKE_BASE_URL).create(DadJokeApi::class.java)
    }

    val yesNoApi: YesNoApi by lazy {
        createRetrofit(Constants.YES_NO_BASE_URL).create(YesNoApi::class.java)
    }
}
