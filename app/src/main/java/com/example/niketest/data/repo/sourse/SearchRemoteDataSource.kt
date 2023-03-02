package com.example.niketest.data.repo.sourse

import com.example.niketest.data.Product
import com.example.niketest.services.http.ApiService
import io.reactivex.Single

class SearchRemoteDataSource(val apiService: ApiService):SearchDataSource {

    override fun search(query: String): Single<List<Product>> =
        apiService.searchProduct(query)

}