package com.aarav.shesaswiftieee.di

import android.content.Context
import androidx.media3.common.AudioAttributes
import androidx.media3.common.C
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.trackselection.DefaultTrackSelector
import androidx.media3.session.MediaSession
import com.aarav.shesaswiftieee.Repository.FireRepository
import com.aarav.shesaswiftieee.player.notification.NotificationManager
import com.aarav.shesaswiftieee.player.service.AudioServiceHandler
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.DelicateCoroutinesApi
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideFireRepository() = FireRepository()

    @Provides
    @Singleton
    fun provideAudioAttributes(): AudioAttributes =
        AudioAttributes.Builder().setContentType(C.AUDIO_CONTENT_TYPE_MUSIC).setUsage(C.USAGE_MEDIA)
            .build()

    @Provides
    @Singleton
    @UnstableApi
    fun provideExoPlayer(
        @ApplicationContext context: Context, audioAttributes: AudioAttributes
    ): ExoPlayer = ExoPlayer.Builder(context).setAudioAttributes(audioAttributes, true)
        .setHandleAudioBecomingNoisy(true).setTrackSelector(DefaultTrackSelector(context)).build()

    @Provides
    @Singleton
    fun provideMediaSession(@ApplicationContext context: Context, player: ExoPlayer): MediaSession =
        MediaSession.Builder(context, player).build()

    @Provides
    @Singleton
    fun provideNotificationManager(
        @ApplicationContext context: Context, player: ExoPlayer
    ): NotificationManager = NotificationManager(
        context, player
    )

    @OptIn(DelicateCoroutinesApi::class)
    @Provides
    @Singleton
    fun provideServiceHandler(player: ExoPlayer): AudioServiceHandler = AudioServiceHandler(player)


}