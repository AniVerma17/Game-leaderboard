package com.example.leaderboard.domain

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetLeaderboardUseCase @Inject constructor(
    private val userScoreRepository: UserScoreRepository
) {
    operator fun invoke(): Flow<List<RankedUser>> = userScoreRepository.getAllUserScores()
        .map { userScores ->
            var rank = 0
            userScores.sortedByDescending { user -> user.score }.let {
                it.mapIndexed { index, user ->
                    RankedUser(
                        user.username,
                        user.score,
                        if (it.getOrNull(index - 1)?.score == user.score) rank else ++rank
                    )
                }
            }
        }
        .flowOn(Dispatchers.Default)
}