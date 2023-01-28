package com.example.niketest.feature.list

import androidx.lifecycle.MutableLiveData
import com.example.niketest.R
import com.example.niketest.common.NikeSingleObserver
import com.example.niketest.common.NikeViewModel
import com.example.niketest.common.asyncNetworkRequest
import com.example.niketest.data.Product
import com.example.niketest.data.repo.ProductRepository

class ProductListViewModel(var sort: Int, val productRepository: ProductRepository) :
    NikeViewModel() {

    val productsLiveData = MutableLiveData<List<Product>>()
    val selectedSortTitleLiveData = MutableLiveData<Int>()
    val sortTitle = arrayOf(
        R.string.sortLatest,
        R.string.sortPopular,
        R.string.sortPriceHighToLow,
        R.string.sortPriceLowToHigh
    )

    init {
        getProducts()
        selectedSortTitleLiveData.value = sortTitle[sort]
    }

    fun getProducts() {
        progressBarLiveData.value = true
        productRepository.getProduct(sort)
            .asyncNetworkRequest()
            .doFinally { progressBarLiveData.value = false }
            .subscribe(object : NikeSingleObserver<List<Product>>(compositeDisposable) {
                override fun onSuccess(t: List<Product>) {
                    productsLiveData.value = t
                }
            })
    }

    fun onSelectedSortChangeByUser(sort: Int) {
        this.sort = sort
        this.selectedSortTitleLiveData.value = sortTitle[sort]
        getProducts()
    }


}