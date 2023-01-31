package com.example.niketest.services.http

import com.example.niketest.data.TokenContainer
import com.example.niketest.data.TokenResponse
import com.example.niketest.data.repo.sourse.CLIENT_ID
import com.example.niketest.data.repo.sourse.CLIENT_SECRET
import com.example.niketest.data.repo.sourse.UserDataSource
import com.google.gson.JsonObject
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import timber.log.Timber

class NikeAuthenticator : Authenticator, KoinComponent {
    val apiService: ApiService by inject()
    val userLocalDataSource:UserDataSource by inject()
    override fun authenticate(route: Route?, response: Response): Request? {
        if (TokenContainer.token != null && TokenContainer.refreshToken != null && !response.request.url.pathSegments.last().equals("token",false)) {
            try {
                val token=refreshToken()
                if (token.isEmpty()){
                    return null
                }
                return response.request.newBuilder().header("Authorization","Bearer $token").build()
            }catch (exception:Exception){
                Timber.e(exception)
            }

        }

        return null
    }

    fun refreshToken(): String {
        val response: retrofit2.Response<TokenResponse> = apiService.refreshToken(JsonObject().apply {
            addProperty("grant_type","refresh_token")
            addProperty("refresh_token",TokenContainer.refreshToken)
            addProperty("client_id", CLIENT_ID)
            addProperty("client_secret", CLIENT_SECRET)
        }).execute()
        response.body()?.let {
            TokenContainer.update(it.access_token,it.refresh_token)
            userLocalDataSource.saveToken(it.access_token,it.refresh_token)
            return it.access_token
        }
        return ""
    }
}