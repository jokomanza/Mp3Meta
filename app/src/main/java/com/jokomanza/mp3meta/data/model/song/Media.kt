package com.jokomanza.mp3meta.data.model.song

data class Media(
    val attribution: String,
    val provider: String,
    val start: Int,
    val type: String,
    val url: String
)