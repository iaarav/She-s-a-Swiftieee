package com.aarav.shesaswiftieee.di

import com.aarav.shesaswiftieee.player.use_case.AddMediaItemsUseCase
import com.aarav.shesaswiftieee.player.use_case.DestroyMediaControllerUseCase
import com.aarav.shesaswiftieee.player.use_case.GetCurrentMusicPositionUseCase
import com.aarav.shesaswiftieee.player.use_case.PauseMusicUseCase
import com.aarav.shesaswiftieee.player.use_case.PlayMusicUseCase
import com.aarav.shesaswiftieee.player.use_case.ResumeMusicUseCase
import com.aarav.shesaswiftieee.player.use_case.SeekMusicPositionUseCase
import com.aarav.shesaswiftieee.player.use_case.SetMediaControllerCallbackUseCase
import com.aarav.shesaswiftieee.player.use_case.SetMusicShuffleEnabledUseCase
import com.aarav.shesaswiftieee.player.use_case.SetPlayerRepeatOneEnabledUseCase
import com.aarav.shesaswiftieee.player.use_case.SkipNextMusicUseCase
import com.aarav.shesaswiftieee.player.use_case.SkipPreviousMusicUseCase
import org.koin.dsl.module

val domainModule = module {
    single { AddMediaItemsUseCase(get()) }
    single { SetMediaControllerCallbackUseCase(get()) }
    single { PlayMusicUseCase(get()) }
    single { ResumeMusicUseCase(get()) }
    single { PauseMusicUseCase(get()) }
    single { SeekMusicPositionUseCase(get()) }
    single { SkipNextMusicUseCase(get()) }
    single { SkipPreviousMusicUseCase(get()) }
    single { SetMusicShuffleEnabledUseCase(get()) }
    single { SetPlayerRepeatOneEnabledUseCase(get()) }
    single { GetCurrentMusicPositionUseCase(get()) }
    single { DestroyMediaControllerUseCase(get()) }
}