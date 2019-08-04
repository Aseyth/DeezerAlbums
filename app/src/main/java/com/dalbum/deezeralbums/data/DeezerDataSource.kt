package com.dalbum.deezeralbums.data

import com.dalbum.deezeralbums.data.entity.Album
import com.dalbum.deezeralbums.data.entity.AlbumDetails
import com.dalbum.deezeralbums.data.entity.PaginatedResponse
import io.reactivex.Single

interface DeezerDataSource {

    /**
     * Get albums from server for the user id given as parameter
     */
    fun getUserAlbums(uid: String): Single<PaginatedResponse<Album>>

    /**
     * Get albums from the server using an URL as parameter
     */
    fun loadMore(url: String): Single<PaginatedResponse<Album>>

    /**
     * Get album details for the id given as parameter
     */
    fun getAlbum(aid: String): Single<AlbumDetails>
}