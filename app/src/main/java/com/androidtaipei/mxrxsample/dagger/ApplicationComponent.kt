package com.androidtaipei.mxrxsample.dagger

import com.androidtaipei.mxrxsample.core.MyApplication
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        (AppModule::class),
        (AppAssistedModule::class),
        (ActivityBuilder::class),
        (AndroidSupportInjectionModule::class)
    ]
)
interface ApplicationComponent : AndroidInjector<MyApplication> {
    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<MyApplication>() {
        abstract fun appModule(module: AppModule): Builder
    }
}
