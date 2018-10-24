package com.androidtaipei.mxrxsample.core

import android.content.Context
import android.os.Bundle
import android.os.Parcelable
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v7.app.AlertDialog
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.airbnb.mvrx.MvRx
import com.androidtaipei.mxrxsample.R

fun Fragment.showFragment(fragment: Fragment) {
    showFragment(fragment, null)
}

fun Fragment.showFragment(fragment: Fragment, args: Parcelable?) {
    val args = Bundle().apply { putParcelable(MvRx.KEY_ARG, args) }
    fragment.arguments = args
    if (fragmentManager != null) {
        val tc = fragmentManager!!.beginTransaction()
        tc.setCustomAnimations(
                R.anim.anim_enter,
                R.anim.anim_exit,
                R.anim.anim_pop_enter,
                R.anim.anim_pop_exit
        )
                .add(R.id.fragmentContainer, fragment, fragment::javaClass.name)
                .addToBackStack("my_back_stack")
        tc.commit()
    }
}

fun Fragment.popFragment() {
    if (fragmentManager != null) {
        fragmentManager!!.popBackStack()
    }
}

fun Fragment.popToRootFragment() {
    if (fragmentManager != null) {
        fragmentManager!!.popBackStack("my_back_stack", FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }
}

fun Fragment.showErrorMessage(throwable: Throwable) {
    AlertDialog.Builder(requireContext())
            .setTitle(R.string.system_report)
            .setMessage(throwable.localizedMessage)
            .show()
}

fun Fragment.showToast(text: CharSequence) {
    Toast.makeText(requireContext(), text, Toast.LENGTH_SHORT).show()
}

fun Fragment.hideKeyboard(view: View) {
    val imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(view.windowToken, 0)
}