package com.jokomanza.mp3meta

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.*
import com.jokomanza.mp3meta.data.model.search.Search
import com.jokomanza.mp3meta.data.model.song.Song
import com.jokomanza.mp3meta.data.repository.GeniusRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.safety.Whitelist


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

    @InternalCoroutinesApi
    fun getPage(id: String): LiveData<String> = liveData {
        repository.getPage(id)
            .onStart { /* emit loading state */ }
            .catch { exception ->
                Log.d("Result", "getPage: Error $exception")
            }
            .collect {
                val doc = Jsoup.parse(it)
                doc.select("div.lyrics")
                doc.outputSettings(Document.OutputSettings().prettyPrint(false));//makes html() preserve linebreaks and spacing
                doc.select("br").append("\\n");
                doc.select("p").prepend("\\n\\n");
                val s = doc.html().replace("\\\\n", "\n");
                emit(Jsoup.clean(s, "", Whitelist.none(),  Document.OutputSettings().prettyPrint(false)))
            }
    }

    fun getWebPage(url: String): LiveData<String> = liveData {
        viewModelScope.launch (Dispatchers.IO) {
            val doc = Jsoup.connect(url).get();
            val result = doc.select(".Lyrics__Container")
            emit(result.toString())
        }
    }
}