package com.example.niketest.data.repo

import com.example.niketest.data.Product
import com.example.niketest.data.repo.sourse.SearchDataSource
import io.reactivex.Single

class SearchRepositoryImpl(private val searchDataSource: SearchDataSource) : SearchRepository {

    override fun search(query: String): Single<List<Product>> =
        searchDataSource.search(query)

}