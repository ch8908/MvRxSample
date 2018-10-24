package com.androidtaipei.mxrxsample.dagger.provider

import com.androidtaipei.mxrxsample.main.HelloWorldFragment
import com.androidtaipei.mxrxsample.main.MainFragment
import com.androidtaipei.mxrxsample.main.SignInFragment
import com.androidtaipei.mxrxsample.main.SignUpFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainFragmentProvider {
    @ContributesAndroidInjector
    abstract fun provideChargeFragmentFactory(): MainFragment

    @ContributesAndroidInjector
    abstract fun provideSignInFragmentFactory(): SignInFragment

    @ContributesAndroidInjector
    abstract fun provideSignUpFragmentFactory(): SignUpFragment

    @ContributesAndroidInjector
    abstract fun provideHelloWorldFragmentFactory(): HelloWorldFragment
}
