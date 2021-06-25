package com.jokomanza.mp3meta

import android.util.Log
import androidx.lifecycle.*
import com.jokomanza.mp3meta.data.model.search.Search
import com.jokomanza.mp3meta.data.model.song.Song
import com.jokomanza.mp3meta.data.repository.GeniusRepository
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart

class MainActivityViewModel : ViewModel() {

    val repository = GeniusRepository()

    @InternalCoroutinesApi
    fun search(q: String): LiveData<Search> = liveData {
        repository.searchMusic(q)
            .onStart { /* emit loading state */ }
            .catch { exception -> /* emit error state */ }
            .collect {
                emit(it)
            }
    }

    @InternalCoroutinesApi
    fun getSong(id: String): LiveData<Song> = liveData {
        repository.getSong(id)
            .onStart { /* emit loading state */ }
            .catch { exception ->
                Log.d("Result", "getSong: Error $exception")
            }
            .collect {
                emit(it)
            }
    }
}