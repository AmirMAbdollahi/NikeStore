package com.example.niketest.data.repo.sourse

import com.example.niketest.data.Product
import io.reactivex.Completable
import io.reactivex.Single

interface ProductDataSource {
    fun getProduct(sort: Int): Single<List<Product>>

    fun getFavoriteProducts(sort: Int): Single<List<Product>>

    fun addToFavorite(): Completable

    fun deleteFromFavorite(): Completable
}