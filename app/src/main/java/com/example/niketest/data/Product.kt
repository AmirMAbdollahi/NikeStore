package com.example.niketest.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "products")
@Parcelize
data class Product(
    val discount: Int,
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val image: String,
    val previous_price: Int,
    val price: Int,
    val status: Int,
    val title: String
) : Parcelable {
    var isFavorite: Boolean = false
}

const val SORT_LATEST = 0
const val SORT_POPULAR = 1
const val SORT_PRICE_DESC = 2
const val SORT_PRICE_ASC = 3