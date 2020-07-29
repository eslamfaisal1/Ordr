package com.android.order.ui.cart

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.order.R
import com.android.order.adapters.CartProductsAdapter
import com.android.order.models.*
import com.android.order.networking.RetrofitClient
import kotlinx.android.synthetic.main.activity_cart.*
import kotlinx.android.synthetic.main.activity_cart.lead_address
import kotlinx.android.synthetic.main.activity_cart.lead_name
import kotlinx.android.synthetic.main.activity_cart.lead_phone
import kotlinx.android.synthetic.main.activity_cart.total_price
import kotlinx.android.synthetic.main.activity_product_details.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import com.android.order.views.CustomDialogClass

class CartActivity : AppCompatActivity(), CartProductsAdapter.Interaction {


    private val TAG = "CartActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_cart)


        initRecyclerView()

        CartViewModel.getInstance(applicationContext)?.getProducts()
            ?.observe(this, Observer { response ->
                when (response.status) {
                    Status.SUCCESS -> {

                        cartProductsAdapter.clear()

                        response.data?.let { productsList ->

                            if (productsList.isEmpty()) {
                                cart_details.visibility = View.GONE
                                empty_view.visibility = View.VISIBLE
                            } else {
                                cartProductsAdapter.insertProducts(productsList)
                                claclulateTotalPrice(productsList)
                                cart_details.visibility = View.VISIBLE
                                empty_view.visibility = View.GONE
                            }
                        }


                    }

                    Status.ERROR -> {

                    }
                    Status.LOADING -> {

                    }
                }
            })

        CartViewModel.getInstance(applicationContext)?.requestProducts()


    }

    private fun claclulateTotalPrice(productsList: List<Product>) {

        var totalPrice = 0.0

        productsList.forEach { product ->
            product.price?.let {
                val productPrice = product.count * product.price!!
                totalPrice += productPrice
            }

        }

        total_price.text = totalPrice.toString()

    }

    lateinit var cartProductsAdapter: CartProductsAdapter

    private fun initRecyclerView() {
        cartProductsAdapter = CartProductsAdapter(this, this)

        cart_products_recycler_view.apply {
            layoutManager = LinearLayoutManager(this@CartActivity)
            adapter = cartProductsAdapter
        }
    }

    fun back(view: View) {
        onBackPressed()
    }

    override fun onItemSelected(position: Int, item: Product) {

    }

    override fun onIncreaseCount(position: Int, item: Product) {
        item.count = item.count + 1
        CartViewModel.getInstance(applicationContext)?.updateProduct(item)
    }

    override fun onDecreaseCount(position: Int, item: Product) {
        item.count = item.count - 1
        if (item.count == 0) {
            CartViewModel.getInstance(applicationContext)?.deleteFromCart(item)
        } else
            CartViewModel.getInstance(applicationContext)?.updateProduct(item)
    }

    private fun setOrderInPlaceMultiOrder(): PlaceMultiOrder {

        val placeMultiOrder = PlaceMultiOrder()

        val lead = Lead()

        lead.name = lead_name.text.toString()
        lead.address = lead_address.text.toString()
        lead.mobile = lead_phone.text.toString()
        lead.address = lead_address.text.toString()
        lead.shortage = "ordr"
        lead.empcode = "null"

        placeMultiOrder.lead = lead


        val orderList: MutableList<Order> = ArrayList()

        cartProductsAdapter.getProductsList().forEach { product ->

            val order = Order()
            order.price = total_price.text.toString().toDouble().toInt()
            order.quantity = count.text.toString().toInt()
            order.productId = product.id

            orderList.add(order)
        }
        placeMultiOrder.orders = orderList


        return placeMultiOrder
    }

    fun makeOrder(view: View) {

        when {
            lead_name.text.trim().isEmpty() -> {
                lead_name.error = getString(R.string.please_enter_name)
                return
            }
            lead_phone.text.trim().isEmpty() -> {
                lead_name.error = getString(R.string.please_inter_phone)
                return
            }
            lead_address.text.trim().isEmpty() -> {
                lead_name.error = getString(R.string.please_enter_address)
                return
            }
            else -> {
                setOrderInPlaceMultiOrder()
                makeOrder()

            }
        }

    }


    private fun makeOrder() {
        CoroutineScope(Dispatchers.IO).launch {
            try {

                val response =
                    RetrofitClient.getInstance().api.placeMultiOrder(setOrderInPlaceMultiOrder())

                if (response.isSuccessful) {

                    GlobalScope.launch(Dispatchers.Main) {
                        val cdd = CustomDialogClass(this@CartActivity)
                        cdd.setCancelable(false)
                        cdd.show()
                    }


                } else {

                    GlobalScope.launch(Dispatchers.Main) {
                        Toast.makeText(
                            this@CartActivity,
                            response.message(),
                            Toast.LENGTH_LONG
                        ).show()
                    }

                }

            } catch (e: Throwable) {

                Log.d("eslamfaisalproducts", e.message!!)

                GlobalScope.launch(Dispatchers.Main) {

                    Toast.makeText(
                        this@CartActivity,
                        e.message!!,
                        Toast.LENGTH_LONG
                    ).show()

                }


            }
        }

    }

}