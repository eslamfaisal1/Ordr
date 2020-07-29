package com.android.order.ui.product_details

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.order.models.ProductDetails
import com.android.order.models.Resource
import com.android.order.networking.RetrofitClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ProductDetailsViewModel : ViewModel() {

    private val _productDetails: MutableLiveData<Resource<ProductDetails>> =
        MutableLiveData<Resource<ProductDetails>>()

    fun getProducts(): LiveData<Resource<ProductDetails>> {
        return this._productDetails
    }

    fun requestProducts(productID: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {

                val response = RetrofitClient.getInstance().api.getProductDetails(productID)

                if (response.isSuccessful) {
                    response.body()?.let {
                        setProductsListResponse(Resource.success(it))
                    } ?: Log.d("eslamfaisalproducts", response.message())

                } else {
                    Log.d("eslamfaisalproducts", response.message())
                }


            } catch (e: Throwable) {

                Log.d("eslamfaisalproducts", e.message!!)
            }
        }

    }

    private fun setProductsListResponse(response: Resource<ProductDetails>) {
        GlobalScope.launch(Dispatchers.Main) {

            _productDetails.value = response

        }

    }
}
