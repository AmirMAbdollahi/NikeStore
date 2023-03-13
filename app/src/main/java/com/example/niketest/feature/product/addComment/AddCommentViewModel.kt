package com.example.niketest.feature.product.addComment

import androidx.lifecycle.MutableLiveData
import com.example.niketest.common.NikeSingleObserver
import com.example.niketest.common.NikeViewModel
import com.example.niketest.common.asyncNetworkRequest
import com.example.niketest.data.Comment
import com.example.niketest.data.repo.CommentRepository

class AddCommentViewModel(val commentRepository: CommentRepository) :
    NikeViewModel() {

    val commentLiveData = MutableLiveData<Comment>()

    fun addComment(title: String, content: String, productId: Int) {
        commentRepository.insert(title, content, productId)
            .asyncNetworkRequest()
            .subscribe(object : NikeSingleObserver<Comment>(compositeDisposable) {
                override fun onSuccess(t: Comment) {
                    commentLiveData.value = t
                }
            })
    }

}


