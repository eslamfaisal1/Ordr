package com.android.order.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.order.models.Product
import com.android.order.models.Resource
import com.android.order.networking.RetrofitClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ProductsViewModel : ViewModel() {

    private val _productsLst: MutableLiveData<Resource<List<Product>>> =
        MutableLiveData<Resource<List<Product>>>()

    fun getProducts(): LiveData<Resource<List<Product>>> {
        return this._productsLst
    }

    fun requestProducts() {
        CoroutineScope(Dispatchers.IO).launch {
            try {

                val response = RetrofitClient.getInstance().api.getProducts()

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

    private fun setProductsListResponse(response: Resource<List<Product>>) {
        GlobalScope.launch(Dispatchers.Main) {

            _productsLst.value = response

        }

    }


}
