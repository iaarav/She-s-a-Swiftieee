package com.aarav.shesaswiftieee

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.aarav.shesaswiftieee.ViewModel.SongViewModel
import com.aarav.shesaswiftieee.data.SWIFT
import com.aarav.shesaswiftieee.ui.theme.ShesASwiftieeeTheme
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ShesASwiftieeeTheme {
                Log.d("SOOCK", "STARTED IG")
                val viewMode:SongViewModel by viewModels()

                Log.d("SOOCK", "onCreate: ${viewMode.songData.value.data?.toString()}")
                Log.d("SOOCK", "YO")
            }
        }
    }
}
