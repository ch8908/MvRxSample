package com.androidtaipei.mxrxsample.dagger

import com.androidtaipei.mxrxsample.MainActivity
import com.androidtaipei.mxrxsample.dagger.provider.MainFragmentProvider
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder {
    @ContributesAndroidInjector(modules = [MainFragmentProvider::class])
    internal abstract fun bindMainActivity(): MainActivity
}