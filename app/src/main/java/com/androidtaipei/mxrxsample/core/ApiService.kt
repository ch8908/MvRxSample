package com.androidtaipei.mxrxsample.core

import com.androidtaipei.mxrxsample.feature.Account
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class ApiService {
    fun signIn(email: String, password: String): Single<Account> {

        return Single.timer(1000, TimeUnit.MILLISECONDS).map { _ ->
            if ("1234" == password) {
                Account(email)
            } else {
                throw Throwable("wrong password")
            }
        }.subscribeOn(Schedulers.io())
    }

    fun signUp(email: String, password: String, passwordConfirmation: String): Single<Account> {
        return Single.timer(2000, TimeUnit.MILLISECONDS).map {
            if (password != passwordConfirmation) {
                throw Throwable("password does not match.")
            }
            if ("1234" == password) {
                Account(email)
            } else {
                throw Throwable("wrong password")
            }
        }.subscribeOn(Schedulers.io())
    }
}