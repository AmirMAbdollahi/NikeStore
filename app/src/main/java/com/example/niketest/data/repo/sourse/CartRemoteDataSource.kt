package com.example.niketest.data.repo.sourse

import com.example.niketest.data.AddToCartResponse
import com.example.niketest.data.CartItemCount
import com.example.niketest.data.CartResponse
import com.example.niketest.data.MessageResponse
import com.example.niketest.services.http.ApiService
import com.google.gson.JsonObject
import io.reactivex.Single

class CartRemoteDataSource(val apiService: ApiService) : CartDataSource {
    override fun addToCart(productId: Int): Single<AddToCartResponse> =apiService.addToCart(
        JsonObject().apply {
            addProperty("product_id",productId)
        }
    )

    override fun get(): Single<CartResponse> {
        TODO("Not yet implemented")
    }

    override fun remove(cartItemId: Int): Single<MessageResponse> {
        TODO("Not yet implemented")
    }

    override fun changeCount(cartItemId: Int, count: Int): Single<AddToCartResponse> {
        TODO("Not yet implemented")
    }

    override fun getCartItemsCount(): Single<CartItemCount> {
        TODO("Not yet implemented")
    }
}