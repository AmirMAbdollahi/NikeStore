package com.example.niketest.data.repo

import com.example.niketest.data.Banner
import io.reactivex.Single

interface BannerRepository {

    fun getBanner(): Single<List<Banner>>


}