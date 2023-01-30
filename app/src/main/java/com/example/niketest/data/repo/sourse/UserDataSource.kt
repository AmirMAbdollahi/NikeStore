package com.example.niketest.data.repo.sourse

import com.example.niketest.data.MessageResponse
import com.example.niketest.data.TokenResponse
import io.reactivex.Single

interface UserDataSource {

    fun login(userName: String, password: String): Single<TokenResponse>
    fun signUp(userName: String, password: String): Single<MessageResponse>
    fun loadToken()
    fun saveToken(token: String, refreshToken: String)

}