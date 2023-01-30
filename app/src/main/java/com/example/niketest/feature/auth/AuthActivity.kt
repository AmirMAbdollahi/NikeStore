package com.example.niketest.feature.auth

import android.os.Bundle
import com.example.niketest.R
import com.example.niketest.common.NikeActivity

class AuthActivity : NikeActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        supportFragmentManager.beginTransaction().apply {
           replace(R.id.fragmentContainer,LoginFragment())
        }.commit()

    }
}