package com.androidtaipei.mxrxsample.dagger

import android.content.Context
import com.androidtaipei.mxrxsample.core.ApiService
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(
    private val context: Context
) {
    @Provides
    @Singleton
    fun provideContext(): Context {
        return context
    }

    @Provides
    @Singleton
    fun provideApiService(): ApiService {
        return ApiService()
    }
}