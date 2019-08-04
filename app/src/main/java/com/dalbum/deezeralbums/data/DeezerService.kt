package com.dalbum.deezeralbums.data

import android.content.Context
import com.dalbum.deezeralbums.data.entity.Album
import com.dalbum.deezeralbums.data.entity.AlbumDetails
import com.dalbum.deezeralbums.data.entity.PaginatedResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Url

interface DeezerService {

    companion object {
        fun getInstance(context: Context): DeezerService {
            return RetrofitConfiguration(context).create().create(DeezerService::class.java)
        }
    }

    @GET("user/{uid}/albums")
    fun getUserAlbums(@Path("uid") uid: String): Single<PaginatedResponse<Album>>

    @GET
    fun loadMore(@Url url: String): Single<PaginatedResponse<Album>>

    @GET("album/{aid}")
    fun getAlbum(@Path("aid") aid: String): Single<AlbumDetails>
}