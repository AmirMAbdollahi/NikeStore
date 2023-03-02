package com.example.niketest.data.repo

import com.example.niketest.data.Product
import io.reactivex.Single

interface SearchRepository {

    fun search(query:String):Single<List<Product>>

}