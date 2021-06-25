package com.jokomanza.mp3meta.data.model.song

data class User(
    val api_path: String,
    val avatar: Avatar,
    val current_user_metadata: CurrentUserMetadataX,
    val header_image_url: String,
    val human_readable_role_for_display: Any,
    val id: Int,
    val iq: Int,
    val login: String,
    val name: String,
    val role_for_display: Any,
    val url: String
)