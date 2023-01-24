package com.example.niketest.data.repo.sourse

import com.example.niketest.data.Product
import com.example.niketest.services.http.ApiService
import io.reactivex.Completable
import io.reactivex.Single

class ProductRemoteDataSource(val apiService: ApiService) : ProductDataSource {
    override fun getProduct(sort: Int): Single<List<Product>> = apiService.getProduct(sort.toString())

    override fun getFavoriteProducts(sort: Int): Single<List<Product>> = apiService.getProduct(sort.toString())

    override fun addToFavorite(): Completable {
        TODO("Not yet implemented")
    }

    override fun deleteFromFavorite(): Completable {
        TODO("Not yet implemented")
    }
}