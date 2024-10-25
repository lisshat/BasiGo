package com.example.basigo.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.basigo.retrofit.seatApiService


@Composable
fun SeatBookingScreen(tripId: String,busId: String) {
    var bus by remember { mutableStateOf<Bus?>(null) }
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    // Fetch seat layout for the selected bus
    LaunchedEffect(busId) {
        try {
            val response = seatApiService.getBusSeatLayout(busId)
            bus = response  // Store the bus data, including the seat layout
            isLoading = false
        } catch (e: Exception) {
            errorMessage = "Failed to load seat layout"
            isLoading = false
            e.printStackTrace()  // Handle errors
        }
    }

    if (isLoading) {
        // Show a loading spinner
        Column {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        }
    } else if (errorMessage != null) {
        // Show error message
        Column {
            Text(
                text = errorMessage!!,
                color = Color.Red,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }
    } else {
        // Display seat layout
        bus?.let { busData ->
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(busData.seats.keys.size) { seatNumber ->
                    val seat = busData.seats[seatNumber.toString()]
                    seat?.let {
                        SeatCard(seatNumber = seatNumber + 1, seat = it)
                    }
                }
            }
        }
    }
}

@Composable
fun SeatCard(seatNumber: Int, seat: Seat) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(if (seat.available) Color.Green else Color.Gray)
    ) {
        Text(text = "Seat $seatNumber", modifier = Modifier.weight(1f))
        Text(text = "${seat.price} Ksh", modifier = Modifier.weight(1f))
        Text(text = seat.type, modifier = Modifier.weight(1f))
    }
}



// Seat data class to represent each seat's attributes
data class Seat(
    val available: Boolean,  // Is the seat available for booking?
    val price: Int,  // Price of the seat
    val type: String  // Type of the seat, e.g., "regular", "premium", etc.
)

// Bus data class to represent the entire bus including seat layout
data class Bus(
    val _id: String,  // Unique identifier for the bus
    val company: String,  // The company that owns the bus
    val seats: Map<String, Seat>,  // Map of seats with seat number as the key and Seat object as the value
    val totalSeats: Int  // Total number of seats in the bus
)
