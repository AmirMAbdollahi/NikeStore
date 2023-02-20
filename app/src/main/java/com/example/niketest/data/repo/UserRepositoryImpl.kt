package com.example.niketest.data.repo

import com.example.niketest.data.TokenContainer
import com.example.niketest.data.TokenResponse
import com.example.niketest.data.repo.sourse.UserDataSource
import io.reactivex.Completable

class UserRepositoryImpl(
    val userRemoteDataSource: UserDataSource,
    val userLocalDataSource: UserDataSource
) : UserRepository {
    override fun login(userName: String, password: String): Completable {
        return userRemoteDataSource.login(userName, password).doOnSuccess {
            onSuccessfulLogin(userName, it)
        }.ignoreElement()
    }

    override fun signUp(userName: String, password: String): Completable {
        return userRemoteDataSource.signUp(userName, password).flatMap {
            userRemoteDataSource.login(userName, password).doOnSuccess {
                onSuccessfulLogin(userName, it)
            }
        }.ignoreElement()
    }

    override fun loadToken() {
        userLocalDataSource.loadToken()
    }

    override fun getUserName(): String = userLocalDataSource.getUserName()

    override fun signOUt() {
        userLocalDataSource.signOut()
        TokenContainer.update(null, null)
    }

    fun onSuccessfulLogin(userName: String, tokenResponse: TokenResponse) {
        TokenContainer.update(tokenResponse.access_token, tokenResponse.refresh_token)
        userLocalDataSource.saveToken(tokenResponse.access_token, tokenResponse.refresh_token)
        userLocalDataSource.saveUserName(userName)
    }

}