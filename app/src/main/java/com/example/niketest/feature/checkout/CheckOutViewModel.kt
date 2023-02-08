package com.example.niketest.feature.checkout

import androidx.lifecycle.MutableLiveData
import com.example.niketest.common.NikeSingleObserver
import com.example.niketest.common.NikeViewModel
import com.example.niketest.common.asyncNetworkRequest
import com.example.niketest.data.Checkout
import com.example.niketest.data.repo.OrderRepository

class CheckOutViewModel(orderId: Int, orderRepository: OrderRepository) : NikeViewModel() {

    val checkoutLiveData = MutableLiveData<Checkout>()

    init {
        orderRepository.checkout(orderId)
            .asyncNetworkRequest()
            .subscribe(object : NikeSingleObserver<Checkout>(compositeDisposable) {
                override fun onSuccess(t: Checkout) {
                    checkoutLiveData.value = t
                }
            })
    }


}