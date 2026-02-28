package com.example.gamescores

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.gamescores.leaderboard.LeaderBoardScreen
import com.example.gamescores.ui.theme.LeaderboardTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LeaderboardTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { paddingValues ->
                    Box(Modifier.padding(paddingValues)) {
                        LeaderBoardScreen()
                    }
                }
            }
        }
    }
}