package com.berglund.qapital.di

import com.berglund.qapital.usecase.ActivitiesUseCase
import com.berglund.qapital.repository.ActivitiesRepository
import com.berglund.qapital.repository.UserRepository
import com.berglund.qapital.usecase.UserUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
@ExperimentalCoroutinesApi
object UseCaseModule {

    @Provides
    @Singleton
    fun bindActivitiesUseCase(activitiesRepository: ActivitiesRepository): ActivitiesUseCase = ActivitiesUseCase(activitiesRepository)

    @Provides
    @Singleton
    fun bindUserUseCase(userRepository: UserRepository): UserUseCase = UserUseCase(userRepository)
}