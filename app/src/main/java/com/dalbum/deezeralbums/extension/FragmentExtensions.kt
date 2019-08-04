package com.dalbum.deezeralbums.extension

import android.os.Bundle
import android.os.Parcelable
import androidx.fragment.app.Fragment

/**
 * Add a Parcelable to fragment arguments
 */
fun <T : Parcelable> Fragment.withParcelable(key: String, value: T?) {
    value?.let {
        if (arguments == null) arguments = Bundle()
        arguments!!.putParcelable(key, it)
    }
}