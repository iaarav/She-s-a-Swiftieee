package com.aarav.shesaswiftieee.player.use_case

import com.aarav.shesaswiftieee.data.SWIFT
import com.aarav.shesaswiftieee.player.controller.PlaybackController
import com.aarav.shesaswiftieee.player.service.MusicPlaybackController
import javax.inject.Inject

class AddMediaItemsUseCase(
    private val playbackController: PlaybackController
) {

    operator fun invoke(musics: List<SWIFT>) {
        playbackController.addMediaItems(musics)
    }
}