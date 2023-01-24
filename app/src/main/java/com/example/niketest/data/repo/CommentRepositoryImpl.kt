package com.example.niketest.data.repo

import com.example.niketest.data.Comment
import com.example.niketest.data.repo.sourse.CommentDataSource
import com.example.niketest.data.repo.sourse.CommentRemoteDataSource
import io.reactivex.Single

class CommentRepositoryImpl(val commentRemoteDataSource: CommentDataSource) : CommentRepository {
    override fun addAll(productId: Int): Single<List<Comment>> =commentRemoteDataSource.addAll(productId)

    override fun insert(): Single<Comment> {
        TODO("Not yet implemented")
    }
}