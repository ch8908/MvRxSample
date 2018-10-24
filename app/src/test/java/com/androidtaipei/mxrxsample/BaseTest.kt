// This suppression may be due to a bug in Detekt because this is an abstract class.
@file:Suppress("UtilityClassWithPublicConstructor")
package com.androidtaipei.mxrxsample

import android.os.Bundle
import android.os.Parcelable
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.airbnb.mvrx.MvRx
import io.reactivex.Scheduler
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.annotations.NonNull
import io.reactivex.disposables.Disposable
import io.reactivex.exceptions.CompositeException
import io.reactivex.internal.schedulers.ExecutorScheduler
import io.reactivex.plugins.RxJavaPlugins
import org.junit.BeforeClass
import org.junit.Ignore
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.android.controller.ActivityController
import org.robolectric.shadows.ShadowLog
import java.util.concurrent.Executor
import java.util.concurrent.TimeUnit

@RunWith(RobolectricTestRunner::class)
@Ignore
abstract class BaseTest {
    companion object {
        @JvmStatic
        @BeforeClass
        fun classSetUp() {
            ShadowLog.stream = System.out
            RxAndroidPlugins.reset()
            RxJavaPlugins.reset()
            val immediate = object : Scheduler() {
                // this prevents StackOverflowErrors when scheduling with a delay
                override fun scheduleDirect(@NonNull run: Runnable, delay: Long, @NonNull unit: TimeUnit): Disposable = super.scheduleDirect(run, 0, unit)

                override fun createWorker(): Scheduler.Worker = ExecutorScheduler.ExecutorWorker(Executor { it.run() })
            }
            RxJavaPlugins.setNewThreadSchedulerHandler { immediate }
            RxJavaPlugins.setComputationSchedulerHandler { immediate }
            RxJavaPlugins.setInitIoSchedulerHandler { immediate }
            RxJavaPlugins.setSingleSchedulerHandler { immediate }
            // This is necessary to prevent rxjava from swallowing errors
            // https://github.com/ReactiveX/RxJava/issues/5234
            Thread.setDefaultUncaughtExceptionHandler { _, e ->
                if (e is CompositeException && e.exceptions.size == 1) throw e.exceptions[0]
                throw e
            }
            RxAndroidPlugins.setInitMainThreadSchedulerHandler { immediate }
        }
    }

    protected inline fun <reified F : Fragment, reified A : AppCompatActivity> createFragment(
            savedInstanceState: Bundle? = null,
            args: Parcelable? = null
    ): Pair<ActivityController<A>, F> {
        val controller = Robolectric.buildActivity(A::class.java)
                .create(savedInstanceState)
                .start()
                .resume()
                .visible()
        val activity = controller.get()

        val fragment = if (savedInstanceState == null) {
            F::class.java.newInstance().apply {
                arguments = Bundle().apply { putParcelable(MvRx.KEY_ARG, args) }
                activity.supportFragmentManager.beginTransaction().add(this, "TAG").commitNow()
            }
        } else {
            activity.supportFragmentManager.findFragmentByTag("TAG") as F
        }

        return controller to fragment
    }
}
