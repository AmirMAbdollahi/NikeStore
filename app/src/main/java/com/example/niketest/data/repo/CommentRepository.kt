package com.example.niketest.data.repo

import com.example.niketest.data.Comment
import io.reactivex.Single

interface CommentRepository {

    fun addAll(productId: Int): Single<List<Comment>>

    fun insert(): Single<Comment>
}