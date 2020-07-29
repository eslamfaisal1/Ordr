package com.android.order.ui.cart

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.android.order.db.AppDatabase
import com.android.order.models.Product
import com.android.order.models.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CartViewModel {


    companion object {

        lateinit var context: Context

        private var INSTANCE: CartViewModel? = null
        fun getInstance(context: Context): CartViewModel? {
            this.context = context
            if (INSTANCE == null) {
                synchronized(AppDatabase::class) {
                    INSTANCE = CartViewModel()
                }
            }
            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }

    private val _productsLst: MutableLiveData<Resource<List<Product>>> =
        MutableLiveData<Resource<List<Product>>>()

    private val _productByID: MutableLiveData<Resource<Product>> =
        MutableLiveData<Resource<Product>>()

    fun getProducts(): LiveData<Resource<List<Product>>> {
        return this._productsLst
    }

    fun getProductByID(): LiveData<Resource<Product>> {
        return this._productByID
    }

    fun receteProductById(){
        _productByID.value  = null
    }
    fun requestProducts() {
        CoroutineScope(Dispatchers.IO).launch {
            try {

                AppDatabase.getInstance(context)?.let {
                    val list: List<Product> = it.userDao().getAll()

                    setProductsListResponse(Resource.success(list))

                }
            } catch (e: Throwable) {

                Log.d("eslamfaisalproducts", e.message!!)
            }
        }

    }

    fun updateProduct(product: Product) {

        CoroutineScope(Dispatchers.IO).launch {
            try {
                AppDatabase.getInstance(context)?.let {
                    it.userDao().updateTour(product.count, product.id!!)
                    requestProducts()
                }
            } catch (e: Throwable) {

                Log.d("eslamfaisalproducts", e.message!!)
            }
        }

    }

    fun requestProductByID(product: Product) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                AppDatabase.getInstance(context)?.let {
                    val list: List<Product> = it.userDao().loadByIds(product.id!!)

                    setProductByIDResponse(Resource.success(list[0]))
                }
            } catch (e: Throwable) {

                Log.d("eslamfaisalproducts", e.message!!)
            }
        }

    }

    fun insertAllToDB(list: List<Product>) {
        CoroutineScope(Dispatchers.IO).launch {
            try {

                AppDatabase.getInstance(context)?.let {

                    list.forEach { produc: Product ->
                        it.userDao().insertAll(produc)
                    }

                }
                requestProducts()

            } catch (e: Throwable) {

                Log.d("eslamfaisalproducts", e.message!!)
            }
        }

    }


    fun deleteFromCart(product: Product) {
        CoroutineScope(Dispatchers.IO).launch {
            try {

                AppDatabase.getInstance(context)?.userDao()?.delete(product)

                requestProducts()
                Log.d("shouditdeleted", "e.message!!")

            } catch (e: Throwable) {

                Log.d("eslamfaisalproducts", e.message!!)
            }
        }
    }

    fun insertOneToDB(product: Product) {
        CoroutineScope(Dispatchers.IO).launch {
            try {

                AppDatabase.getInstance(context)?.userDao()?.insertAll(product)
                requestProductByID(product)

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

    private fun setProductByIDResponse(response: Resource<Product>) {
        GlobalScope.launch(Dispatchers.Main) {

            _productByID.value = response

        }

    }


}
