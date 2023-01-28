package com.example.niketest.data.repo.sourse

import com.example.niketest.data.AddToCartResponse
import com.example.niketest.data.CartItemCount
import com.example.niketest.data.CartResponse
import com.example.niketest.data.MessageResponse
import io.reactivex.Single

interface CartDataSource {

    fun addToCart(productId: Int): Single<AddToCartResponse>
    fun get(): Single<CartResponse>
    fun remove(cartItemId: Int): Single<MessageResponse>
    fun changeCount(cartItemId: Int, count: Int): Single<AddToCartResponse>
    fun getCartItemsCount(): Single<CartItemCount>

}