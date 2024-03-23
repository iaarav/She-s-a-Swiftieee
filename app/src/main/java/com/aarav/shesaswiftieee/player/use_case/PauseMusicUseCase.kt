package com.aarav.shesaswiftieee.player.use_case

import com.aarav.shesaswiftieee.player.controller.PlaybackController
import com.aarav.shesaswiftieee.player.service.MusicPlaybackController
import javax.inject.Inject

class PauseMusicUseCase (private val playbackController: PlaybackController) {


    operator fun invoke() {

            playbackController.pause()

    }
}