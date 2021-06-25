package com.jokomanza.mp3meta.data.model.song

data class DescriptionAnnotation(
    val _type: String,
    val annotatable: Annotatable,
    val annotations: List<Annotation>,
    val annotator_id: Int,
    val annotator_login: String,
    val api_path: String,
    val classification: String,
    val fragment: String,
    val id: Int,
    val is_description: Boolean,
    val path: String,
    val range: Range,
    val song_id: Int,
    val url: String,
    val verified_annotator_ids: List<Any>
)