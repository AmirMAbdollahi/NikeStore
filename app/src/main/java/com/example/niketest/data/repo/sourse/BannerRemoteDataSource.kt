package com.example.niketest.data.repo.sourse

import com.example.niketest.data.Banner
import com.example.niketest.services.http.ApiService
import io.reactivex.Single

class BannerRemoteDataSource(val apiService: ApiService):BannerDataSource {
    override fun getBanner(): Single<List<Banner>> = apiService.getBanners()
}