package com.androidtaipei.mxrxsample.main

import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.airbnb.mvrx.*
import com.androidtaipei.mxrxsample.MainActivity
import com.androidtaipei.mxrxsample.R
import com.androidtaipei.mxrxsample.core.*
import com.androidtaipei.mxrxsample.feature.Account
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import io.reactivex.Single
import kotlinx.android.synthetic.main.fragment_sign_in.*

data class SignInState(
    val signInRequest: Async<Account> = Uninitialized,
    val signUpRequest: Async<Account> = Uninitialized
) : MvRxState

class SignInViewModel @AssistedInject constructor(
    @Assisted initialState: SignInState,
    private val apiService: ApiService
) : MvRxViewModel<SignInState>(initialState) {
    @AssistedInject.Factory
    interface Factory {
        fun create(initialState: SignInState): SignInViewModel
    }

    companion object : MvRxViewModelFactory<SignInState> {
        @JvmStatic
        override fun create(activity: FragmentActivity, state: SignInState): SignInViewModel {
            return (activity as MainActivity).signFragmentViewModelFactory.create(state)
        }
    }

    init {
    }

    fun signIn(email: String, password: String) {
        Single.fromCallable {
            if (email.isEmpty() || password.isEmpty()) {
                throw Throwable("empty email or password")
            }
        }.flatMap { apiService.signIn(email, password) }.execute(fun SignInState.(it: Async<Account>): SignInState {
            return copy(signInRequest = it)
        })
    }

    fun signUp(email: String, password: String, passwordConfirmation: String) {
        Single.fromCallable {
            if (email.isEmpty() || password.isEmpty() || passwordConfirmation.isEmpty()) {
                throw Throwable("empty email or password")
            }
        }.flatMap { apiService.signUp(email, password, passwordConfirmation) }.execute { copy(signUpRequest = it) }
    }
}

class SignInFragment : BaseFragment() {
    companion object {
        fun newInstance(): SignInFragment {
            return SignInFragment()
        }
    }

    private val viewModel by fragmentViewModel(SignInViewModel::class)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_sign_in, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        logInButton.setOnClickListener { _ -> logIn() }
        signUpButton.setOnClickListener { _ -> gotoSignUpPage() }
        viewModel.asyncSubscribe(SignInState::signInRequest, onSuccess = {
            showToast("Log in success.")
            popToRootFragment()
        }, onFail = {
            showErrorMessage(it)
        })
    }

    private fun gotoSignUpPage() {
        showFragment(SignUpFragment.newInstance())
    }

    override fun invalidate() {
        withState(viewModel) {
            if (it.signInRequest is Loading) {
                progressBar.visibility = View.VISIBLE
            } else {
                progressBar.visibility = View.GONE
            }
        }
    }

    private fun logIn() {
        hideKeyboard(view!!)
        view!!.clearFocus()
        viewModel.signIn(emailEditText.text.toString(), passwordEditText.text.toString())
    }
}