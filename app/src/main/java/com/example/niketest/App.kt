package com.example.niketest

import android.app.Application
import android.content.SharedPreferences
import android.os.Bundle
import com.example.niketest.data.repo.*
import com.example.niketest.data.repo.sourse.*
import com.example.niketest.feature.ProductDetailViewModel
import com.example.niketest.feature.auth.AuthViewModel
import com.example.niketest.feature.cart.CartViewModel
import com.example.niketest.feature.checkout.CheckOutViewModel
import com.example.niketest.feature.common.ProductListAdapter
import com.example.niketest.feature.list.ProductListViewModel
import com.example.niketest.feature.home.HomeViewModel
import com.example.niketest.feature.main.MainViewModel
import com.example.niketest.feature.product.comment.CommentListViewModel
import com.example.niketest.feature.shipping.ShippingViewModel
import com.example.niketest.services.FrescoImageLoadingService
import com.example.niketest.services.ImageLoadingService
import com.example.niketest.services.http.ApiService
import com.example.niketest.services.http.createApiServiceInstance
import com.facebook.drawee.backends.pipeline.Fresco
import org.koin.android.ext.android.get
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module
import timber.log.Timber

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())

        Fresco.initialize(this)

        val myModule = module {
            single<ApiService> { createApiServiceInstance() }
            single<ImageLoadingService> { FrescoImageLoadingService() }
            factory<ProductRepository> {
                ProductRepositoryImpl(
                    ProductRemoteDataSource(get()),
                    ProductLocalDataSource()
                )
            }
            single<SharedPreferences> {
                this@App.getSharedPreferences(
                    "app_settings",
                    MODE_PRIVATE
                )
            }
            single { UserLocalDataSource(get()) }
            single<UserRepository> {
                UserRepositoryImpl(
                    UserRemoteDataSource(get()),
                    UserLocalDataSource(get())
                )
            }
            single<OrderRepository> { OrderRepositoryImpl(OrderRemoteDataSource(get())) }
            factory { (viewType: Int) -> ProductListAdapter(viewType, get()) }
            factory<BannerRepository> { BannerRepositoryImpl(BannerRemoteDataSource(get())) }
            factory<CommentRepository> { CommentRepositoryImpl(CommentRemoteDataSource(get())) }
            factory<CartRepository> { CartRepositoryImpl(CartRemoteDataSource(get())) }
            viewModel { HomeViewModel(get(), get()) }
            viewModel { (bundle: Bundle) -> ProductDetailViewModel(bundle, get(), get()) }
            viewModel { (productId: Int) -> CommentListViewModel(productId, get()) }
            viewModel { (sort: Int) -> ProductListViewModel(sort, get()) }
            viewModel { AuthViewModel(get()) }
            viewModel { CartViewModel(get()) }
            viewModel { MainViewModel(get()) }
            viewModel { ShippingViewModel(get()) }
            viewModel { (orderId:Int)-> CheckOutViewModel(orderId,get()) }
        }

        startKoin {
            androidContext(this@App)
            modules(myModule)
        }

        val userRepository: UserRepository = get()
        userRepository.loadToken()

    }


}