package com.dalbum.deezeralbums.ui

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

/**
 * Simple RecyclerView Adapter implementation
 */
class RecyclerViewSimpleAdapter<T, ViewHolder : RecyclerView.ViewHolder>(
    private val createVH: ((parent: ViewGroup, viewType: Int) -> ViewHolder),
    private val bindVH: ((holder: ViewHolder, item: T) -> Unit),
    private val onListUpdated: ((isEmpty: Boolean) -> Unit)? = null,
    private val clickListener: ((item: T) -> Unit)? = null
) : RecyclerView.Adapter<ViewHolder>() {

    @Suppress("MemberVisibilityCanBePrivate")
    var items: List<T> = emptyList()
        set(newValue) {
            field = newValue
            onListUpdated?.invoke(newValue.isEmpty())
            notifyDataSetChanged()
        }

    /**
     * @inheritDoc
     */
    override fun getItemCount(): Int = items.size

    /**
     * @inheritDoc
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return createVH(parent, viewType)
    }

    /**
     * @inheritDoc
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        bindVH(holder, items[position])
        clickListener?.let { listener ->
            holder.itemView.setOnClickListener { listener.invoke(items[position]) }
        }
    }
}