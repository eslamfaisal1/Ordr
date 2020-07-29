package com.android.order.ui.product_details
import com.android.order.views.CustomDialogClass

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.order.Constants
import com.android.order.Constants.PRODUCT
import com.android.order.R
import com.android.order.adapters.SmallProductImagesAdapter
import com.android.order.models.*
import com.android.order.models.Status.*
import com.android.order.networking.RetrofitClient
import com.android.order.ui.cart.CartViewModel
import com.android.order.ui.cart.CartViewModel.Companion.context
import com.android.order.utils.LanguageSharedHelper
import kotlinx.android.synthetic.main.activity_product_details.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ProductDetailsActivity : AppCompatActivity() {

    lateinit var productDetailsViewModel: ProductDetailsViewModel
    lateinit var productArgument: Product
    lateinit var productDB: Product
    private var productIsInCart = false
    lateinit var productDetails: ProductDetails
    lateinit var placeSingleOrder: PlaceSingleOrder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_details)

        productArgument = intent.getSerializableExtra(PRODUCT) as Product

        productDetailsViewModel = ViewModelProvider(this).get(ProductDetailsViewModel::class.java)

        productDetailsViewModel.getProducts().observe(this, Observer {
            when (it.status) {
                SUCCESS -> {
                    it.data?.let {
                        setProductData(it)
                        initSingleOrder()
                    }

                }
                ERROR -> {

                }
                LOADING -> {

                }
            }
        })

        productDetailsViewModel.requestProducts(productArgument.id!!)

        CartViewModel.getInstance(applicationContext)?.requestProductByID(productArgument)

        CartViewModel.getInstance(applicationContext)?.getProductByID()?.observe(this, Observer {
            if (it != null)
            {
                when (it.status) {
                    SUCCESS -> {
                        checkExists(it.data)
                        CartViewModel.getInstance(applicationContext)?.receteProductById()
                    }
                    ERROR -> {

                    }
                    LOADING -> {

                    }
                }
            }

        })


    }

    private fun initSingleOrder() {

        setOrderInPlaceSingleOrder()
    }

    private fun setOrderInPlaceSingleOrder() {
        placeSingleOrder = PlaceSingleOrder()

        val lead = Lead()

        lead.name = lead_name.text.toString()
        lead.address = lead_address.text.toString()
        lead.mobile = lead_phone.text.toString()
        lead.address = lead_address.text.toString()
        lead.shortage = "ordr"
        lead.empcode = "null"

        placeSingleOrder.lead = lead

        val order = Order()

        order.price = total_price.text.toString().toDouble().toInt()
        order.quantity = count.text.toString().toInt()
        order.productId = productArgument.id

        placeSingleOrder.order = order
    }

    private fun checkExists(data: Product?) {
        productDB = data!!
        if (productDB.id == productArgument.id) {
            productIsInCart = true
            cart_button.setCardBackgroundColor(resources.getColor(R.color.colorYellow))
            cart_button_image.setImageDrawable(resources.getDrawable(R.drawable.ic_remove_from_cart))
        }

    }

    private val TAG = "ProductDetailsActivity"

    @SuppressLint("SetJavaScriptEnabled")
    private fun setProductData(it: ProductDetails) {
        productDetails = it
        product_image.setImageURI(it.heroImage)
        when (LanguageSharedHelper.getLanguage(this, Constants.LANGUAGE)) {
            "ar" -> {
                product_name.text = it.nameInArabic
            }

            "en" -> {
                product_name.text = it.name
            }
        }

        description.settings.javaScriptEnabled = true
        description.loadDataWithBaseURL("", it.description, "text/html", "UTF-8", "")

        images_recycler_view.apply {
            layoutManager = LinearLayoutManager(
                this@ProductDetailsActivity,
                LinearLayoutManager.HORIZONTAL,
                false
            )
            adapter = SmallProductImagesAdapter(it.carouselsUrls)
        }

        left_in_stock.text = "${it.stock} ${getString(R.string.left_in_stock)}"
        total_price.text = "${it.price}"
    }

    fun back(view: View) {
        onBackPressed()
    }

    fun addToCart(view: View) {
        if (!productIsInCart)
            CartViewModel.getInstance(applicationContext)?.insertOneToDB(productArgument)
        else {
            makeNotInCart()
        }
    }

    private fun makeNotInCart() {
        CartViewModel.getInstance(applicationContext)?.deleteFromCart(productDB)
        productIsInCart = false
        cart_button.setCardBackgroundColor(resources.getColor(R.color.colorAccent))
        cart_button_image.setImageDrawable(resources.getDrawable(R.drawable.ic_add_to_cart))
    }

    @SuppressLint("SetTextI18n")
    fun decreaseCunt(view: View) {
        val oldCount: Int = count.text.toString().toInt()
        if (oldCount > 1) {
            val newCount = oldCount - 1
            count.text = (newCount).toString()

            val newTotalPrice = newCount * productDetails.price
            total_price.text = newTotalPrice.toString()
        }

        setOrderInPlaceSingleOrder()

    }

    fun increaseCount(view: View) {
        val oldCount: Int = count.text.toString().toInt()
        val newCount = oldCount + 1
        count.text = (newCount).toString()
        val newTotalPrice = newCount * productDetails.price
        total_price.text = newTotalPrice.toString()
        setOrderInPlaceSingleOrder()

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
                setOrderInPlaceSingleOrder()
                makeOrder()

            }
        }

    }

    private fun makeOrder() {
        CoroutineScope(Dispatchers.IO).launch {
            try {

                val response = RetrofitClient.getInstance().api.placeSingleOrder(placeSingleOrder)

                if (response.isSuccessful) {

                    GlobalScope.launch(Dispatchers.Main) {

                        val cdd = CustomDialogClass(this@ProductDetailsActivity)
                        cdd.show()

                    }


                } else {

                    GlobalScope.launch(Dispatchers.Main) {
                        Toast.makeText(
                            this@ProductDetailsActivity,
                            response.message(),
                            Toast.LENGTH_LONG
                        ).show()
                    }

                }

            } catch (e: Throwable) {

                Log.d("eslamfaisalproducts", e.message!!)

                GlobalScope.launch(Dispatchers.Main) {

                    Toast.makeText(
                        this@ProductDetailsActivity,
                        e.message!!,
                        Toast.LENGTH_LONG
                    ).show()

                }


            }
        }

    }


}