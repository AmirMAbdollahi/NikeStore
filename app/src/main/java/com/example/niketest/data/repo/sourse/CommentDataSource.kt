package com.example.niketest.data.repo.sourse

import com.example.niketest.data.Comment
import io.reactivex.Single

interface CommentDataSource {

    fun addAll(productId: Int): Single<List<Comment>>

    fun insert(title: String, content: String, productId: Int): Single<Comment>

}