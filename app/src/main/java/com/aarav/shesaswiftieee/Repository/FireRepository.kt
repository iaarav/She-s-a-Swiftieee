package com.aarav.shesaswiftieee.Repository

import android.util.Log
import com.aarav.shesaswiftieee.data.DataOrException
import com.aarav.shesaswiftieee.data.SWIFT
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class FireRepository {
    val db = FirebaseFirestore.getInstance()

    /*
    This function will only return the list of SWIFT data type which will be converted to
    DataOrException for the further use in the SongViewModel
    */
    suspend fun getAllSongsFromDataBase(collection: String): DataOrException<List<SWIFT>, Boolean, Exception> {


        val collectionRef = db.collection(collection)
        val dataOrException = DataOrException<List<SWIFT>, Boolean, Exception>()

        withContext(Dispatchers.IO) {
            try {
                dataOrException.loading = true
                dataOrException.data =
                    collectionRef.get().await().documents.map { documentSnapshot ->
                        documentSnapshot.toObject(SWIFT::class.java)!!
                    }
                if (!dataOrException.data.isNullOrEmpty()) dataOrException.loading = false


            } catch (
                exception: FirebaseFirestoreException
            ) {
                dataOrException.e = exception

            }
        }
        return dataOrException

    }
}