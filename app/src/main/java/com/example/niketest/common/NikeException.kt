package com.example.niketest.common

import androidx.annotation.StringRes

class NikeException(
    val type: Type,
    @StringRes val userFriendlyMessage: Int = 0,
    val serverMassage: String? = null
) : Throwable() {

    enum class Type {
        SIMPLE, AUTH
    }


}