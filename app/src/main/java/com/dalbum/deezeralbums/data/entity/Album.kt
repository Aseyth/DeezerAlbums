package com.dalbum.deezeralbums.data.entity

data class Album(
    val id: Int,
    val title: String,
    val link: String,
    val cover: String,
    val cover_small: String,
    val cover_medium: String,
    val cover_big: String,
    val cover_xl: String,
    val nb_tracks: Int,
    val release_date: String,
    val record_type: String,
    val available: Boolean,
    val tracklist: String,
    val explicit_lyrics: Boolean,
    val time_add: Int,
    val artist: Artist,
    val type: String
)