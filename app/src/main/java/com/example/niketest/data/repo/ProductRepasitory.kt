package com.example.niketest.data.repo

import com.example.niketest.data.Product
import io.reactivex.Completable
import io.reactivex.Single

interface ProductRepository {
    fun getProduct(sort:Int): Single<List<Product>>

    fun getFavoriteProducts(sort: Int): Single<List<Product>>

    fun addToFavorite(): Completable

    fun deleteFromFavorite(): Completable
}