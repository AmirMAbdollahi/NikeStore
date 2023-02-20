package com.example.niketest.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.niketest.data.Product
import com.example.niketest.data.repo.sourse.ProductLocalDataSource

@Database(entities = [Product::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun productDao():ProductLocalDataSource

}