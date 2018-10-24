package com.androidtaipei.mxrxsample

import android.arch.lifecycle.Lifecycle
import com.airbnb.mvrx.withState
import com.androidtaipei.mxrxsample.core.ApiService
import com.androidtaipei.mxrxsample.main.SignInState
import com.androidtaipei.mxrxsample.main.SignInViewModel
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest : BaseTest() {
    private lateinit var owner: TestLifecycleOwner
    @Before
    fun setup() {
        owner = TestLifecycleOwner()
        owner.lifecycle.markState(Lifecycle.State.RESUMED)
    }

    @Test
    fun signIn_emptyPassword() {
        val signInViewModel = SignInViewModel(SignInState(), ApiService())
        signInViewModel.asyncSubscribe(owner, SignInState::signInRequest, onSuccess = {
            assertTrue(false)
        }, onFail = {
            assertTrue(true)
            assertEquals("empty email or password", it.localizedMessage)
        })
        signInViewModel.signIn("", "1234")
    }
}