package com.example.niketest.feature.list

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.example.niketest.R
import com.example.niketest.common.EXTRA_KEY_DATA
import com.example.niketest.common.NikeActivity
import com.example.niketest.data.Product
import com.example.niketest.feature.common.ProductListAdapter
import com.example.niketest.feature.common.VIEW_TYPE_LARGE
import com.example.niketest.feature.common.VIEW_TYPE_SMALL
import com.example.niketest.feature.product.ProductDetailActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.android.synthetic.main.activity_product_list.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import timber.log.Timber

class ProductListActivity : NikeActivity(),ProductListAdapter.OnProductClickListener {
    val productListViewModel: ProductListViewModel by viewModel {
        parametersOf(
            intent.extras!!.getInt(
                EXTRA_KEY_DATA
            )
        )
    }

    val productListAdapter: ProductListAdapter by inject { parametersOf(VIEW_TYPE_SMALL) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_list)
        val gridLayoutManager = GridLayoutManager(this, 2)
        productsRv.layoutManager = gridLayoutManager
        productsRv.adapter = productListAdapter

        productListAdapter.onProductClickListener=this

        viewTypeChangerBtn.setImageResource(R.drawable.ic_view_type_large)
        viewTypeChangerBtn.setOnClickListener {
            if (productListAdapter.viewType == VIEW_TYPE_SMALL) {
                viewTypeChangerBtn.setImageResource(R.drawable.ic_grid)
                productListAdapter.viewType = VIEW_TYPE_LARGE
                gridLayoutManager.spanCount = 1
                productListAdapter.notifyDataSetChanged()
            } else {
                viewTypeChangerBtn.setImageResource(R.drawable.ic_view_type_large)
                productListAdapter.viewType = VIEW_TYPE_SMALL
                gridLayoutManager.spanCount = 2
                productListAdapter.notifyDataSetChanged()
            }

        }

        productListViewModel.selectedSortTitleLiveData.observe(this) {
            selectecSortTitleTv.text=getString(it)
        }

        productListViewModel.progressBarLiveData.observe(this){
            setProgressIndicator(it)
        }

        productListViewModel.productsLiveData.observe(this) {
            Timber.i(it.toString())
            productListAdapter.products = it as ArrayList<Product>
        }

        toolbarView.onBackButtonClickListener= View.OnClickListener {
            finish()
        }

        sortBtn.setOnClickListener {
            val dialog = MaterialAlertDialogBuilder(this)
                .setSingleChoiceItems(R.array.sortTitleArray, productListViewModel.sort)
                { dialog, selectedSortIndex ->
                    dialog.dismiss()
                    productListViewModel.onSelectedSortChangeByUser(selectedSortIndex) }
                .setTitle(getString(R.string.sort))
            dialog.show()
        }

    }

    override fun onClickProduct(product: Product) {
        startActivity(Intent(this,ProductDetailActivity::class.java).apply {
            putExtra(EXTRA_KEY_DATA,product)
        })
    }
}