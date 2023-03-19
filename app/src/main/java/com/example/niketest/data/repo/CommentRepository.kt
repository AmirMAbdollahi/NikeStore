package com.example.niketest.data.repo

import com.example.niketest.data.Comment
import io.reactivex.Completable
import io.reactivex.Single

interface CommentRepository {

    fun addAll(productId: Int): Single<List<Comment>>

    fun insertSingle(title: String, content: String, productId: Int): Single<Comment>


}