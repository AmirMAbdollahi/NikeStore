package com.example.niketest.data.repo.sourse

import com.example.niketest.data.Banner
import io.reactivex.Single

interface BannerDataSource {

    fun getBanner():Single<List<Banner>>
}