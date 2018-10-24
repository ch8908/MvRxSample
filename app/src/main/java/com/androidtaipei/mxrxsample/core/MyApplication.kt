package com.androidtaipei.mxrxsample.core

import com.androidtaipei.mxrxsample.dagger.AppModule
import com.androidtaipei.mxrxsample.dagger.DaggerApplicationComponent
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication

class MyApplication : DaggerApplication() {
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerApplicationComponent.builder()
            .appModule(AppModule(this))
            .create(this)
    }
}