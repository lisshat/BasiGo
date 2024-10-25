package com.example.basigo.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// Singleton object for Retrofit instance
object RetrofitInstance{

private const val SEAT_LAYOUT_BASE_URL = "http://10.0.2.2:5002/"

private const val BOOKING_API_BASE_URL = "http://10.0.2.2:5001/"

private const val BASE_URL = "http://10.0.2.2:5000/"

val seatLayoutRetrofit: Retrofit by lazy {
    Retrofit.Builder()
        .baseUrl(SEAT_LAYOUT_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}

    // Create Retrofit instance once, reuse it throughout the app
    val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

// Create Retrofit instance for Booking API
val bookingApiRetrofit: Retrofit by lazy {
    Retrofit.Builder()
        .baseUrl(BOOKING_API_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}
}

