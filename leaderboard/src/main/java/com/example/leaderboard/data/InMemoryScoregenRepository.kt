package com.example.leaderboard.data

import com.example.leaderboard.domain.UserScoreRepository
import com.example.scoregen.LiveScoreDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class InMemoryScoregenRepository @Inject constructor() : UserScoreRepository {
    override fun getAllUserScores(): Flow<List<UserScoreDto>> = LiveScoreDataSource.scoresFlow.map {
        it.entries.map { (username, score) -> UserScoreDto(username, score) }
    }.flowOn(Dispatchers.Default)
}