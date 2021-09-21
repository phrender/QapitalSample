package com.berglund.qapital.netwotk

import com.berglund.qapital.entities.ActivitiesEntity
import com.berglund.qapital.entities.UserEntity
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.*

interface QapitalApi {

    @GET("activities/")
    suspend fun getActivities(@Query("from") from: Date, @Query("to") to: Date): Response<UserEntity>

    @GET("users/{userId}")
    suspend fun getUser(@Path("userId") userId: Int): Response<ActivitiesEntity>
}