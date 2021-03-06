package com.jokomanza.mp3meta.data.api

import com.jokomanza.mp3meta.data.model.Page
import com.jokomanza.mp3meta.data.model.search.Search
import com.jokomanza.mp3meta.data.model.song.Song
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface GeniusApi {

    @Headers(
        "Authorization: Bearer alXXDbPZtK1m2RrZ8I4k2Hn8Ahsd0Gh_o076HYvcdlBvmc0ULL1H8Z8xRlew5qaG"
    )
    @GET("search")
    suspend fun searchMusic(@Query("q") q: String) : Search

    @Headers(
        "Authorization: Bearer alXXDbPZtK1m2RrZ8I4k2Hn8Ahsd0Gh_o076HYvcdlBvmc0ULL1H8Z8xRlew5qaG"
    )
    @GET("songs/{id}")
    suspend fun getSong(@Path("id") id: String) : Song

    @Headers(
        "User-Agent: Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/89.0.4389.128 Mobile Safari/537.36"
    )
    @GET("{id}")
    suspend fun getPage(@Path("id") id: String) : String
}