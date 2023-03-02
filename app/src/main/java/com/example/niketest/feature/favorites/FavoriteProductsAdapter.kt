package com.example.niketest.feature.favorites

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.niketest.R
import com.example.niketest.data.Product
import com.example.niketest.services.ImageLoadingService
import com.example.niketest.view.NikeImageView

class FavoriteProductsAdapter(
    val products: MutableList<Product>,
    val imageLoadingService: ImageLoadingService,
    val favoriteProductEventListener: FavoriteProductEventListener? = null
) :
    RecyclerView.Adapter<FavoriteProductsAdapter.FavoriteProductViewHolder>() {

    inner class FavoriteProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTv = itemView.findViewById<TextView>(R.id.productTitleTv)
        val productIv = itemView.findViewById<NikeImageView>(R.id.nikeImageView)
        fun bindProduct(product: Product) {
            titleTv.text = product.title
            imageLoadingService.load(productIv, product.image)
            itemView.setOnClickListener {
                favoriteProductEventListener!!.onClick(product)
            }
            itemView.setOnLongClickListener {
                products.remove(product)
                notifyItemRemoved(adapterPosition)
                favoriteProductEventListener!!.onLongClick(product)
                return@setOnLongClickListener false
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteProductViewHolder {
        return FavoriteProductViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_favorite_product, parent, false)
        )
    }


    override fun onBindViewHolder(holder: FavoriteProductViewHolder, position: Int) =
        holder.bindProduct(products[position])

    override fun getItemCount(): Int = products.size

    interface FavoriteProductEventListener{
        fun onClick(product: Product)
        fun onLongClick(product: Product)
    }

}