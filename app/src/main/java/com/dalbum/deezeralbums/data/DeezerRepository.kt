package com.dalbum.deezeralbums.data

import com.dalbum.deezeralbums.util.SingletonHolderSingleArg
import com.dalbum.deezeralbums.data.entity.Album
import com.dalbum.deezeralbums.data.entity.AlbumDetails
import com.dalbum.deezeralbums.data.entity.PaginatedResponse
import io.reactivex.Single

class DeezerRepository(
    private val service: DeezerService
) : DeezerDataSource {

    companion object : SingletonHolderSingleArg<DeezerRepository, DeezerService>(::DeezerRepository)

    /**
     * @inheritDoc
     */
    override fun getUserAlbums(uid: String): Single<PaginatedResponse<Album>> {
        return service.getUserAlbums(uid)
    }

    /**
     * @inheritDoc
     */
    override fun loadMore(url: String): Single<PaginatedResponse<Album>> {
        return service.loadMore(url)
    }

    /**
     * @ineritDoc
     */
    override fun getAlbum(aid: String): Single<AlbumDetails> {
        return service.getAlbum(aid)
    }
}