package com.dalbum.deezeralbums.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.dalbum.deezeralbums.R
import com.dalbum.deezeralbums.util.ViewModelFactory
import com.dalbum.deezeralbums.data.entity.Album
import com.dalbum.deezeralbums.extension.snack
import com.dalbum.deezeralbums.navigation.AlbumNavigator
import kotlinx.android.synthetic.main.album_item.view.*
import kotlinx.android.synthetic.main.album_item.view.albumCover
import kotlinx.android.synthetic.main.fragment_albums.*

class AlbumsFragment : Fragment() {

    companion object {
        private const val NB_COLUMN_COVER = 3
    }

    private val navigator: AlbumNavigator by lazy { AlbumNavigator(context!!) }

    private var nextPage: String? = null

    /**
     * View model
     */
    private val albumsViewModel: AlbumsViewModel by lazy {
        ViewModelProviders.of(this, ViewModelFactory.getInstance(context!!)).get(AlbumsViewModel::class.java)
    }

    /**
     * Albums ViewHolder
     */
    private class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val albumCover: ImageView = itemView.albumCover
        val albumName: TextView = itemView.albumTitle
    }

    /**
     * Albums adapter
     */
    private var adapter = RecyclerViewSimpleAdapter<Album, ViewHolder>({ parent, _ ->
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.album_item, parent, false))
    }, { holder, album ->
        holder.albumName.text = album.title
        Glide
            .with(context!!)
            .load(album.cover)
            .placeholder(R.drawable.cover_placeholder)
            .transition(DrawableTransitionOptions.withCrossFade())
            .centerCrop()
            .into(holder.albumCover)
    }, null, {
        albumsViewModel.fetchAlbum(it.id.toString())
        loading.visibility = View.VISIBLE
    })

    private fun <T> LiveData<T>.observe(observe: (T) -> Unit) = observe(this@AlbumsFragment, Observer { observe(it) })

    /**
     * @inheritDoc
     */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_albums, container, false)
    }

    /**
     * @inheritDoc
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        albumRecycler.layoutManager = GridLayoutManager(context!!, NB_COLUMN_COVER)
        albumRecycler.addOnScrollListener(paginator())
        initObservers()
        if (albumsViewModel.paginatedList.isEmpty()) {
            albumsViewModel.fetch()
            loading.visibility = View.VISIBLE
        } else {
            adapter.items = albumsViewModel.paginatedList
            initAdapter()
        }
    }

    /**
     * @inheritDoc
     */
    override fun onDestroy() {
        super.onDestroy()
        albumsViewModel.dispose()
        albumsViewModel.paginatedList.clear()
    }

    /**
     * Initialize paginator
     */
    private fun paginator() : RecyclerViewPaginator {
       return object : RecyclerViewPaginator(albumRecycler) {
            override val isLastPage: Boolean
                get() = albumsViewModel.isLastPage

            override fun loadMore() {
                nextPage?.run {
                    albumsViewModel.loadMore(this)
                    loading.visibility = View.VISIBLE
                }
            }
        }
    }

    /**
     * Initialize adapter
     */
    private fun initAdapter() {
        albumRecycler.adapter = adapter
    }

    /**
     * Initialize the observers
     */
    private fun initObservers() {
        albumsViewModel.paginatedResponse.observe {
            it.getContentIfNotHandled()?.run {
                loading.visibility = View.GONE
                adapter.items = albumsViewModel.paginatedList
                nextPage = this.next
                initAdapter()
            }
        }
        albumsViewModel.loadMoreResponse.observe {
            it.getContentIfNotHandled()?.run {
                loading.visibility = View.GONE
                adapter.items = albumsViewModel.paginatedList
                nextPage = this.next
                adapter.notifyDataSetChanged()
            }
        }
        albumsViewModel.albumDetailsResponse.observe {
            it.getContentIfNotHandled()?.run {
                loading.visibility = View.GONE
                fragmentManager?.run {
                    if (it.peekContent().id != null) {
                        navigator.presentAlbumDetailsFragment(this, it.peekContent())
                    } else {
                        albumRecycler.snack("There is no data on this album")
                    }
                }
            }
        }
        albumsViewModel.albumsErrorResponse.observe {
            it.getContentIfNotHandled()?.run {
                loading.visibility = View.GONE
                albumRecycler.snack(it.peekContent().message!!)
            }
        }
    }
}