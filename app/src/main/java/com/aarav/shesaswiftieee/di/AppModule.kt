package com.aarav.shesaswiftieee.di

import android.content.Context
import com.aarav.shesaswiftieee.player.service.MusicPlaybackController
import com.aarav.shesaswiftieee.repository.FireRepository
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
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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
    fun AddMediaItems(@ApplicationContext context: Context): AddMediaItemsUseCase = AddMediaItemsUseCase(MusicPlaybackController(context))

    @Provides
    @Singleton
    fun DestroyMediaController(@ApplicationContext context: Context): DestroyMediaControllerUseCase =
        DestroyMediaControllerUseCase(MusicPlaybackController(context))

    @Provides
    @Singleton
    fun GetCurrentMusicPosition(@ApplicationContext context: Context): GetCurrentMusicPositionUseCase =
        GetCurrentMusicPositionUseCase(MusicPlaybackController(context))


    @Provides
    @Singleton
    fun PauseMusic(@ApplicationContext context: Context): PauseMusicUseCase = PauseMusicUseCase(MusicPlaybackController(context))

    @Provides
    @Singleton
    fun PlayMusic(@ApplicationContext context: Context): PlayMusicUseCase = PlayMusicUseCase(MusicPlaybackController(context))

    @Provides
    @Singleton
    fun ResumeMusic(@ApplicationContext context: Context): ResumeMusicUseCase = ResumeMusicUseCase(MusicPlaybackController(context))

    @Provides
    @Singleton
    fun SeekMusicPosition(@ApplicationContext context: Context): SeekMusicPositionUseCase = SeekMusicPositionUseCase(MusicPlaybackController(context))

    @Provides
    @Singleton
    fun SetMediaControllerCallback(@ApplicationContext context: Context): SetMediaControllerCallbackUseCase =
        SetMediaControllerCallbackUseCase(MusicPlaybackController(context))

    @Provides
    @Singleton
    fun SetMusicShuffleEnabled(@ApplicationContext context: Context): SetMusicShuffleEnabledUseCase =
        SetMusicShuffleEnabledUseCase(MusicPlaybackController(context))

    @Provides
    @Singleton
    fun SetPlayerRepeatOneEnabled(@ApplicationContext context: Context): SetPlayerRepeatOneEnabledUseCase =
        SetPlayerRepeatOneEnabledUseCase(MusicPlaybackController(context))

    @Provides
    @Singleton
    fun SkipNextMusic(@ApplicationContext context: Context): SkipNextMusicUseCase = SkipNextMusicUseCase(MusicPlaybackController(context))

    @Provides
    @Singleton
    fun SkipPreviousMusic(@ApplicationContext context: Context): SkipPreviousMusicUseCase = SkipPreviousMusicUseCase(MusicPlaybackController(context))


}