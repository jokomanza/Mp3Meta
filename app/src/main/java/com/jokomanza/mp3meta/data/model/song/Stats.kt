package com.jokomanza.mp3meta.data.model.song

data class Stats(
    val accepted_annotations: Int,
    val concurrents: Int,
    val contributors: Int,
    val hot: Boolean,
    val iq_earners: Int,
    val pageviews: Int,
    val transcribers: Int,
    val unreviewed_annotations: Int,
    val verified_annotations: Int
)