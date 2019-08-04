package com.dalbum.deezeralbums.data.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Track(
    val id: Int,
    val readable: Boolean,
    val title: String,
    val title_short: String,
    val title_version: String,
    val link: String,
    val duration: Int,
    val rank: String,
    val explicit_lyrics: Boolean,
    val explicit_content_lyrics: Int,
    val explicit_content_cover: Int,
    val preview: String,
    val artist: Artist,
    val type: String
): Parcelable