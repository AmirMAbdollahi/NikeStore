package com.example.niketest.data.repo

import com.example.niketest.data.Product
import io.reactivex.Completable
import io.reactivex.Single

interface ProductRepository {
    fun getProduct(sort:Int): Single<List<Product>>

    fun getFavoriteProducts(): Single<List<Product>>

    fun addToFavorite(product: Product): Completable

    fun deleteFromFavorite(product: Product): Completable
}