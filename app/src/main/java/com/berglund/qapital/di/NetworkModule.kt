package com.berglund.qapital.di

import com.berglund.qapital.BuildConfig
import com.berglund.qapital.network.QapitalApi
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

	@Provides
	@Singleton
	fun provideOkHttpClient(): OkHttpClient {
		val builder = OkHttpClient.Builder()
		builder
			.readTimeout(60L, TimeUnit.SECONDS)
			.writeTimeout(60L, TimeUnit.SECONDS)

		if (BuildConfig.DEBUG) {
			builder.addInterceptor(provideLoggingInterceptor())
		}

		return builder.build()
	}

	@Provides
	@Singleton
	fun provideLoggingInterceptor(): HttpLoggingInterceptor =
		HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

	@Provides
	@Singleton
	fun provideGson(): Gson =
		GsonBuilder()
			.setDateFormat("yyyy-MM-DD'T'HH:mm:ssZ")
			.setLenient()
			.create()

	@Provides
	@Singleton
	fun provideRetrofit(okHttpClient: OkHttpClient, gson: Gson): Retrofit =
		Retrofit.Builder()
			.client(okHttpClient)
			.baseUrl("http://qapital-ios-testtask.herokuapp.com/")
			.addConverterFactory(GsonConverterFactory.create(gson))
			.build()

	@Provides
	@Singleton
	fun provideApiService(retrofit: Retrofit): QapitalApi = retrofit.create(QapitalApi::class.java)
}
