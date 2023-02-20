package com.example.niketest.feature.favorites

import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.niketest.R
import com.example.niketest.common.EXTRA_KEY_DATA
import com.example.niketest.common.NikeActivity
import com.example.niketest.data.Product
import com.example.niketest.feature.product.ProductDetailActivity
import kotlinx.android.synthetic.main.activity_favorites_product.*
import kotlinx.android.synthetic.main.view_default_empty_state.*
import org.koin.android.ext.android.get
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoritesProductActivity : NikeActivity(),FavoriteProductsAdapter.FavoriteProductEventListener {

    val viewModel:FavoriteProductsViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorites_product)

        helpBtn.setOnClickListener {
            showSnackBar(getString(R.string.favoriteHelpMessage))
        }

        viewModel.favoriteProductLiveData.observe(this){
            if(it.isNotEmpty()){
                favoritesProductRv.layoutManager=LinearLayoutManager(this,RecyclerView.VERTICAL,false)
                favoritesProductRv.adapter=FavoriteProductsAdapter(it as MutableList<Product>,get(),this)
            }else {
                showEmptyState(R.layout.view_default_empty_state)
                emptyStateMessageTv.text=getString(R.string.emptyStateFavorite)
            }

        }

    }

    override fun onClick(product: Product) {
        startActivity(Intent(this,ProductDetailActivity::class.java).apply {
            putExtra(EXTRA_KEY_DATA,product)
        })
    }

    override fun onLongClick(product: Product) {
        viewModel.removeFromFavorite(product)
        viewModel.favoriteProductLiveData.observe(this){
            if (it.isEmpty()){
                showEmptyState(R.layout.view_default_empty_state)
                emptyStateMessageTv.text=getString(R.string.emptyStateFavorite)
            }
        }
    }
}