package com.example.leaderboard.domain

import com.example.leaderboard.data.UserScoreDto
import kotlinx.coroutines.flow.Flow

interface UserScoreRepository {
    fun getAllUserScores(): Flow<List<UserScoreDto>>
}