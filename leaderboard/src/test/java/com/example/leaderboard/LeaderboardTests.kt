package com.example.leaderboard

import com.example.leaderboard.data.UserScoreDto
import com.example.leaderboard.domain.GetLeaderboardUseCase
import com.example.leaderboard.domain.RankedUser
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Test

class LeaderboardTests {

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun rankingsUpdates() = runTest {
        val initialList = listOf(
            UserScoreDto("Player 1", 1500),
            UserScoreDto("Player 2", 1300),
            UserScoreDto("Player 3", 400),
            UserScoreDto("Player 4", 1400),
            UserScoreDto("Player 5", 2100),
        )
        var expectedRankedList = listOf(
            RankedUser("Player 5", 2100, 1),
            RankedUser("Player 1", 1500, 2),
            RankedUser("Player 4", 1400, 3),
            RankedUser("Player 2", 1300, 4),
            RankedUser("Player 3", 400, 5),
        )
        val repository = ScoresTestRepository(initialList)
        val leaderboardUseCase = GetLeaderboardUseCase(repository)

        assertEquals(expectedRankedList, leaderboardUseCase().first())

        repository.updateScore(UserScoreDto("Player 1", 2200))
        expectedRankedList = listOf(
            RankedUser("Player 1", 2200, 1),
            RankedUser("Player 5", 2100, 2),
            RankedUser("Player 4", 1400, 3),
            RankedUser("Player 2", 1300, 4),
            RankedUser("Player 3", 400, 5),
        )
        assertEquals(expectedRankedList, leaderboardUseCase().first())

        repository.updateScore(UserScoreDto("Player 3", 2100))
        expectedRankedList = listOf(
            RankedUser("Player 1", 2200, 1),
            RankedUser("Player 5", 2100, 2),
            RankedUser("Player 3", 2100, 2),
            RankedUser("Player 4", 1400, 3),
            RankedUser("Player 2", 1300, 4),
        )
        assertEquals(expectedRankedList, leaderboardUseCase().first())
    }
}