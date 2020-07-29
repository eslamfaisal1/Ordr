package com.android.order.ui

import android.content.Intent
import android.graphics.PorterDuff
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.viewpager.widget.ViewPager
import com.android.order.*
import com.android.order.Constants.PRODUCT
import com.android.order.adapters.AdapterImageSlider
import com.android.order.adapters.ProductsAdapter
import com.android.order.models.Image
import com.android.order.models.Product
import com.android.order.models.Status.*
import com.android.order.ui.cart.CartActivity
import com.android.order.ui.cart.CartViewModel
import com.android.order.ui.product_details.ProductDetailsActivity
import com.android.order.ui.viewmodel.ProductsViewModel
import com.android.order.utils.LanguageSharedHelper
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), ProductsAdapter.Interaction {

    lateinit var adapterImageSlider: AdapterImageSlider

    lateinit var productsAdapter: ProductsAdapter

    lateinit var productsViewModel: ProductsViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setLangauge()
        productsViewModel = ViewModelProvider(this).get(ProductsViewModel::class.java)

        val images: MutableList<Image> = ArrayList()

        productsViewModel.getProducts().observe(this, Observer { response ->

            when (response.status) {
                SUCCESS -> {
                    val producList: List<Product>? = response.data
                    producList?.forEach { product: Product ->
                        images.add(
                            Image(
                                id = "1",
                                url = product.heroImage
                            )
                        )
                    }

                    initImageSliderComponent(images)
                    response.data?.let {
                        productsAdapter.insertProducts(it)
                    }

//                    producList?.let {
//                        CartViewModel.getInstance(this)?.insertAllToDB(it)
//                    }


                }
                ERROR -> {

                }
                LOADING -> {

                }
            }
        })

        getProducts()

        initProductsRecycler()

        CartViewModel.getInstance(applicationContext)?.getProducts()?.observe(this, Observer {
            when (it.status) {
                SUCCESS -> {
                    Log.d(TAG, "CartProductsLis: " + it.data.toString())
                }
                ERROR -> {

                }
                LOADING -> {

                }
            }
        })

    }

    private val TAG = "MainActivity"
    private fun initProductsRecycler() {
        productsAdapter = ProductsAdapter(this, this)
        products_recycler_view.apply {
            layoutManager = GridLayoutManager(this@MainActivity, 2)
            adapter = productsAdapter

        }

    }


    private fun getProducts() {

        productsViewModel.requestProducts()

    }

    fun goCart(view: View) {
        val intent = Intent(this, CartActivity::class.java)

        startActivity(intent)

    }

    private fun initImageSliderComponent(images: List<Image>) {

        adapterImageSlider = AdapterImageSlider(this, ArrayList())
        adapterImageSlider.setItems(images)
        viewPager.adapter = adapterImageSlider

        // displaying selected image first
        viewPager.currentItem = 0
        addBottomDots(layout_dots, adapterImageSlider.count, 0)
        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                pos: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(pos: Int) {
                addBottomDots(layout_dots, adapterImageSlider.count, pos)
            }

            override fun onPageScrollStateChanged(state: Int) {}
        })


    }

    private fun addBottomDots(
        layout_dots: LinearLayout,
        size: Int,
        current: Int
    ) {
        val dots = arrayOfNulls<ImageView>(size)
        layout_dots.removeAllViews()
        for (i in dots.indices) {
            dots[i] = ImageView(this)
            val width_height = 20
            val params =
                LinearLayout.LayoutParams(ViewGroup.LayoutParams(width_height, width_height))
            params.setMargins(10, 10, 10, 10)
            dots[i]!!.layoutParams = params
            dots[i]!!.setImageResource(R.drawable.shape_circle)
            dots[i]!!.setColorFilter(
                ContextCompat.getColor(this, R.color.overlay_dark_10),
                PorterDuff.Mode.SRC_ATOP
            )
            layout_dots.addView(dots[i])
        }
        if (dots.isNotEmpty()) {
            dots[current]!!.setColorFilter(
                ContextCompat.getColor(this, R.color.colorAccent),
                PorterDuff.Mode.SRC_ATOP
            )
        }
    }

    override fun onItemSelected(position: Int, item: Product) {

        val intent = Intent(this, ProductDetailsActivity::class.java)

        intent.putExtra(PRODUCT, item)

        startActivity(intent)
    }

    fun changeLanguage(view: View) {
        changeLanguage(this)
    }

    fun setLangauge(){
        language.text = LanguageSharedHelper.getLanguage(this, Constants.LANGUAGE);
    }

}