package com.example.niketest.data.repo.sourse

import com.example.niketest.data.Product
import io.reactivex.Single

interface SearchDataSource {

    fun search(query:String): Single<List<Product>>

}