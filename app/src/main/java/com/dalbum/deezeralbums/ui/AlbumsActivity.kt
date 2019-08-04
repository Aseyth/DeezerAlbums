package com.dalbum.deezeralbums.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dalbum.deezeralbums.R
import com.dalbum.deezeralbums.navigation.AlbumNavigator

class AlbumsActivity : AppCompatActivity() {

    /**
     * Album navigator
     */
    private val navigator: AlbumNavigator by lazy { AlbumNavigator(this) }

    /**
     * @inheritDoc
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_albums)
        navigator.presentAlbumsFragment(supportFragmentManager)
    }
}