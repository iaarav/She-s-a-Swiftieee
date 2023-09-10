package com.aarav.shesaswiftieee.ViewModel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aarav.shesaswiftieee.Repository.FireRepository
import com.aarav.shesaswiftieee.data.DataOrException
import com.aarav.shesaswiftieee.data.SWIFT
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SongViewModel @Inject constructor(private val fireRepository: FireRepository) : ViewModel() {
    val songData: MutableState<DataOrException<MutableList<SWIFT>, Boolean, Exception>> =
        mutableStateOf(
            DataOrException(
                mutableListOf<SWIFT>(), true , Exception("")
            )
        )

    init {
        viewModelScope.launch(Dispatchers.IO) {
            getAllSongs()
        }
    }

    private fun getAllSongs() {
        viewModelScope.launch {
            songData.value.loading=true
            songData.value.data=fireRepository.getAllSongsFromDataBase("SWIFT")
            if (!songData.value.data.isNullOrEmpty()) songData.value.loading = false

            Log.d("SOOCK", "getAllBooksFromDatabase: ${songData.value.data?.toList().toString()}")
        }
    }
}