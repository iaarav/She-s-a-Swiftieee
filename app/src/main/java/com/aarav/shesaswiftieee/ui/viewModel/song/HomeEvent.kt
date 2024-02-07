package com.aarav.shesaswiftieee.ui.viewModel.song

import com.aarav.shesaswiftieee.data.SWIFT

sealed class HomeEvent {
    object PlayMusic: HomeEvent()
    object PauseMusic: HomeEvent()
    object ResumeMusic: HomeEvent()

    data class OnMusicSelected(val selectedMusic: SWIFT) : HomeEvent()
}