package com.example.niketest.feature.order

import androidx.lifecycle.MutableLiveData
import com.example.niketest.common.NikeSingleObserver
import com.example.niketest.common.NikeViewModel
import com.example.niketest.common.asyncNetworkRequest
import com.example.niketest.data.OrderHistoryItem
import com.example.niketest.data.repo.OrderRepository

class OrderHistoryViewModel(orderRepository: OrderRepository) : NikeViewModel() {

    val orderHistoryLiveData = MutableLiveData<List<OrderHistoryItem>>()

    init {
        progressBarLiveData.value=true
        orderRepository.list().asyncNetworkRequest().doFinally { progressBarLiveData.value = false }
            .subscribe(object : NikeSingleObserver<List<OrderHistoryItem>>(compositeDisposable) {
                override fun onSuccess(t: List<OrderHistoryItem>) {
                    orderHistoryLiveData.value = t
                }
            })
    }


}