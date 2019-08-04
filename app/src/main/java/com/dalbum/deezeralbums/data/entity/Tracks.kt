package com.dalbum.deezeralbums.data.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Tracks(
    val data: List<Track>
) : Parcelable