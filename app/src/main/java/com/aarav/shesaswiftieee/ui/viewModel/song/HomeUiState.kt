package com.aarav.shesaswiftieee.ui.viewModel.song

import com.aarav.shesaswiftieee.data.SWIFT

data class HomeUiState(
    val loading: Boolean? = false,
    val musics: List<SWIFT>? = emptyList(),
    val selectedMusic: SWIFT? = null,
    val errorMessage: String? = null
)
