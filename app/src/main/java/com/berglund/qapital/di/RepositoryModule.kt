package com.berglund.qapital.di

import com.berglund.qapital.network.QapitalApi
import com.berglund.qapital.repository.ActivitiesRepository
import com.berglund.qapital.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {

    @Provides
    @ViewModelScoped
    fun provideUserRepository(api: QapitalApi): UserRepository = UserRepository(api)

    @Provides
    @ViewModelScoped
    fun provideActivitiesRepository(api :QapitalApi) = ActivitiesRepository(api)
}