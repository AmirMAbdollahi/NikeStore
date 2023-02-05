package com.example.niketest.feature.main

import com.example.niketest.common.NikeSingleObserver
import com.example.niketest.common.NikeViewModel
import com.example.niketest.data.CartItemCount
import com.example.niketest.data.TokenContainer
import com.example.niketest.data.repo.CartRepository
import io.reactivex.schedulers.Schedulers
import org.greenrobot.eventbus.EventBus

class MainViewModel(private val cartRepository: CartRepository):NikeViewModel() {

    fun getCartItemsCount(){
        if(!TokenContainer.token.isNullOrEmpty()){
            cartRepository.getCartItemsCount()
                .subscribeOn(Schedulers.io())
                .subscribe(object :NikeSingleObserver<CartItemCount>(compositeDisposable){
                    override fun onSuccess(t: CartItemCount) {
                        EventBus.getDefault().postSticky(t)
                    }
                })
        }
    }

}