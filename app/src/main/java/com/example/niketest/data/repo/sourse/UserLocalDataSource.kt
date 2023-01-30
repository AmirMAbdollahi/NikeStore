package com.example.niketest.data.repo.sourse

import android.content.SharedPreferences
import com.example.niketest.data.MessageResponse
import com.example.niketest.data.TokenContainer
import com.example.niketest.data.TokenResponse
import io.reactivex.Single

class UserLocalDataSource(val sharedPreferences: SharedPreferences) : UserDataSource {
    override fun login(userName: String, password: String): Single<TokenResponse> {
        TODO("Not yet implemented")
    }

    override fun signUp(userName: String, password: String): Single<MessageResponse> {
        TODO("Not yet implemented")
    }

    override fun loadToken() {
        TokenContainer.update(
            sharedPreferences.getString("access_token", null),
            sharedPreferences.getString("refresh_token", null)
        )
    }

    override fun saveToken(token: String, refreshToken: String) {
        sharedPreferences.edit().apply {
            putString("access_token", token)
            putString("refresh_token", refreshToken)
        }.apply()
    }
}