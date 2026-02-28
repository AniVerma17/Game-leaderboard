package com.example.leaderboard.di

import com.example.leaderboard.data.InMemoryScoregenRepository
import com.example.leaderboard.domain.UserScoreRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class LeaderboardModule {

    @Binds
    abstract fun provideScoregenRepository(value: InMemoryScoregenRepository): UserScoreRepository
}