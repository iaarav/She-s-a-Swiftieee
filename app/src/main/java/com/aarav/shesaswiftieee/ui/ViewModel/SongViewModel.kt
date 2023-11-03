package com.aarav.shesaswiftieee.ui.ViewModel

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
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SongViewModel @Inject constructor(private val fireRepository: FireRepository) : ViewModel() {
    val songData: MutableState<DataOrException<List<SWIFT>, Boolean, Exception>> =
        mutableStateOf(DataOrException(listOf(), true, Exception("")))

    init {
        viewModelScope.launch(Dispatchers.IO) {
            getAllSongs()
        }
    }

    private fun getAllSongs() {
        viewModelScope.launch(Dispatchers.Main) {
            try {
                songData.value.loading = true // Set loading to true before the request
                val result = fireRepository.getAllSongsFromDataBase("SWIFT")
                    songData.value = result
            } catch (exception: Exception) {
                Log.e("SOOCK", "look mom the view model is faulty ", exception)
            } finally {
                songData.value.loading = false // Set loading to false after the request
            }
        }
    }
}