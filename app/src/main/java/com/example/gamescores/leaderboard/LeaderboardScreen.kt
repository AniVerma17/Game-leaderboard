package com.example.gamescores.leaderboard

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.leaderboard.domain.RankedUser

@Composable
fun LeaderBoardScreen(viewModel: LeaderboardViewModel = viewModel()) {
    val rankedUsers by viewModel.scoresList.collectAsStateWithLifecycle()
    LeaderboardView(rankedUsers)
}

@Composable
fun LeaderboardView(rankedUsers: List<RankedUser>) {
    LazyColumn {
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Rank", style = MaterialTheme.typography.bodyLarge)
                Spacer(Modifier.width(24.dp))
                Text(
                    text = "Username",
                    modifier = Modifier.weight(1f),
                    style = MaterialTheme.typography.bodyLarge,
                )
                Text(
                    text = "Score",
                    modifier = Modifier.padding(end = 16.dp),
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.End
                )
            }
        }
        items(rankedUsers, key = { it.username }) { user ->
            RankedUserItem(
                user = user,
                modifier = Modifier.animateItem(
                    fadeInSpec = tween(durationMillis = 250),
                    fadeOutSpec = tween(durationMillis = 100),
                    placementSpec = spring(
                        stiffness = Spring.StiffnessLow,
                        dampingRatio = Spring.DampingRatioMediumBouncy
                    )
                )
            )
        }
    }
}

@Composable
fun RankedUserItem(user: RankedUser, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "${user.rank}", style = MaterialTheme.typography.bodyLarge)
            Spacer(Modifier.width(32.dp))
            Text(
                text = user.username,
                modifier = Modifier.weight(1f),
                style = MaterialTheme.typography.bodyLarge
            )
            Spacer(Modifier.width(24.dp))
            Text(text = user.score.toString(), style = MaterialTheme.typography.bodyLarge)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LeaderboardViewPreview() {
    val users = listOf(
        RankedUser("Player 1", 1500, 1),
        RankedUser("Player 2", 1450, 2),
        RankedUser("Player 3", 1400, 3),
        RankedUser("Player 4", 1350, 4),
        RankedUser("Player 5", 1300, 5)
    )
    LeaderboardView(rankedUsers = users)
}
