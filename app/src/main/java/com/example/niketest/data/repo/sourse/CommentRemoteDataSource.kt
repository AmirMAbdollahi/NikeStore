package com.example.niketest.data.repo.sourse

import com.example.niketest.data.Comment
import com.example.niketest.services.http.ApiService
import com.google.gson.JsonObject
import io.reactivex.Completable
import io.reactivex.Single

class CommentRemoteDataSource(val apiService: ApiService) : CommentDataSource {
    override fun addAll(productId: Int): Single<List<Comment>> = apiService.getComments(productId)

    override fun insertSingle(title: String, content: String, productId: Int): Single<Comment> =
        apiService.addCommentSingle(JsonObject().apply {
            addProperty("title", title)
            addProperty("content", content)
            addProperty("product_id", productId)
        })

}