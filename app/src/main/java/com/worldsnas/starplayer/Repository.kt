package com.worldsnas.starplayer

import android.util.Log
import androidx.lifecycle.LiveData
import com.worldsnas.starplayer.model.Music
import javax.inject.Inject


interface IRepository {

    fun getData(): LiveData<List<Music>>?
}

class RepositoryImp @Inject constructor() : IRepository {

    override fun getData(): LiveData<List<Music>>? {
        Log.i("Repository", " this is a test")
        return null
    }
}