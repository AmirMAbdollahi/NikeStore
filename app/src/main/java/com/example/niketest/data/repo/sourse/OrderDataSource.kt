package com.example.niketest.data.repo.sourse

import com.example.niketest.data.Checkout
import com.example.niketest.data.OrderHistoryItem
import com.example.niketest.data.SubmitOrderResult
import io.reactivex.Single

interface OrderDataSource {
    fun submit(
        firstName: String,
        lastName: String,
        postalCode: String,
        phoneNumber: String,
        address: String,
        paymentMethod: String
    ): Single<SubmitOrderResult>

    fun checkout(orderId:Int): Single<Checkout>

    fun list():Single<List<OrderHistoryItem>>
}