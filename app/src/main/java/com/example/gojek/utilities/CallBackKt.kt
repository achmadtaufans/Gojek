package com.example.gojek.utilities

import retrofit2.Call
import retrofit2.Response

class CallBackKt<T>: retrofit2.Callback<T> {
    var onSuccess: ((Response<T>) -> Unit)? = null
    var onFailure: ((t: Throwable?) -> Unit)? = null

    /**
     * Get failure response if it's failed to call API to server
     */
    override fun onResponse(call: Call<T>, response: Response<T>) {
        onSuccess?.invoke(response)
    }

    /**
     * Get response if it's success to call API to server
     */
    override fun onFailure(call: Call<T>, throwable: Throwable) {
        onFailure?.invoke(throwable)
    }
}