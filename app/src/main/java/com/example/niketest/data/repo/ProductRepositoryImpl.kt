package com.example.niketest.data.repo

import com.example.niketest.data.Product
import com.example.niketest.data.repo.sourse.ProductDataSource
import io.reactivex.Completable
import io.reactivex.Single

class ProductRepositoryImpl(
    val remoteDateSource: ProductDataSource,
    val localDataSource: ProductDataSource
) : ProductRepository {
    override fun getProduct(sort: Int): Single<List<Product>> =
        localDataSource.getFavoriteProducts()
            .flatMap { favoriteProducts ->
                remoteDateSource.getProduct(sort).doOnSuccess {
                    val favoriteProductIds = favoriteProducts.map {
                        it.id
                    }
                    it.forEach { product ->
                        if (favoriteProductIds.contains(product.id))
                            product.isFavorite = true
                    }
                }
            }

    override fun getFavoriteProducts(): Single<List<Product>> =
        localDataSource.getFavoriteProducts()

    override fun addToFavorite(product: Product): Completable =
        localDataSource.addToFavorite(product)

    override fun deleteFromFavorite(product: Product): Completable =
        localDataSource.deleteFromFavorite(product)

}