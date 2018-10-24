package com.androidtaipei.mxrxsample.core

import android.os.Bundle
import com.airbnb.mvrx.MvRxViewModelStore
import com.airbnb.mvrx.MvRxViewModelStoreOwner
import dagger.android.AndroidInjection
import dagger.android.support.DaggerAppCompatActivity

abstract class BaseActivity : DaggerAppCompatActivity(), MvRxViewModelStoreOwner {
    /**
     * MvRx has its own wrapped ViewModelStore that enables improved state restoration if Android
     * kills your app and restores it in a new process.
     */
    override val mvrxViewModelStore by lazy { MvRxViewModelStore(viewModelStore) }

    override fun onCreate(savedInstanceState: Bundle?) {
        /**
         * This MUST be called before restoreViewModels!
         * In a new process, MvRxViewModel will restore automatically, before restore, Fragment needs to get all injection ready. (If you're using dagger)
         */
        AndroidInjection.inject(this)

        /**
         * This MUST be called before super!
         * In a new process, super.onCreate will trigger Fragment.onCreate, which could access a ViewModel.
         */
        mvrxViewModelStore.restoreViewModels(this, savedInstanceState)

        super.onCreate(savedInstanceState)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mvrxViewModelStore.saveViewModels(outState)
    }
}