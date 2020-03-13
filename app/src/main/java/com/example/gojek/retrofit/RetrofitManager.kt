package com.example.gojek.retrofit

import com.example.gojek.api.APIList
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitManager {
    val TAG: String = RetrofitManager::class.java.name
    private var mRetrofit: Retrofit
    private val httpLoggingInterceptor: HttpLoggingInterceptor
    private var okHttpClient: OkHttpClient
    var customHttpInterceptors: CustomHttpInterceptors? = null

    const val _TIMEOUT_CONNECT_DEFAULT : Long = 3 //in seconds
    const val _TIMEOUT_READ_DEFAULT : Long = 10 //in seconds
    const val _TIMEOUT_WRITE_DEFAULT : Long = 20 //in seconds
    const val _TIMEOUT_READ_SHORT : Long = 5 //in seconds
    const val _TIMEOUT_WRITE_SHORT : Long = 5 //in seconds
    const val _TIMEOUT_READ_LONG : Long = 30 //in seconds
    const val _TIMEOUT_WRITE_LONG : Long = 60 //in seconds

    //To provide Retrofit service
    val service: APIList get() = mRetrofit.create(APIList::class.java)

    //Initialize needed properties like headers, base url, etc...
    init {
        httpLoggingInterceptor = HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)

        //Build ok http client
        okHttpClient = initOkHttp(HttpLoggingInterceptor())

        mRetrofit = Retrofit.Builder()
            .client(okHttpClient)
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://csrng.net/")
            .build()

        initRetrofit()
    }

    /**
     * Set Auth ID for headers
     */
    fun setAuthorization(token: String) {
        val customHttpInterceptors = CustomHttpInterceptors.Builder()
            .addHeaderParams(AUTHORIZATION, token)
            .build()
        okHttpClient = initOkHttp(customHttpInterceptors)
        initRetrofit()
    }

    /**
     * Set Auth and usersub for headers
     */
    fun setAuthAndUserSubs(token :String, userSub: String) {
        val customHttpInterceptors = CustomHttpInterceptors.Builder()
            .addHeaderParams(AUTHORIZATION, token)
            .addHeaderParams(USERSUB, userSub)
            .build()

        okHttpClient = initOkHttp(customHttpInterceptors)
        initRetrofit()
    }

    /**
     * Add custom headers in API
     */
    fun addCustomHeaders(map: Map<String, String>) {
        val customHttpBuilder: CustomHttpInterceptors.Builder = CustomHttpInterceptors.Builder()

        map.forEach {
            customHttpBuilder.addHeaderParams(it.key, it.value)
        }

        okHttpClient = initOkHttp(customHttpBuilder.build())
        initRetrofit()
    }

    /**
     * set to rebuild retrofit when port wants to be change
     */
    fun initRetrofit() {
        val gson = GsonBuilder()
            .setLenient()
            .create()

        mRetrofit = Retrofit.Builder()
            .client(okHttpClient)
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl(getBaseURL())
            .build()
    }

    /**
     * set to rebuild okhttp when there is new header
     */
    fun initOkHttp(
        customHttpInterceptors: HttpLoggingInterceptor?
        , readTimeout: Long = _TIMEOUT_READ_DEFAULT
        , writeTimeout: Long = _TIMEOUT_WRITE_DEFAULT
    ) : OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(_TIMEOUT_CONNECT_DEFAULT, TimeUnit.SECONDS)
            .writeTimeout(writeTimeout, TimeUnit.SECONDS)
            .readTimeout(readTimeout, TimeUnit.SECONDS)
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor(customHttpInterceptors)
            .build()
    }
}
