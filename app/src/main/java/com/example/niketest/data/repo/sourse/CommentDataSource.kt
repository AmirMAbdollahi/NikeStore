package com.example.niketest.data.repo.sourse

import com.example.niketest.data.Comment
import io.reactivex.Single

interface CommentDataSource {

    fun addAll(productId: Int): Single<List<Comment>>

    fun insert(): Single<Comment>

}