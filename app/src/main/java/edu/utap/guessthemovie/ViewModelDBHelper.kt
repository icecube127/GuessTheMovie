package edu.utap.guessthemovie

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.tasks.Tasks.await
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import edu.utap.guessthemovie.model.ScoreMeta

class ViewModelDBHelper {
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val rootCollection = "allScore"
    var score = 0

    fun fetchScoreMeta(notesList: MutableLiveData<List<ScoreMeta>>){
        db.collection(rootCollection)
            .get()
            .addOnSuccessListener { result ->
                Log.d(javaClass.simpleName, "allNotes fetch ${result!!.documents.size}")
                // NB: This is done on a background thread
                notesList.postValue(result.documents.mapNotNull {
                    it.toObject(ScoreMeta::class.java)
                })
            }
            .addOnFailureListener {
                Log.d(javaClass.simpleName, "allNotes fetch FAILED ", it)
            }
    }

    fun updateScoreMeta(scoreMeta: ScoreMeta,
                        notesList: MutableLiveData<List<ScoreMeta>>){
        db.collection(rootCollection)
            .document(scoreMeta.ownerUid)
            .set(scoreMeta)
            //EEE // XXX Writing a note
            .addOnSuccessListener {
                Log.d(
                    javaClass.simpleName,
                    "ScoreMeta update  id: ${scoreMeta.firestoreID}"
                )
                //fetchMyScoreMeta()
            }
            .addOnFailureListener { e ->
                Log.d(javaClass.simpleName, "ScoreMeta update FAILED id: ${scoreMeta.firestoreID}")
                Log.w(javaClass.simpleName, "Error ", e)
            }
    }
}