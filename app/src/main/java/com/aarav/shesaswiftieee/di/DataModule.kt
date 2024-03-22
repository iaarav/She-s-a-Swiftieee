package com.aarav.shesaswiftieee.di

import com.aarav.shesaswiftieee.player.controller.PlaybackController
import com.aarav.shesaswiftieee.player.service.MusicPlaybackController
import com.aarav.shesaswiftieee.repository.FireRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dataModule = module {
    single { FireRepository() }
    single<PlaybackController> { MusicPlaybackController(androidContext()) }
}