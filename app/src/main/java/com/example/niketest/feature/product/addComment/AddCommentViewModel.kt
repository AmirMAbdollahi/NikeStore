package com.example.niketest.feature.product.addComment

import androidx.lifecycle.MutableLiveData
import com.example.niketest.common.NikeCompletableObserver
import com.example.niketest.common.NikeSingleObserver
import com.example.niketest.common.NikeViewModel
import com.example.niketest.common.asyncNetworkRequest
import com.example.niketest.data.Comment
import com.example.niketest.data.repo.CommentRepository
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class AddCommentViewModel(val commentRepository: CommentRepository) :
    NikeViewModel() {

    val commentLiveData = MutableLiveData<Comment>()

    fun addCommentSingle(title: String, content: String, productId: Int) {
        commentRepository.insertSingle(title, content, productId)
            .asyncNetworkRequest()
            .subscribe(object : NikeSingleObserver<Comment>(compositeDisposable) {
                override fun onSuccess(t: Comment) {
                    commentLiveData.value = t
                }
            })
    }

    fun addCommentCompletable(title: String, content: String, productId: Int): Completable {
        return commentRepository.insertSingle(title, content, productId)
            .ignoreElement()
    }

}


