package com.jokomanza.mp3meta.data.model.song

data class CurrentUserMetadataXX(
    val excluded_permissions: List<String>,
    val interactions: InteractionsXX,
    val iq_by_action: IqByActionX,
    val permissions: List<String>
)