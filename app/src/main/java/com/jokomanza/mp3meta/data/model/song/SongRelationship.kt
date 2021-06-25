package com.jokomanza.mp3meta.data.model.song

data class SongRelationship(
    val relationship_type: String,
    val songs: List<Any>,
    val type: String
)