package com.example.niketest.feature

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import com.example.niketest.common.EXTRA_KEY_DATA
import com.example.niketest.common.NikeSingleObserver
import com.example.niketest.common.NikeViewModel
import com.example.niketest.common.asyncNetworkRequest
import com.example.niketest.data.Comment
import com.example.niketest.data.Product
import com.example.niketest.data.repo.CartRepository
import com.example.niketest.data.repo.CommentRepository
import io.reactivex.Completable

class ProductDetailViewModel(
    bundle: Bundle,
    private val commentRepositoryImpl: CommentRepository,
    val cartRepository: CartRepository
) :
    NikeViewModel() {

    val productLiveData = MutableLiveData<Product>()
    val commentListLiveData = MutableLiveData<List<Comment>>()
    val commentLiveData = MutableLiveData<Comment>()


    init {
        productLiveData.value = bundle.getParcelable(EXTRA_KEY_DATA)
        progressBarLiveData.value = true
        commentRepositoryImpl.addAll(productLiveData.value!!.id)
            .asyncNetworkRequest()
            .doFinally { progressBarLiveData.value = false }
            .subscribe(object : NikeSingleObserver<List<Comment>>(compositeDisposable) {
                override fun onSuccess(t: List<Comment>) {
                    commentListLiveData.value = t
                }
            })

    }

    fun onAddToCartBtn(): Completable =
        cartRepository.addToCart(productLiveData.value!!.id).ignoreElement()

    fun addComment(title: String, content: String, productId: Int) {
        commentRepositoryImpl.insertSingle(title, content, productId)
            .asyncNetworkRequest()
            .subscribe(object : NikeSingleObserver<Comment>(compositeDisposable) {
                override fun onSuccess(t: Comment) {
                    commentLiveData.value = t
                }
            })
    }


}