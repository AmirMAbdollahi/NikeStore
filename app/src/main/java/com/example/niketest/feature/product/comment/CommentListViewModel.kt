package com.example.niketest.feature.product.comment

import androidx.lifecycle.MutableLiveData
import com.example.niketest.common.NikeSingleObserver
import com.example.niketest.common.NikeViewModel
import com.example.niketest.common.asyncNetworkRequest
import com.example.niketest.data.Comment
import com.example.niketest.data.repo.CommentRepository

class CommentListViewModel(productId: Int, commentRepositoryImpl: CommentRepository) :
    NikeViewModel() {

    val commentsLiveData = MutableLiveData<List<Comment>>()

    init {
        progressBarLiveData.value = true
        commentRepositoryImpl.addAll(productId)
            .asyncNetworkRequest()
            .doFinally { progressBarLiveData.value = false }
            .subscribe(object : NikeSingleObserver<List<Comment>>(compositeDisposable) {
                override fun onSuccess(t: List<Comment>) {
                    commentsLiveData.value = t
                }
            })
    }


}