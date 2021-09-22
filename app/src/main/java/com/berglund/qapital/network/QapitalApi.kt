package com.berglund.qapital.network

import com.berglund.qapital.entities.ActivitiesEntity
import com.berglund.qapital.entities.UserEntity
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface QapitalApi {

    @GET("activities")
    fun getActivities(@Query("from", encoded = true) from: String, @Query("to", encoded = true) to: String): Call<ActivitiesEntity>

    @GET("users/{userId}")
    fun getUser(@Path("userId") userId: Int): Call<UserEntity>
}