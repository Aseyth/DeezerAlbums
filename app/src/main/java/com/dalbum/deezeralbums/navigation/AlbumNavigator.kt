package com.dalbum.deezeralbums.navigation

import android.content.Context
import androidx.fragment.app.FragmentManager
import com.dalbum.deezeralbums.R
import com.dalbum.deezeralbums.data.entity.AlbumDetails
import com.dalbum.deezeralbums.extension.replace
import com.dalbum.deezeralbums.ui.AlbumDetailsFragment
import com.dalbum.deezeralbums.ui.AlbumsFragment

class AlbumNavigator(
    private val context: Context
) {

    /**
     * Present the Albums fragment
     */
    fun presentAlbumsFragment(fragmentManager: FragmentManager) {
        val fragment = AlbumsFragment()
        fragmentManager.replace(fragment, R.id.root_frame, false)
    }

    /**
     * Present the Album Details fragment
     */
    fun presentAlbumDetailsFragment(fragmentManager: FragmentManager, albumDetails: AlbumDetails) {
        val fragment = AlbumDetailsFragment.createInstance(context, albumDetails)
        fragmentManager.replace(fragment, R.id.root_frame, true)
    }
}