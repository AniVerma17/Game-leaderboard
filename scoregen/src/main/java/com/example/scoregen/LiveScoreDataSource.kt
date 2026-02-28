package com.example.scoregen

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.shareIn
import kotlin.random.Random
import kotlin.random.nextInt

object LiveScoreDataSource {
    private val users: MutableMap<String, Int> = mutableMapOf(
        "adrock123" to 0,
        "brood@weeng" to 0,
        "grooum@lol" to 0,
        "DancingPotato" to 0,
        "CaptainQuirk" to 0,
        "BubbleWrapBandit" to 0,
        "DPadDiva" to 0,
        "god0fw@r" to 0,
        "thenoos.666" to 0,
        "Stark@3000" to 0
    )

    private val coroutineScope = CoroutineScope(Dispatchers.Default)

    val scoresFlow: SharedFlow<Map<String, Int>> = flow {
        emit(users.toMap())
        while (true) {
            delay(Random.nextInt(1..4) * 500L)
            val key = users.keys.random()
            users[key] = users[key]?.plus(100) ?: 0
            emit(users.toMap())
        }
    }.shareIn(
        coroutineScope,
        started = SharingStarted.Eagerly,
        replay = 1
    )
}