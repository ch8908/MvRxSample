package com.androidtaipei.mxrxsample

import android.os.Bundle
import com.androidtaipei.mxrxsample.core.BaseActivity
import com.androidtaipei.mxrxsample.main.SignInViewModel
import javax.inject.Inject

class MainActivity : BaseActivity() {
    @Inject
    lateinit var signFragmentViewModelFactory: SignInViewModel.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

}
