package com.example.niketest

import android.app.Application
import android.os.Bundle
import com.example.niketest.data.repo.*
import com.example.niketest.data.repo.sourse.BannerRemoteDataSource
import com.example.niketest.data.repo.sourse.CommentRemoteDataSource
import com.example.niketest.data.repo.sourse.ProductLocalDataSource
import com.example.niketest.data.repo.sourse.ProductRemoteDataSource
import com.example.niketest.feature.ProductDetailViewModel
import com.example.niketest.feature.common.ProductListAdapter
import com.example.niketest.feature.list.ProductListViewModel
import com.example.niketest.feature.home.HomeViewModel
import com.example.niketest.feature.product.comment.CommentListViewModel
import com.example.niketest.services.FrescoImageLoadingService
import com.example.niketest.services.ImageLoadingService
import com.example.niketest.services.http.ApiService
import com.example.niketest.services.http.createApiServiceInstance
import com.facebook.drawee.backends.pipeline.Fresco
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
            factory { (viewType: Int) -> ProductListAdapter(viewType, get()) }
            factory<BannerRepository> { BannerRepositoryImpl(BannerRemoteDataSource(get())) }
            factory<CommentRepository> { CommentRepositoryImpl(CommentRemoteDataSource(get())) }
            viewModel { HomeViewModel(get(), get()) }
            viewModel { (bundle: Bundle) -> ProductDetailViewModel(bundle, get()) }
            viewModel { (productId: Int) -> CommentListViewModel(productId, get()) }
            viewModel { (sort: Int) -> ProductListViewModel(sort, get()) }
        }

        startKoin {
            androidContext(this@App)
            modules(myModule)
        }

    }


}