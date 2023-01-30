package com.example.niketest.feature.auth

import com.example.niketest.common.NikeViewModel
import com.example.niketest.data.repo.UserRepository
import io.reactivex.Completable

class AuthViewModel(private val userRepository: UserRepository):NikeViewModel() {

    fun login(userName:String,password:String):Completable{
        progressBarLiveData.value=true
        return userRepository.login(userName,password).doFinally {
            progressBarLiveData.postValue(false)
        }
    }

    fun signUP(userName:String,password: String):Completable{
        progressBarLiveData.value=true
        return userRepository.signUp(userName,password).doFinally {
            progressBarLiveData.postValue(false)
        }
    }


}