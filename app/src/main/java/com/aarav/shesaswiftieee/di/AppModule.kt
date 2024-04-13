package com.aarav.shesaswiftieee.di

import android.app.Application
import com.aarav.shesaswiftieee.player.service.MusicPlaybackController
import com.aarav.shesaswiftieee.repository.FireRepository
import com.aarav.shesaswiftieee.player.use_case.AddMediaItemsUseCase
import com.aarav.shesaswiftieee.player.use_case.ClearMediaItemsUseCase
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
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideFireRepository() = FireRepository()

    @Provides
    @Singleton
    fun AddMediaItems(playbackController: MusicPlaybackController): AddMediaItemsUseCase =
        AddMediaItemsUseCase(playbackController)

    @Provides
    @Singleton
    fun ClearMediaItems(playbackController: MusicPlaybackController): ClearMediaItemsUseCase =
        ClearMediaItemsUseCase(playbackController)

    @Provides
    @Singleton
    fun DestroyMediaController(playbackController: MusicPlaybackController): DestroyMediaControllerUseCase =
        DestroyMediaControllerUseCase(playbackController)

    @Provides
    @Singleton
    fun GetCurrentMusicPosition(playbackController: MusicPlaybackController): GetCurrentMusicPositionUseCase =
        GetCurrentMusicPositionUseCase(playbackController)


    @Provides
    @Singleton
    fun PauseMusic(playbackController: MusicPlaybackController): PauseMusicUseCase =
        PauseMusicUseCase(playbackController)

    @Provides
    @Singleton
    fun PlayMusic(playbackController: MusicPlaybackController): PlayMusicUseCase =
        PlayMusicUseCase(playbackController)

    @Provides
    @Singleton
    fun ResumeMusic(playbackController: MusicPlaybackController): ResumeMusicUseCase =
        ResumeMusicUseCase(playbackController)

    @Provides
    @Singleton
    fun SeekMusicPosition(playbackController: MusicPlaybackController): SeekMusicPositionUseCase =
        SeekMusicPositionUseCase(playbackController)

    @Provides
    @Singleton
    fun SetMediaControllerCallback(playbackController: MusicPlaybackController): SetMediaControllerCallbackUseCase =
        SetMediaControllerCallbackUseCase(playbackController)

    @Provides
    @Singleton
    fun SetMusicShuffleEnabled(playbackController: MusicPlaybackController): SetMusicShuffleEnabledUseCase =
        SetMusicShuffleEnabledUseCase(playbackController)

    @Provides
    @Singleton
    fun SetPlayerRepeatOneEnabled(playbackController: MusicPlaybackController): SetPlayerRepeatOneEnabledUseCase =
        SetPlayerRepeatOneEnabledUseCase(playbackController)

    @Provides
    @Singleton
    fun SkipNextMusic(playbackController: MusicPlaybackController): SkipNextMusicUseCase =
        SkipNextMusicUseCase(playbackController)

    @Provides
    @Singleton
    fun SkipPreviousMusic(playbackController: MusicPlaybackController): SkipPreviousMusicUseCase =
        SkipPreviousMusicUseCase(playbackController)


    @Provides
    @Singleton
    fun provideMusicPlaybackController(application: Application): MusicPlaybackController {
        return MusicPlaybackController(application.applicationContext)
    }
}