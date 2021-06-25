package com.jokomanza.mp3meta.data.model.song

data class CurrentUserMetadata(
    val excluded_permissions: List<String>,
    val interactions: Interactions,
    val iq_by_action: IqByAction,
    val permissions: List<String>,
    val relationships: Relationships
)