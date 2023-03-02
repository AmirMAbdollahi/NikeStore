package com.example.niketest

import android.app.Application
import android.content.SharedPreferences
import android.os.Bundle
import androidx.room.Room
import com.example.niketest.data.db.AppDatabase
import com.example.niketest.data.repo.*
import com.example.niketest.data.repo.sourse.*
import com.example.niketest.feature.ProductDetailViewModel
import com.example.niketest.feature.auth.AuthViewModel
import com.example.niketest.feature.cart.CartViewModel
import com.example.niketest.feature.checkout.CheckOutViewModel
import com.example.niketest.feature.common.ProductListAdapter
import com.example.niketest.feature.favorites.FavoriteProductsViewModel
import com.example.niketest.feature.list.ProductListViewModel
import com.example.niketest.feature.home.HomeViewModel
import com.example.niketest.feature.main.MainViewModel
import com.example.niketest.feature.order.OrderHistoryViewModel
import com.example.niketest.feature.product.comment.CommentListViewModel
import com.example.niketest.feature.profile.ProfileViewModel
import com.example.niketest.feature.search.SearchViewModel
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
import org.koin.dsl.single
import timber.log.Timber

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())

        Fresco.initialize(this)

        val myModule = module {
            single<ApiService> { createApiServiceInstance() }
            single<ImageLoadingService> { FrescoImageLoadingService() }
            single { Room.databaseBuilder(this@App, AppDatabase::class.java, "db_app").build() }
            factory<ProductRepository> {
                ProductRepositoryImpl(
                    ProductRemoteDataSource(get()),
                    get<AppDatabase>().productDao()
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
            factory<SearchRepository> { SearchRepositoryImpl(SearchRemoteDataSource(get())) }
            viewModel { HomeViewModel(get(), get()) }
            viewModel { (bundle: Bundle) -> ProductDetailViewModel(bundle, get(), get()) }
            viewModel { (productId: Int) -> CommentListViewModel(productId, get()) }
            viewModel { (sort: Int) -> ProductListViewModel(sort, get()) }
            viewModel { AuthViewModel(get()) }
            viewModel { CartViewModel(get()) }
            viewModel { MainViewModel(get()) }
            viewModel { ShippingViewModel(get()) }
            viewModel { (orderId: Int) -> CheckOutViewModel(orderId, get()) }
            viewModel { ProfileViewModel(get()) }
            viewModel { FavoriteProductsViewModel(get()) }
            viewModel { OrderHistoryViewModel(get()) }
            viewModel { SearchViewModel(get()) }
        }

        startKoin {
            androidContext(this@App)
            modules(myModule)
        }

        val userRepository: UserRepository = get()
        userRepository.loadToken()

    }


}