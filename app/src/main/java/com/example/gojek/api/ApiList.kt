package com.example.gojek.api

import com.example.gojek.data.SpeedResponse
import retrofit2.Call
import retrofit2.http.*

interface APIList {

@GET("csrng/csrng.php?min=0&max=100")
fun fetchData() : Call<SpeedResponse>
}