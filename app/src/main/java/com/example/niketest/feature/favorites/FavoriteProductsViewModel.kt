package com.example.niketest.feature.favorites

import androidx.lifecycle.MutableLiveData
import com.example.niketest.common.NikeCompletableObserver
import com.example.niketest.common.NikeSingleObserver
import com.example.niketest.common.NikeViewModel
import com.example.niketest.data.Product
import com.example.niketest.data.repo.ProductRepository
import io.reactivex.schedulers.Schedulers

class FavoriteProductsViewModel(val productRepository: ProductRepository) : NikeViewModel() {

    val favoriteProductLiveData = MutableLiveData<List<Product>>()

    init {
        productRepository.getFavoriteProducts()
            .subscribeOn(Schedulers.io())
            .subscribe(object : NikeSingleObserver<List<Product>>(compositeDisposable) {
                override fun onSuccess(t: List<Product>) {
                    favoriteProductLiveData.postValue(t)
                }
            })
    }

    fun removeFromFavorite(product: Product) {
        productRepository.deleteFromFavorite(product)
            .subscribeOn(Schedulers.io())
            .subscribe(object : NikeCompletableObserver(compositeDisposable) {
                override fun onComplete() {
                    product.isFavorite = false
                }
            })
    }


}