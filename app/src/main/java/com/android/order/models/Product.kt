package com.android.order.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable


@Entity(tableName = "cart_products")
class Product : Serializable {

    @PrimaryKey
    @SerializedName("pid")
    @Expose
    var pid: Int? = null

    @ColumnInfo(name = "id")
    @SerializedName("id")
    @Expose
    var id: String? = null

    @ColumnInfo(name = "name")
    @SerializedName("name")
    @Expose
    var name: String? = null

    @ColumnInfo(name = "nameInArabic")
    @SerializedName("nameInArabic")
    @Expose
    var nameInArabic: String? = null

    @ColumnInfo(name = "description")
    @SerializedName("description")
    @Expose
    var description: String? = null

    @ColumnInfo(name = "notes")
    @SerializedName("notes")
    @Expose
    var notes: String? = null

    @ColumnInfo(name = "heroImage")
    @SerializedName("heroImage")
    @Expose
    var heroImage: String? = null

    @ColumnInfo(name = "price")
    @SerializedName("price")
    @Expose
    var price: Double? = null

    @ColumnInfo(name = "stock")
    @SerializedName("stock")
    @Expose
    var stock: Int? = null

    @ColumnInfo(name = "category")
    @SerializedName("category")
    @Expose
    var category: String? = null

    @ColumnInfo(name = "discount")
    @SerializedName("discount")
    @Expose
    var discount: Double? = null

    @ColumnInfo(name = "count")
    @SerializedName("count")
    @Expose
    var count: Int = 1


    override fun toString(): String {
        return "Product(id=$id, name=$name, nameInArabic=$nameInArabic, description=$description, notes=$notes, heroImage=$heroImage, price=$price, stock=$stock, category=$category, discount=$discount)"
    }


}