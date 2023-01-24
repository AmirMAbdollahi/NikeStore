package com.example.niketest.data.repo.sourse

import com.example.niketest.data.Comment
import com.example.niketest.services.http.ApiService
import io.reactivex.Single

class CommentRemoteDataSource(val apiService: ApiService) : CommentDataSource {
    override fun addAll(productId: Int): Single<List<Comment>> =apiService.getComments(productId)

    override fun insert(): Single<Comment> {
        TODO("Not yet implemented")
    }
}