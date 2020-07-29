package com.android.order.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.android.order.models.Product

@Dao
interface ProductsDao {
    @Query("SELECT * FROM cart_products")
    fun getAll(): List<Product>

    @Query("SELECT * FROM cart_products WHERE id IN (:id)")
    fun loadByIds(id: String): List<Product>

    @Insert
    fun insertAll(vararg products: Product)

    @Insert
    fun insertProduct(product: Product)

    @Delete
    fun delete(product: Product)

    @Query("UPDATE cart_products SET count = :newCount WHERE id = :id")
    fun updateTour(newCount: Int, id: String)
}