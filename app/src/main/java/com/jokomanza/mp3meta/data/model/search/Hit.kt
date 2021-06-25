package com.jokomanza.mp3meta.data.model.search

data class Hit(
    val highlights: List<Any>,
    val index: String,
    val result: Result,
    val type: String
)