package com.example.niketest.data.repo.sourse

import com.example.niketest.data.Product
import io.reactivex.Completable
import io.reactivex.Single

interface ProductDataSource {
    fun getProduct(sort: Int): Single<List<Product>>

    fun getFavoriteProducts(): Single<List<Product>>

    fun addToFavorite(product: Product): Completable

    fun deleteFromFavorite(product: Product): Completable
}