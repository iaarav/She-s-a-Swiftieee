package com.aarav.shesaswiftieee

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.aarav.shesaswiftieee.ViewModel.SongViewModel
import com.aarav.shesaswiftieee.data.SWIFT
import com.aarav.shesaswiftieee.ui.theme.ShesASwiftieeeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ShesASwiftieeeTheme {
                val viewModel: SongViewModel by viewModels()
                display(viewModel)
            }
        }
    }

    @Composable
    private fun display(viewModel: SongViewModel) {
        val daata = viewModel.songData.value
        val songlisst = mutableListOf<SWIFT>()
        if (daata.loading == true) {
            CircularProgressIndicator()
            Log.d("SOOCK", "onCreate: LOADING LOADING LOADIND BAKA")
        } else {
            // Data has loaded, log it
            daata.data?.forEach { obj ->
                Log.d(
                    "SOOCK",
                    "${obj.title} is mediaId ${obj.mediaID} of the album ${obj.album} which is sung by the famous one and only: ${obj.singer}"
                )
                songlisst.add(obj)
            }
            LazyVerticalGrid(columns = GridCells.Fixed(2)) {
                items(songlisst) { song ->
                    SongItem(song)
                }
            }
        }
    }
}
@Composable
fun SongItem(song: SWIFT) {
    // Composable to display a single song item
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = "${song.mediaID}")
            Text(text = "${song.title}")
            Text(text = "${song.album}")
            Text(text = "${song.singer}")
        }
    }
}
