package com.example.niketest.feature.main

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.niketest.R
import com.example.niketest.common.EXTRA_KEY_DATA
import com.example.niketest.common.NikeFragment
import com.example.niketest.common.convertDpToPixel
import com.example.niketest.data.Product
import com.example.niketest.feature.product.ProductDetailActivity
import kotlinx.android.synthetic.main.fragment_main.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class MainFragment : NikeFragment(),ProductListAdapter.OnProductClickListener {
    val mainViewModel: MainViewModel by viewModel()
    val productListLatestAdapter: ProductListAdapter by inject()
    val productListPopularAdapter: ProductListAdapter by inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        latestProductRv.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        latestProductRv.adapter = productListLatestAdapter

        popularProductRv.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        popularProductRv.adapter = productListPopularAdapter

        productListLatestAdapter.onProductClickListener=this
        productListPopularAdapter.onProductClickListener=this

        mainViewModel.productLatestLiveData.observe(viewLifecycleOwner) {
            Timber.tag("latest").i("product : ")
            productListLatestAdapter.products = it as ArrayList<Product>
        }
        mainViewModel.productPopularLiveData.observe(viewLifecycleOwner) {
            Timber.tag("popular").i("product : ")
            productListPopularAdapter.products= it as ArrayList<Product>
        }
        mainViewModel.progressBarLiveData.observe(viewLifecycleOwner) {
            setProgressIndicator(it)
        }
        mainViewModel.bannerLiveData.observe(viewLifecycleOwner) {
            Timber.i(it.toString())
            val bannerSliderAdapter = BannerSliderAdapter(this, it)
            bannerSliderViewPager.adapter = bannerSliderAdapter
            val viewPagerHeight = (((bannerSliderViewPager.measuredWidth - convertDpToPixel(
                32f,
                requireContext()
            )) * 173) / 328).toInt()
            val layoutParams = bannerSliderViewPager.layoutParams
            layoutParams.height = viewPagerHeight
            bannerSliderViewPager.layoutParams = layoutParams

            sliderIndicator.setViewPager2(bannerSliderViewPager)
        }

    }

    override fun onClickProduct(product: Product) {
        startActivity(Intent(requireContext(),ProductDetailActivity::class.java).apply {
            putExtra(EXTRA_KEY_DATA,product)
        })
    }
}