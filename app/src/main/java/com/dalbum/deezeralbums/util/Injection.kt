package com.dalbum.deezeralbums.util

import android.content.Context
import com.dalbum.deezeralbums.data.DeezerDataSource
import com.dalbum.deezeralbums.data.DeezerRepository
import com.dalbum.deezeralbums.data.DeezerService

object Injection {

    /**
     * ¨Provides the deezer repository
     *
     * @return DeezerDataSource
     */
    fun provideDeezerRepository(context: Context): DeezerDataSource {
        return DeezerRepository.getInstance(DeezerService.getInstance(context))
    }

    /**
     * Provides scheduler provider
     *
     * @return SchedulerProvider
     */
    fun provideSchedulerProvider(): BaseSchedulerProvider =
        SchedulerProvider
}