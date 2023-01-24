package com.example.niketest.data.repo

import com.example.niketest.data.Product
import com.example.niketest.data.repo.sourse.ProductDataSource
import io.reactivex.Completable
import io.reactivex.Single

class ProductRepositoryImpl(
    val remoteDateSource: ProductDataSource,
    val localDataSource: ProductDataSource
) : ProductRepository {
    override fun getProduct(sort: Int): Single<List<Product>> = remoteDateSource.getProduct(sort)

    override fun getFavoriteProducts(sort: Int): Single<List<Product>> =remoteDateSource.getFavoriteProducts(sort)

    override fun addToFavorite(): Completable {
        TODO("Not yet implemented")
    }

    override fun deleteFromFavorite(): Completable {
        TODO("Not yet implemented")
    }
}