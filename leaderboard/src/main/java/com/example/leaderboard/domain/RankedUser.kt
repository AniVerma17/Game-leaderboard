package com.example.leaderboard.domain

data class RankedUser(
    val username: String,
    val score: Int,
    val rank: Int
)
