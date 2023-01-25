package com.example.niketest.feature.home

import androidx.lifecycle.MutableLiveData
import com.example.niketest.common.NikeSingleObserver
import com.example.niketest.common.NikeViewModel
import com.example.niketest.common.asyncNetworkRequest
import com.example.niketest.data.Banner
import com.example.niketest.data.Product
import com.example.niketest.data.SORT_LATEST
import com.example.niketest.data.SORT_POPULAR
import com.example.niketest.data.repo.BannerRepository
import com.example.niketest.data.repo.ProductRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class HomeViewModel(
     productRepository: ProductRepository,
     bannerRepository: BannerRepository
) : NikeViewModel() {
    val productLatestLiveData = MutableLiveData<List<Product>>()
    val productPopularLiveData = MutableLiveData<List<Product>>()
    val bannerLiveData = MutableLiveData<List<Banner>>()

    init {
        progressBarLiveData.value = true
        productRepository.getProduct(SORT_LATEST)
            .asyncNetworkRequest()
            .doFinally { progressBarLiveData.value = false }
            .subscribe(object : NikeSingleObserver<List<Product>>(compositeDisposable) {
                override fun onSuccess(t: List<Product>) {
                    productLatestLiveData.value = t
                }

            })

        productRepository.getFavoriteProducts(SORT_POPULAR).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : NikeSingleObserver<List<Product>>(compositeDisposable) {
                override fun onSuccess(t: List<Product>) {
                    productPopularLiveData.value = t
                }

            })

        bannerRepository.getBanner()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : NikeSingleObserver<List<Banner>>(compositeDisposable) {
                override fun onSuccess(t: List<Banner>) {
                    bannerLiveData.value = t
                }
            })


    }

}