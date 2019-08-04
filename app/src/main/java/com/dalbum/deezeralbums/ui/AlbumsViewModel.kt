package com.dalbum.deezeralbums.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dalbum.deezeralbums.util.BaseSchedulerProvider
import com.dalbum.deezeralbums.util.Event
import com.dalbum.deezeralbums.data.DeezerDataSource
import com.dalbum.deezeralbums.data.entity.Album
import com.dalbum.deezeralbums.data.entity.AlbumDetails
import com.dalbum.deezeralbums.data.entity.PaginatedResponse
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign

class AlbumsViewModel(
    private val repository: DeezerDataSource,
    private val scheduler: BaseSchedulerProvider
) : ViewModel() {

    /**
     * Disposable bag
     */
    private val disposables = CompositeDisposable()

    /**
     * LiveData that would be notified of server responses when fetching albums
     */
    private var mutablePaginatedResponse = MutableLiveData<Event<PaginatedResponse<Album>>>()

    /**
     * LiveData exposed to Views to listen to data when fetching albums
     */
    val paginatedResponse: LiveData<Event<PaginatedResponse<Album>>> = mutablePaginatedResponse

    /**
     * LiveData that would be notified of server responses when fetching next albums page
     */
    private var mutableLoadMoreResponse = MutableLiveData<Event<PaginatedResponse<Album>>>()

    /**
     * LiveData exposed to Views to listen to data when fetching next albums page
     */
    val loadMoreResponse: LiveData<Event<PaginatedResponse<Album>>> = mutableLoadMoreResponse

    /**
     * LiveData that would be notified of the server responses when fetching album details data
     */
    val mutableAlbumDetailsResponse = MutableLiveData<Event<AlbumDetails>>()

    /**
     * LiveData exposed to Views to listen to data when fetching album details
     */
    val albumDetailsResponse: LiveData<Event<AlbumDetails>> = mutableAlbumDetailsResponse

    /**
     * LiveData that would be notified of errors on data when fetching albums
     */
    private var mutableAlbumsError = MutableLiveData<Event<Throwable>>()

    /**
     * LiveData exposed to Views to listen to errors on data when fetching albums
     */
    val albumsErrorResponse: LiveData<Event<Throwable>> = mutableAlbumsError

    /**
     * Last page
     */
    var isLastPage: Boolean = false

    /**
     * List of albums
     */
    var paginatedList: MutableList<Album> = arrayListOf()

    /**
     * Fetch the list of albums
     */
    fun fetch() {
        disposables += repository.getUserAlbums("2529")
            .subscribeOn(scheduler.io())
            .observeOn(scheduler.ui())
            .subscribe({ remotePaginatedResponse ->
                if (remotePaginatedResponse.next.isNullOrEmpty()) {
                    isLastPage = true
                }
                paginatedList.addAll(remotePaginatedResponse.data)
                mutablePaginatedResponse.postValue(Event(remotePaginatedResponse))
            }, {
                mutableAlbumsError.postValue(Event(it))
            })
    }

    /**
     * Fetch next page of albums
     */
    fun loadMore(url: String) {
        disposables += repository.loadMore(url)
            .subscribeOn(scheduler.io())
            .observeOn(scheduler.ui())
            .subscribe({ remotePaginatedResponse ->
                if (remotePaginatedResponse.next.isNullOrEmpty()) {
                    isLastPage = true
                }
                paginatedList.addAll(remotePaginatedResponse.data)
                mutableLoadMoreResponse.postValue(Event(remotePaginatedResponse))
            }, {
                mutableAlbumsError.postValue(Event(it))
            })
    }

    /**
     * Fetch the album details
     */
    fun fetchAlbum(aid: String) {
        disposables += repository.getAlbum(aid)
            .subscribeOn(scheduler.io())
            .observeOn(scheduler.ui())
            .subscribe({ album ->
                mutableAlbumDetailsResponse.postValue(Event(album))
            }, {
                mutableAlbumsError.postValue(Event(it))
            })
    }

    /**
     * Dispose
     */
    fun dispose() {
        disposables.dispose()
    }
}