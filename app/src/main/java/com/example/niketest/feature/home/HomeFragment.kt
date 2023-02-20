package com.example.niketest.feature.home

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
import com.example.niketest.data.SORT_LATEST
import com.example.niketest.data.SORT_POPULAR
import com.example.niketest.feature.common.ProductListAdapter
import com.example.niketest.feature.common.VIEW_TYPE_ROUND
import com.example.niketest.feature.list.ProductListActivity
import com.example.niketest.feature.main.BannerSliderAdapter
import com.example.niketest.feature.product.ProductDetailActivity
import kotlinx.android.synthetic.main.fragment_home.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import timber.log.Timber

class HomeFragment : NikeFragment(), ProductListAdapter.ProductEventListener {
    private val homeViewModel: HomeViewModel by viewModel()
    private val productListLatestAdapter: ProductListAdapter by inject { parametersOf(VIEW_TYPE_ROUND) }
    private val productListPopularAdapter: ProductListAdapter by inject { parametersOf(VIEW_TYPE_ROUND) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        latestProductRv.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        latestProductRv.adapter = productListLatestAdapter

        popularProductRv.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        popularProductRv.adapter = productListPopularAdapter

        productListLatestAdapter.productEventListener=this
        productListPopularAdapter.productEventListener=this

        homeViewModel.productLatestLiveData.observe(viewLifecycleOwner) {
            Timber.tag("latest").i("product : ")
            productListLatestAdapter.products = it as ArrayList<Product>
        }
        homeViewModel.productPopularLiveData.observe(viewLifecycleOwner) {
            Timber.tag("popular").i("product : ")
            productListPopularAdapter.products= it as ArrayList<Product>
        }
        homeViewModel.progressBarLiveData.observe(viewLifecycleOwner) {
            setProgressIndicator(it)
        }

        viewLatestProductsBtn.setOnClickListener {
            startActivity(Intent(requireContext(),ProductListActivity::class.java).apply {
                putExtra(EXTRA_KEY_DATA, SORT_LATEST)
            })
        }

        viewPopularProductsBtn.setOnClickListener {
            startActivity(Intent(requireContext(),ProductListActivity::class.java).apply {
                putExtra(EXTRA_KEY_DATA, SORT_POPULAR)
            })
        }

        homeViewModel.bannerLiveData.observe(viewLifecycleOwner) {
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

    override fun onFavoriteBtnClick(product: Product) {
        homeViewModel.addProductToFavorite(product)
    }
}