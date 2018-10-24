package com.androidtaipei.mxrxsample.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.androidtaipei.mxrxsample.R
import com.androidtaipei.mxrxsample.core.BaseFragment
import com.androidtaipei.mxrxsample.core.showFragment
import com.androidtaipei.mxrxsample.core.simpleController
import com.androidtaipei.mxrxsample.views.basicRow
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : BaseFragment() {
    val epoxyController by lazy { epoxyController() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView.setController(epoxyController)
    }

    override fun invalidate() {
        super.invalidate()
        recyclerView.requestModelBuild()
    }

    private fun gotoLogInPage() {
        showFragment(SignInFragment.newInstance())
    }

    private fun gotoHelloWorld() {
        showFragment(HelloWorldFragment())
    }

    fun epoxyController() = simpleController {
        basicRow {
            id("hello_world")
            title("Hello World")
            subtitle("")
            clickListener { _ ->
                gotoHelloWorld()
            }
        }
        basicRow {
            id("log_in_page")
            title("Log In")
            subtitle("Log In Demo")
            clickListener { _ ->
                gotoLogInPage()
            }
        }

    }
}