package com.example.niketest.feature.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.niketest.R
import com.example.niketest.data.Product
import com.example.niketest.services.ImageLoadingService
import com.example.niketest.view.NikeImageView

class SearchProductsAdapter(
    val searchProducts: List<Product>,
    val imageLoadingService: ImageLoadingService,
    val searchProductsEventListener: SearchProductsEventListener
) : RecyclerView.Adapter<SearchProductsAdapter.SearchViewHolder>() {

    inner class SearchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTv = itemView.findViewById<TextView>(R.id.productTitleTv)
        val productIv = itemView.findViewById<NikeImageView>(R.id.nikeImageView)
        fun bind(product: Product) {
            imageLoadingService.load(productIv, product.image)
            titleTv.text = product.title
            itemView.setOnClickListener {
                searchProductsEventListener.onClick(product)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder =
        SearchViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_favorite_product, parent, false)
        )

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) =
        holder.bind(searchProducts[position])

    override fun getItemCount(): Int = searchProducts.size

    interface SearchProductsEventListener {
        fun onClick(product: Product)
    }

}