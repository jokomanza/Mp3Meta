package com.jokomanza.mp3meta.data.model.song

data class Annotation(
    val api_path: String,
    val authors: List<Author>,
    val body: Body,
    val comment_count: Int,
    val community: Boolean,
    val cosigned_by: List<Any>,
    val current_user_metadata: CurrentUserMetadataXX,
    val custom_preview: Any,
    val has_voters: Boolean,
    val id: Int,
    val pinned: Boolean,
    val rejection_comment: Any,
    val share_url: String,
    val source: Any,
    val state: String,
    val url: String,
    val verified: Boolean,
    val verified_by: Any,
    val votes_total: Int
)