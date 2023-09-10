package com.aarav.shesaswiftieee.Repository

import android.util.Log
import com.aarav.shesaswiftieee.data.SWIFT
import com.google.firebase.firestore.FirebaseFirestore

class FireRepository {
    val db = FirebaseFirestore.getInstance()

    suspend fun getAllSongsFromDataBase(collection:String):MutableList<SWIFT>{
        val collectionRef = db.collection(collection)
        val song = mutableListOf<SWIFT>()
        collectionRef.get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val title = document.getString("title")
                    val mediaID= document.getString("mediaID")

                    song.add(SWIFT(title,mediaID))

                    Log.d("SOOCK", "$title is id $mediaID." )

                }
            }
            .addOnFailureListener { exception ->
                Log.e("SOOCK", "FIRESTORE ERROR",exception )
            }

        return song
    }
}