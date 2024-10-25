package com.example.basigo.retrofit

import com.example.basigo.screens.Bus
import com.example.basigo.screens.Seat
import retrofit2.http.GET
import retrofit2.http.Path

interface SeatApiService {

    // Define the GET request for fetching seat layout by busId
    @GET("api/bus/{busId}")
    suspend fun getBusSeatLayout(
        @Path("busId") busId: String
    ): Map<String, Seat>
}

val seatApiService: SeatApiService by lazy {
    RetrofitInstance.retrofit.create(SeatApiService::class.java)
}
