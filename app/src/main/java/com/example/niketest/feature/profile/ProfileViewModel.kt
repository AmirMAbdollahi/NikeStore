package com.example.niketest.feature.profile

import com.example.niketest.common.NikeViewModel
import com.example.niketest.data.TokenContainer
import com.example.niketest.data.repo.UserRepository

class ProfileViewModel(private val userRepository: UserRepository) : NikeViewModel() {

    val userName: String
        get() = userRepository.getUserName()

    val isSignedIn: Boolean
        get() = TokenContainer.token != null

    fun signOut() = userRepository.signOUt()

}