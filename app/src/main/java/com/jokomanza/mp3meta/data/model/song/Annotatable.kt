package com.jokomanza.mp3meta.data.model.song

data class Annotatable(
    val api_path: String,
    val client_timestamps: ClientTimestamps,
    val context: String,
    val id: Int,
    val image_url: String,
    val link_title: String,
    val title: String,
    val type: String,
    val url: String
)