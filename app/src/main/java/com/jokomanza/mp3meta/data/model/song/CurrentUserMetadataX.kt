package com.jokomanza.mp3meta.data.model.song

data class CurrentUserMetadataX(
    val excluded_permissions: List<Any>,
    val interactions: InteractionsX,
    val permissions: List<String>
)