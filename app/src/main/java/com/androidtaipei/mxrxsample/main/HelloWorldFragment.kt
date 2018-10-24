package com.androidtaipei.mxrxsample.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.airbnb.mvrx.MvRxState
import com.airbnb.mvrx.fragmentViewModel
import com.airbnb.mvrx.withState
import com.androidtaipei.mxrxsample.R
import com.androidtaipei.mxrxsample.core.BaseFragment
import com.androidtaipei.mxrxsample.core.MvRxViewModel
import kotlinx.android.synthetic.main.fragment_hello_world.*

data class HelloWorldState(val title: String = "") : MvRxState

class HelloWorldViewModel(initialState: HelloWorldState) : MvRxViewModel<HelloWorldState>(initialState) {
    fun showTitle(title: String) {
        withState { state ->
            println("a >>>>>>>>>>>>>>>>>> title: ${state.title}")
        }
        setState {
            copy(title = "Android")
        }
    }
}

class HelloWorldFragment : BaseFragment() {
    private val viewModel by fragmentViewModel(HelloWorldViewModel::class)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_hello_world, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.showTitle("Hello World!")
    }

    override fun invalidate() {
        withState(viewModel) {
            titleTextView.text = it.title
        }
    }
}