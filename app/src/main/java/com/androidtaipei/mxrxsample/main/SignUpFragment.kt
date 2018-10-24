package com.androidtaipei.mxrxsample.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.airbnb.mvrx.*
import com.androidtaipei.mxrxsample.R
import com.androidtaipei.mxrxsample.core.*
import kotlinx.android.synthetic.main.fragment_sign_up.*

class SignUpFragment : BaseFragment() {
    companion object {
        fun newInstance(): SignUpFragment {
            return SignUpFragment()
        }
    }

    private val viewModel by fragmentViewModel(SignInViewModel::class)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_sign_up, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        signUpButton.setOnClickListener { _ -> signUp() }
        viewModel.selectSubscribe(SignInState::signUpRequest) {
            when (it) {
                is Success -> {
                    showToast("Sign up success.")
                    popToRootFragment()
                }
                is Fail -> {
                    showErrorMessage(it.error)
                }
            }
        }

    }

    override fun invalidate() {
        withState(viewModel) {
            if (it.signUpRequest is Loading) {
                progressBar.visibility = View.VISIBLE
            } else {
                progressBar.visibility = View.GONE
            }
        }
    }

    private fun signUp() {
        hideKeyboard(view!!)
        view!!.clearFocus()
        viewModel.signUp(emailEditText.text.toString(), passwordEditText.text.toString(), passwordConfirmationEditText.text.toString())
    }
}