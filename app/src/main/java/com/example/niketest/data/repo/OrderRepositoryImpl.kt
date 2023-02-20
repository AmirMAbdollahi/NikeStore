package com.example.niketest.data.repo

import com.example.niketest.data.Checkout
import com.example.niketest.data.OrderHistoryItem
import com.example.niketest.data.SubmitOrderResult
import com.example.niketest.data.repo.sourse.OrderDataSource
import io.reactivex.Single

class OrderRepositoryImpl(private val orderDataSource: OrderDataSource) : OrderRepository {
    override fun submit(
        firstName: String,
        lastName: String,
        postalCode: String,
        phoneNumber: String,
        address: String,
        paymentMethod: String
    ): Single<SubmitOrderResult> {
        return orderDataSource.submit(
            firstName,
            lastName,
            postalCode,
            phoneNumber,
            address,
            paymentMethod
        )
    }

    override fun checkout(orderId: Int): Single<Checkout> {
        return orderDataSource.checkout(orderId)
    }

    override fun list(): Single<List<OrderHistoryItem>> = orderDataSource.list()
}