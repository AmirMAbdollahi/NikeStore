package com.example.niketest.data.repo

import com.example.niketest.data.Banner
import com.example.niketest.data.repo.sourse.BannerDataSource
import com.example.niketest.data.repo.sourse.BannerRemoteDataSource
import io.reactivex.Single

class BannerRepositoryImpl(val remoteDataSource: BannerDataSource):BannerRepository {
    override fun getBanner(): Single<List<Banner>> = remoteDataSource.getBanner()
}