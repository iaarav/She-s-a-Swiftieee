package com.aarav.shesaswiftieee.di

import com.aarav.shesaswiftieee.ui.viewModel.musicPlayer.MusicViewModel
import com.aarav.shesaswiftieee.ui.viewModel.shared.SharedViewModel
import com.aarav.shesaswiftieee.ui.viewModel.song.SongViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { SharedViewModel(get(), get(), get()) }
    viewModel { SongViewModel(get(), get(), get(), get(), get()) }
    viewModel { MusicViewModel(get(), get(), get(), get(), get(), get(), get()) }
}