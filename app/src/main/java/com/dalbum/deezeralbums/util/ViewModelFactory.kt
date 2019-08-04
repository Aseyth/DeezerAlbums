package com.dalbum.deezeralbums.util

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dalbum.deezeralbums.ui.AlbumsViewModel
import java.lang.IllegalArgumentException


/**
 * ViewModel factory
 *
 * Create ViewModels while injecting required dependencies
 */
class ViewModelFactory private constructor(
    private val applicationContext: Context
): ViewModelProvider.Factory {
    companion object : SingletonHolderSingleArg<ViewModelFactory, Context>(::ViewModelFactory)

    /**
     * @inheritDoc
     */
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        when (modelClass) {
            AlbumsViewModel::class.java -> {
              return AlbumsViewModel(
                  Injection.provideDeezerRepository(applicationContext),
                  Injection.provideSchedulerProvider()
              ) as T
            }
        }
        throw  IllegalArgumentException("Unknown model class $modelClass")
    }

}