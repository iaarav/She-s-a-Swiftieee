package com.aarav.shesaswiftieee.ui.screens.songDetailScreen.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Pause
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.aarav.shesaswiftieee.R
import com.aarav.shesaswiftieee.data.PlayerState
import com.aarav.shesaswiftieee.data.SWIFT

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MusicMiniPlayerCard(
    modifier: Modifier = Modifier,
    music: SWIFT?,
    playerState: PlayerState?,
    onResumeClicked: () -> Unit,
    onPauseClicked: () -> Unit,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier,
        onClick = onClick
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(7.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(modifier = Modifier.weight(1f)) {
                music?.run {
                    AsyncImage(
                        modifier = Modifier
                            .size(45.dp)
                            .clip(MaterialTheme.shapes.small),
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(imageURL)
                            .build(),
                        contentScale = ContentScale.FillBounds,
                        contentDescription = "Music cover"
                    )
                    Spacer(modifier = Modifier.width(15.dp))
                    Column {
                        Text(
                            text = title!!,
                            style = MaterialTheme.typography.titleMedium,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Spacer(modifier = Modifier.height(5.dp))
                        Text(
                            text = singer!!,
                            style = MaterialTheme.typography.bodyMedium,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }
            IconButton(
                onClick = {
                    when (playerState) {
                        PlayerState.PLAYING -> onPauseClicked()
                        PlayerState.PAUSED -> onResumeClicked()
                        else -> {}
                    }
                }
            ) {
                Image(
                    imageVector = if (playerState == PlayerState.PLAYING) {
                        Icons.Rounded.Pause
                    } else {
                        Icons.Rounded.PlayArrow
                    },
                    contentDescription = "Play or pause button"
                )
            }
        }
    }
}