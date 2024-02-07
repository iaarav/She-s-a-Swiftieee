package com.aarav.shesaswiftieee.ui.viewModel.shared

import com.aarav.shesaswiftieee.data.PlayerState
import com.aarav.shesaswiftieee.data.SWIFT

data class MusicPlaybackUIState(
    val playerState: PlayerState? = null,
    val currentMusic: SWIFT? = null,
    val currentPosition: Long = 0L,
    val totalDuration: Long = 0L,
    val isShuffleEnabled: Boolean = false,
    val isRepeatOneEnabled: Boolean = false
)
