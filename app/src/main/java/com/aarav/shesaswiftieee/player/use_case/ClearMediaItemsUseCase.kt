package com.aarav.shesaswiftieee.player.use_case

import com.aarav.shesaswiftieee.player.service.MusicPlaybackController
import javax.inject.Inject

class ClearMediaItemsUseCase @Inject constructor(private val playbackController: MusicPlaybackController) {
    operator fun invoke(){
        playbackController.clear()
    }
}