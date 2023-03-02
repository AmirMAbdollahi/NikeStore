package com.example.niketest.feature.search

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.core.view.children
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.niketest.R
import com.example.niketest.common.EXTRA_KEY_DATA
import com.example.niketest.common.NikeActivity
import com.example.niketest.data.EmptyState
import com.example.niketest.data.Product
import com.example.niketest.feature.product.ProductDetailActivity
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.view_cart_empty_state.*
import kotlinx.android.synthetic.main.view_cart_empty_state.view.*
import kotlinx.android.synthetic.main.view_default_empty_state.*
import org.koin.android.ext.android.get
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchActivity : NikeActivity(), SearchProductsAdapter.SearchProductsEventListener {
    val viewModel: SearchViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)


        etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                if (s != null) {
                    viewModel.searchProduct(s.toString())
                }
            }

        })

        viewModel.progressBarLiveData.observe(this) {
            setProgressIndicator(it)
        }

        viewModel.searchLiveData.observe(this) {
            searchRv.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

            searchRv.adapter = SearchProductsAdapter(it, get(), this)
        }

        viewModel.searchEmptyStatLiveData.observe(this) {
            showSearchEmptyState(it)
        }

        backBtn.setOnClickListener {
            finish()
        }

    }

    fun showSearchEmptyState(emptyState: EmptyState) {
        if (emptyState.mustShow) {
            val emptyStateView = showEmptyState(R.layout.view_default_empty_state)
            emptyStateView?.let { view ->
                view.emptyStateMessageTv.text = getString(emptyState.messageResId)
            }
        } else {
//            searchActivity.children.filter { it.tag == "emptyState" }.forEach {
//                searchActivity.removeView(it)
//                println("Amir remove")
//            }
            emptyStateView?.visibility = View.GONE
            println("Amir Gone")
        }
    }

    override fun onClick(product: Product) {
        startActivity(Intent(this, ProductDetailActivity::class.java).apply {
            putExtra(EXTRA_KEY_DATA, product)
        })
    }
}