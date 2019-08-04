package com.dalbum.deezeralbums

import org.junit.AfterClass
import org.junit.BeforeClass
import org.junit.runner.RunWith

import org.robolectric.annotation.Config
import org.robolectric.RobolectricTestRunner

import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers

@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE)
abstract class RxTest {

    companion object {
        @BeforeClass
        @JvmStatic
        fun setUp() {
            RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
            RxJavaPlugins.setComputationSchedulerHandler { Schedulers.trampoline() }
            RxJavaPlugins.setNewThreadSchedulerHandler { Schedulers.trampoline() }
            RxAndroidPlugins.setMainThreadSchedulerHandler { Schedulers.trampoline() }
            RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
        }

        @AfterClass
        @JvmStatic
        fun tearDown() {
            RxJavaPlugins.reset()
            RxAndroidPlugins.reset()
        }
    }
}