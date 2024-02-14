package com.aarav.shesaswiftieee

import android.app.Application
import com.aarav.shesaswiftieee.player.service.MusicPlaybackController
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class MyApplication : Application(){
    @Inject
    lateinit var playbackController: MusicPlaybackController
}