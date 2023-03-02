package com.example.niketest.feature.search

import androidx.lifecycle.MutableLiveData
import com.example.niketest.R
import com.example.niketest.common.NikeSingleObserver
import com.example.niketest.common.NikeViewModel
import com.example.niketest.common.asyncNetworkRequest
import com.example.niketest.data.EmptyState
import com.example.niketest.data.Product
import com.example.niketest.data.repo.SearchRepository

class SearchViewModel(val searchRepository: SearchRepository) : NikeViewModel() {

    val searchLiveData = MutableLiveData<List<Product>>()
    val searchEmptyStatLiveData = MutableLiveData<EmptyState>()

    init {
        searchEmptyStatLiveData.value=EmptyState(true,R.string.searchProduct)
    }

    fun searchProduct(query: String) {
        if (query.isEmpty() ){
            searchEmptyStatLiveData.value=EmptyState(true,R.string.searchProduct)
        }
        progressBarLiveData.value = true
        searchRepository.search(query)
            .asyncNetworkRequest()
            .doFinally { progressBarLiveData.value = false }
            .subscribe(object : NikeSingleObserver<List<Product>>(compositeDisposable) {
                override fun onSuccess(t: List<Product>) {
                    if (t.isNotEmpty()) {
                        searchEmptyStatLiveData.value = EmptyState(false)
                        searchLiveData.value = t
                    } else searchEmptyStatLiveData.value = EmptyState(true, R.string.searchProduct)
                }
            })
    }


    /*fun search(query: String):Single<List<Product>>{
        return searchRepository.search(query)
    }*/


}