package com.android.order.networking

import com.android.order.models.PlaceMultiOrder
import com.android.order.models.PlaceSingleOrder
import com.android.order.models.Product
import com.android.order.models.ProductDetails
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface Api {


    @GET("products/get")
    suspend fun getProducts(): Response<List<Product>>

    @GET("products/info")
    suspend fun getProductDetails(@Query("productId") productId: String): Response<ProductDetails>

    @POST("land/placeOrder")
    suspend fun placeSingleOrder(@Body singleOrder: PlaceSingleOrder): Response<ProductDetails>

    @POST("land/placeOrders")
    suspend fun placeMultiOrder(@Body singleOrder: PlaceMultiOrder): Response<ProductDetails>


}