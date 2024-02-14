package com.aarav.shesaswiftieee.player.service

import android.content.ComponentName
import android.content.Context
import android.net.Uri
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.common.Player
import androidx.media3.session.MediaController
import androidx.media3.session.SessionToken
import com.aarav.shesaswiftieee.data.PlayerState
import com.aarav.shesaswiftieee.data.SWIFT
import com.aarav.shesaswiftieee.data.toMusic
import com.aarav.shesaswiftieee.player.controller.PlaybackController
import com.google.common.util.concurrent.ListenableFuture
import com.google.common.util.concurrent.MoreExecutors
import javax.inject.Inject

class MusicPlaybackController @Inject constructor(context: Context) : PlaybackController {


    private var mediaControllerFuture: ListenableFuture<MediaController>

    private val mediaController: MediaController?
        get() = if (mediaControllerFuture.isDone) mediaControllerFuture.get() else null


    override var mediaControllerCallback: ((
        playerState: PlayerState,
        currentMusic: SWIFT?,
        currentPosition: Long,
        totalDuration: Long,
        isShuffleEnabled: Boolean,
        isRepeatOneEnabled: Boolean
    ) -> Unit)? =
        null

    init {
        val sessionToken =
            SessionToken(context, ComponentName(context, MusicPlaybackService::class.java))
        mediaControllerFuture = MediaController.Builder(context, sessionToken).buildAsync()
        mediaControllerFuture.addListener({ controllerListener() }, MoreExecutors.directExecutor())
    }

    private fun controllerListener() {
        mediaController?.addListener(object : Player.Listener {
            override fun onEvents(player: Player, events: Player.Events) {
                super.onEvents(player, events)
                with(player) {
                    mediaControllerCallback?.invoke(
                        playbackState.toPlayerState(isPlaying),
                        currentMediaItem?.toMusic(),
                        currentPosition.coerceAtLeast(0L),
                        duration.coerceAtLeast(0L),
                        shuffleModeEnabled,
                        repeatMode == Player.REPEAT_MODE_ONE
                    )
                }
            }
        })
    }

    private fun Int.toPlayerState(isPlaying: Boolean) =
        when (this) {
            Player.STATE_IDLE -> PlayerState.STOPPED
            Player.STATE_ENDED -> PlayerState.STOPPED
            else -> if (isPlaying) PlayerState.PLAYING else PlayerState.PAUSED
        }

    override fun addMediaItems(musics: List<SWIFT>) {
        val mediaItems = musics.map {
            MediaItem.Builder().setMediaId(it.songURL.toString()).setUri(it.songURL)
                .setMediaMetadata(
                    MediaMetadata.Builder()
                        .setTitle(it.title)
                        .setArtist(it.singer)
                        .setArtworkUri(Uri.parse(it.imageURL))
                        .setAlbumTitle(it.album)
                        .setTrackNumber(it.mediaID?.toInt())
                        .build()
                )
                .build()
        }
        mediaController?.setMediaItems(mediaItems)
    }

    override fun play(mediaItemIndex: Int) {
        mediaController?.apply {
            seekToDefaultPosition(mediaItemIndex)
            playWhenReady = true
            prepare()
        }
    }

    override fun resume() {
        mediaController?.play()
    }

    override fun pause() {
        mediaController?.pause()
    }

    override fun seekTo(position: Long) {
        mediaController?.seekTo(position)
    }

    override fun skipNext() {
        mediaController?.seekToNext()
    }

    override fun skipPrevious() {
        mediaController?.seekToPrevious()
    }

    override fun setShuffleModeEnabled(isEnabled: Boolean) {
        mediaController?.shuffleModeEnabled = isEnabled
    }

    override fun setRepeatOneEnabled(isEnabled: Boolean) {
        mediaController?.repeatMode = if (isEnabled) {
            Player.REPEAT_MODE_ONE
        } else {
            Player.REPEAT_MODE_OFF
        }
    }

    override fun getCurrentPosition(): Long = mediaController?.currentPosition ?: 0L

    override fun destroy() {
        MediaController.releaseFuture(mediaControllerFuture)
        mediaControllerCallback = null
    }
}