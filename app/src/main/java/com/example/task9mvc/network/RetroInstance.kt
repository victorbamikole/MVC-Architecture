package com.example.task9mvc.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetroInstance {
    //The retro instance class for the base url is created
    companion object {
        const val BaseURL = "https://jsonplaceholder.typicode.com/"

        private val logger = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

        private val okHttp = OkHttpClient.Builder().addInterceptor(logger)

        fun getRetroInstance(): Retrofit {
            return Retrofit.Builder()
                .baseUrl(BaseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttp.build())
                .build()

        }

    }
}