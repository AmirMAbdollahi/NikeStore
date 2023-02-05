package com.example.niketest.feature.cart

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.niketest.R
import com.example.niketest.common.formatPrice
import com.example.niketest.data.CartItem
import com.example.niketest.data.PurchaseDetail
import com.example.niketest.services.ImageLoadingService
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_cart.view.*
import kotlinx.android.synthetic.main.item_purchase_details.view.*

const val VIEW_TYPE_CART_ITEM = 0
const val VIEW_TYPE_PURCHASE_DETAILS = 1

class CartItemAdapter(
    val cartItems: MutableList<CartItem>,
    val imageLoadingService: ImageLoadingService,
    val cartItemViewCallBacks: CartItemViewCallBacks
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var purchaseDetail: PurchaseDetail? = null

    inner class CartItemViewHolder(override val containerView: View) :
        RecyclerView.ViewHolder(containerView), LayoutContainer {
        fun bindCartItem(cartItem: CartItem) {
            imageLoadingService.load(containerView.productIv, cartItem.product.image)
            containerView.productTitleTv.text = cartItem.product.title
            containerView.previousPriceTv.text =
                formatPrice(cartItem.product.price + cartItem.product.discount)
            containerView.priceTv.text = formatPrice(cartItem.product.price)
            containerView.cartItemCountTv.text = cartItem.count.toString()

            containerView.changeCountProgressBar.visibility =
                if (cartItem.changeCountProgressBarIsVisible) View.VISIBLE else View.GONE

            containerView.cartItemCountTv.visibility =
                if (cartItem.changeCountProgressBarIsVisible) View.INVISIBLE else View.VISIBLE

            containerView.removeFromCartBtn.setOnClickListener {
                cartItemViewCallBacks.onRemoveCartItemButtonClick(cartItem)
            }
            containerView.increaseBtn.setOnClickListener {
                if (cartItem.count < 5){
                    cartItem.changeCountProgressBarIsVisible = true
                    containerView.changeCountProgressBar.visibility = View.VISIBLE
                    containerView.cartItemCountTv.visibility = View.INVISIBLE
                    cartItemViewCallBacks.onIncreaseCartItemButtonClick(cartItem)
                }
            }
            containerView.decreaseBtn.setOnClickListener {
                if (cartItem.count > 1) {
                    cartItem.changeCountProgressBarIsVisible = true
                    containerView.changeCountProgressBar.visibility = View.VISIBLE
                    containerView.cartItemCountTv.visibility = View.INVISIBLE
                    cartItemViewCallBacks.onDecreaseCartItemButtonClick(cartItem)
                }
            }
            containerView.productIv.setOnClickListener {
                cartItemViewCallBacks.onProductImageClick(cartItem)
            }
        }
    }

    inner class PurchaseDetailViewHolder(override val containerView: View) :
        RecyclerView.ViewHolder(containerView), LayoutContainer {
        fun bind(totalPrice: Int, shippingCost: Int, payablePrice: Int) {
            containerView.totalPriceTv.text = formatPrice(totalPrice)
            containerView.shippingCostTv.text = formatPrice(shippingCost)
            containerView.payablePriceTv.text = formatPrice(payablePrice)
        }
    }


    interface CartItemViewCallBacks {
        fun onRemoveCartItemButtonClick(cartItem: CartItem)
        fun onIncreaseCartItemButtonClick(cartItem: CartItem)
        fun onDecreaseCartItemButtonClick(cartItem: CartItem)
        fun onProductImageClick(cartItem: CartItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_CART_ITEM) {
            CartItemViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.item_cart, parent, false
                )
            )
        } else {
            PurchaseDetailViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.item_purchase_details, parent, false
                )
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is CartItemViewHolder) {
            holder.bindCartItem(cartItems[position])
        } else if (holder is PurchaseDetailViewHolder) {
            purchaseDetail?.let {
                holder.bind(it.total_price, it.shipping_cost, it.payable_price)
            }
        }
    }

    override fun getItemCount(): Int = cartItems.size + 1

    override fun getItemViewType(position: Int): Int {
        return if (position == cartItems.size) {
            VIEW_TYPE_PURCHASE_DETAILS
        } else VIEW_TYPE_CART_ITEM
    }

    fun removeCartItem(cartItem: CartItem) {
        val index = cartItems.indexOf(cartItem)
        if (index > -1) {
            cartItems.removeAt(index)
            notifyItemRemoved(index)
        }
    }

    fun increaseCount(cartItem: CartItem) {
        val index = cartItems.indexOf(cartItem)
        if (index > -1) {
            cartItems[index].changeCountProgressBarIsVisible = false
            notifyItemChanged(index)
        }
    }

    fun decreaseCount(cartItem: CartItem) {
        val index = cartItems.indexOf(cartItem)
        if (index > -1) {
            cartItems[index].changeCountProgressBarIsVisible = false
            notifyItemChanged(index)
        }
    }

}