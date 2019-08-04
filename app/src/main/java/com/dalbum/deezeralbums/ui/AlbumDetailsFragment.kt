package com.dalbum.deezeralbums.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.dalbum.deezeralbums.R
import com.dalbum.deezeralbums.data.entity.AlbumDetails
import com.dalbum.deezeralbums.data.entity.Track
import com.dalbum.deezeralbums.extension.withParcelable
import kotlinx.android.synthetic.main.fragment_album_details.*
import kotlinx.android.synthetic.main.track_item.view.*

class AlbumDetailsFragment : Fragment() {

    companion object {

        private const val ARGUMENT_ALBUM = "ARGUMENT_ALBUM"

        /**
         * Create new instance
         */
        fun createInstance(context: Context, albumDetails: AlbumDetails): AlbumDetailsFragment {
            return instantiate(context, AlbumDetailsFragment::class.java.name).apply {
                withParcelable(ARGUMENT_ALBUM, albumDetails)
            } as AlbumDetailsFragment
        }

        /**
         * Extract Album from arguments
         */
        private fun extractFromIntent(fragment: AlbumDetailsFragment): AlbumDetails {
            return fragment.arguments!!.getParcelable(ARGUMENT_ALBUM) as AlbumDetails
        }
    }

    /**
     * AlbumDetails ViewHolder
     */
    private class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val trackTitle: TextView = itemView.trackTitle
        val trackArtist: TextView = itemView.trackArtist
    }

    /**
     * AlbumDetails adapter
     */
    private var adapter = RecyclerViewSimpleAdapter<Track, ViewHolder>({ parent, _ ->
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.track_item, parent, false))
    }, { holder, track ->
        holder.trackTitle.text = track.title
        holder.trackArtist.text = track.artist.name
    })

    private lateinit var albumDetails: AlbumDetails

    /**
     * @inheritDoc
     */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_album_details, container, false)
    }

    /**
     * @inheritDoc
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        albumDetails = extractFromIntent(this)
        initRecycler()
        populateData()
    }

    /**
     * Initialize the recycler view
     */
    private fun initRecycler() {
        albumDetailsRecycler.layoutManager = LinearLayoutManager(context!!)
        adapter.items = albumDetails.tracks.data
        albumDetailsRecycler.adapter = adapter
    }

    /**
     * Populate the view with the extracted data
     */
    private fun populateData() {
        Glide
            .with(context!!)
            .load(albumDetails.cover)
            .placeholder(R.drawable.cover_placeholder)
            .transition(DrawableTransitionOptions.withCrossFade())
            .centerCrop()
            .into(albumDetailsCover)

        albumDetailsTitle.text = albumDetails.title
        albumDetailsArtist.text = getString(R.string.albumDetailsArtist, albumDetails.tracks.data[0].artist.name)
    }
}