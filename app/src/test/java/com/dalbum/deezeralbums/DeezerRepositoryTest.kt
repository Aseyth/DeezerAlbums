package com.dalbum.deezeralbums

import androidx.test.platform.app.InstrumentationRegistry
import com.dalbum.deezeralbums.data.DeezerRepository
import com.dalbum.deezeralbums.data.DeezerService
import com.dalbum.deezeralbums.data.RetrofitConfiguration
import com.dalbum.deezeralbums.data.entity.Album
import com.dalbum.deezeralbums.data.entity.AlbumDetails
import com.dalbum.deezeralbums.data.entity.PaginatedResponse
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import io.reactivex.Single
import io.reactivex.observers.TestObserver
import org.junit.Test
import retrofit2.mock.BehaviorDelegate
import retrofit2.mock.MockRetrofit
import retrofit2.mock.NetworkBehavior
import java.lang.reflect.Type
import java.util.concurrent.TimeUnit

class DeezerRepositoryTest : RxTest() {

    private class MockDeezerService(
        private val delegate: BehaviorDelegate<DeezerService>
    ) : DeezerService {
        private val gson: Gson by lazy {
            GsonBuilder()
                .setLenient()
                .disableHtmlEscaping()
                .create()
        }

        val paginatedAlbum by lazy {
            val reader = javaClass.classLoader!!.getResourceAsStream("json/albums.json").reader()
            val type: Type = object : TypeToken<PaginatedResponse<Album>>() {}.type
            gson.fromJson(reader, type) as PaginatedResponse<Album>
        }

        val loadMoreResponse by lazy {
            val reader = javaClass.classLoader!!.getResourceAsStream("json/load_more.json").reader()
            val type: Type = object : TypeToken<PaginatedResponse<Album>>() {}.type
            gson.fromJson(reader, type) as PaginatedResponse<Album>
        }

        val albumDetails by lazy {
            val reader = javaClass.classLoader!!.getResourceAsStream("json/album_details.json").reader()
            val type: Type = object : TypeToken<AlbumDetails>() {}.type
            gson.fromJson(reader, type) as AlbumDetails
        }

        override fun getUserAlbums(uid: String): Single<PaginatedResponse<Album>> {
            return delegate.returningResponse(paginatedAlbum).getUserAlbums(uid)
        }

        override fun loadMore(url: String): Single<PaginatedResponse<Album>> {
            return delegate.returningResponse(loadMoreResponse).loadMore(url)
        }

        override fun getAlbum(aid: String): Single<AlbumDetails> {
            return delegate.returningResponse(albumDetails).getAlbum("49201")
        }
    }

    private val service: MockDeezerService by lazy {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val retrofit = RetrofitConfiguration.getInstance(context).create()
        val behavior: NetworkBehavior = NetworkBehavior.create()
        behavior.setDelay(0, TimeUnit.MILLISECONDS)
        behavior.setVariancePercent(0)
        behavior.setErrorPercent(0)
        behavior.setFailurePercent(0)
        val delegate = MockRetrofit.Builder(retrofit)
            .networkBehavior(behavior)
            .build()
            .create(DeezerService::class.java)
        MockDeezerService(delegate)
    }

    private val deezerRepository: DeezerRepository by lazy {
        DeezerRepository.getInstance(service)
    }

    @Test
    fun testGetUserAlbums() {
        val testObserver = TestObserver<PaginatedResponse<Album>>()
        deezerRepository.getUserAlbums("2529").subscribe(testObserver)
        testObserver.assertValue(service.paginatedAlbum)
    }

    @Test
    fun testLoadMore() {
        val testObserver = TestObserver<PaginatedResponse<Album>>()
        deezerRepository.loadMore("http://api.deezer.com/2.0/user/2529/albums?index=25").subscribe(testObserver)
        testObserver.assertValue(service.loadMoreResponse)
    }

    @Test
    fun getAlbum() {
        val testObserver = TestObserver<AlbumDetails>()
        deezerRepository.getAlbum("49201").subscribe(testObserver)
        testObserver.assertValue(service.albumDetails)
    }
}