package com.example.gojek.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.gojek.api.APIList
import com.example.gojek.api.ApiMain
import com.example.gojek.data.SpeedResponse
import com.example.gojek.utilities.CallBackKt
import retrofit2.Call

/**
 * This class is responsible to be repository that related with ScheduleList
 *
 * @author    Taufan S  <taufansetiawan@onoff.insure>
 */
class SpeedRepository(var context: Context) {
    /**
     * Function to use custom callback retrofit
     */
    fun<T> Call<T>.enqueue(callback: CallBackKt<T>.() -> Unit) {
        val callBackKt = CallBackKt<T>()

        callback.invoke(callBackKt)

        this.enqueue(callBackKt)
    }

    /**
     * To call Wallet API
     */
    fun getWallet(): LiveData<SpeedResponse> {
        val walletResponse: MutableLiveData<SpeedResponse> = MutableLiveData()

        /**
         * get Wallet balance from retrofit Manager
         */
        ApiMain().services.fetchData().enqueue {
            onSuccess = {
                walletResponse.value = it.body()
            }

            onFailure = {}
        }

        return walletResponse
    }
}
