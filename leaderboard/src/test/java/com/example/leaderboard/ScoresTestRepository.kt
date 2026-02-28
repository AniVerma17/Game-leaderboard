package com.example.leaderboard

import com.example.leaderboard.data.UserScoreDto
import com.example.leaderboard.domain.UserScoreRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class ScoresTestRepository(initialList: List<UserScoreDto>) : UserScoreRepository {
    private val flow = MutableSharedFlow<List<UserScoreDto>>(replay = 1)

    init {
        flow.tryEmit(initialList)
    }

    override fun getAllUserScores() = flow.asSharedFlow()

    suspend fun updateScore(userScoreDto: UserScoreDto) {
        val currentList = flow.replayCache.first().toMutableList()
        currentList.removeIf { it.username == userScoreDto.username }
        currentList.add(userScoreDto)
        flow.emit(currentList.toList())
    }
}