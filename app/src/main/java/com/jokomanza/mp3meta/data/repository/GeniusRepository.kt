package com.jokomanza.mp3meta.data.repository

import com.google.gson.Gson
import com.jokomanza.mp3meta.BuildConfig
import com.jokomanza.mp3meta.data.api.GeniusApi
import com.jokomanza.mp3meta.data.model.Page
import com.jokomanza.mp3meta.data.model.search.Search
import com.jokomanza.mp3meta.data.model.song.Song
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

class GeniusRepository {

    fun provideRetrofitBuilder(gson: Gson, okHttpClient: OkHttpClient) : Retrofit.Builder = Retrofit.Builder().client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create(gson))

    fun provideRetrofitBuilder(okHttpClient: OkHttpClient) : Retrofit.Builder = Retrofit.Builder().client(okHttpClient)
        .addConverterFactory(ScalarsConverterFactory.create())

    fun provideLogLevel() : HttpLoggingInterceptor.Level  = when (BuildConfig.BUILD_TYPE.contentEquals("debug")) {
        true -> HttpLoggingInterceptor.Level.BODY
        else -> HttpLoggingInterceptor.Level.NONE
    }
    fun provideGson() = Gson()

    fun provideHttpLoggingInterceptor(level: HttpLoggingInterceptor.Level) = HttpLoggingInterceptor().setLevel(level)

    fun provideOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor)  = OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor).build()

    val api: GeniusApi by lazy {
        provideRetrofitBuilder(provideGson(), provideOkHttpClient(provideHttpLoggingInterceptor(provideLogLevel()))).baseUrl("https://api.genius.com/").build().create(GeniusApi::class.java)
    }

    val genius: GeniusApi by lazy {
        provideRetrofitBuilder(provideOkHttpClient(provideHttpLoggingInterceptor(provideLogLevel()))).baseUrl("https://genius.com/").build().create(GeniusApi::class.java)
    }


    fun searchMusic(q: String): Flow<Search> {
        return flow {
            // exectute API call and map to UI object
            val fooList = api.searchMusic(q)
            // Emit the list to the stream
            emit(fooList)
        }.flowOn(Dispatchers.IO) // Use the IO thread for this Flow
    }

    fun getSong(id: String): Flow<Song> {
        return flow {
            // exectute API call and map to UI object
            val fooList = api.getSong(id)
            // Emit the list to the stream
            emit(fooList)
        }.flowOn(Dispatchers.IO) // Use the IO thread for this Flow
    }

    fun getPage(id: String): Flow<String> {
        return flow {
            // exectute API call and map to UI object
            val fooList = genius.getPage(id)
            // Emit the list to the stream
            emit(fooList)
        }.flowOn(Dispatchers.IO) // Use the IO thread for this Flow
    }
}