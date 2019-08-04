package com.dalbum.deezeralbums.data.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AlbumDetails(
    val id: Int?,
    val title: String,
    val link: String,
    val cover: String,
    val cover_small: String,
    val cover_medium: String,
    val cover_big: String,
    val cover_xl: String,
    val label: String,
    val duration: Int,
    val release_date: String,
    val tracks: Tracks
) : Parcelable