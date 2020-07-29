package com.android.order.adapters


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.android.order.R
import com.android.order.models.Product
import com.facebook.drawee.view.SimpleDraweeView

class CartProductsAdapter(
    private val interaction: Interaction, val context: FragmentActivity
) : RecyclerView.Adapter<CartProductsAdapter.BlogPostViewHolder>() {


    private val listProfucts: MutableList<Product> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BlogPostViewHolder {

        return BlogPostViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.cart_product_item,
                parent,
                false
            )
        )

    }


    override fun onBindViewHolder(holder: BlogPostViewHolder, position: Int) {

        val product: Product = listProfucts[position]
        holder.product_image.setImageURI(product.heroImage)

        holder.name.text = product.name
        holder.count.text = product.count.toString()
        product.price?.let {
            val total: Double = product.price!! * product.count
            holder.item_total_price.text = " " + (total).toString()
            holder.item_price.text = " " + product.price.toString()
        }


        holder.itemView.setOnClickListener {
            interaction.onItemSelected(holder.adapterPosition, product)
        }

        holder.minus.setOnClickListener {
            interaction.onDecreaseCount(holder.adapterPosition, product)
        }

        holder.plus.setOnClickListener {
            interaction.onIncreaseCount(holder.adapterPosition, product)
        }

    }


    override fun getItemCount(): Int {
        return listProfucts.size
    }


    fun insertProducts(list: List<Product>) {
        listProfucts.addAll(list)
        notifyDataSetChanged()
    }

    fun clear() {
        listProfucts.clear()
        notifyDataSetChanged()
    }

    class BlogPostViewHolder
    constructor(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {

        var product_image: SimpleDraweeView = itemView.findViewById(R.id.product_image)

        var name: TextView = itemView.findViewById(R.id.title)
        var item_total_price: TextView = itemView.findViewById(R.id.item_total_price)
        var item_price: TextView = itemView.findViewById(R.id.item_price)
        var count: TextView = itemView.findViewById(R.id.count)
        var minus: ImageView = itemView.findViewById(R.id.minus)
        var plus: ImageView = itemView.findViewById(R.id.plus)


    }

    interface Interaction {
        fun onItemSelected(position: Int, item: Product)
        fun onIncreaseCount(position: Int, item: Product)
        fun onDecreaseCount(position: Int, item: Product)
    }

    fun removeItem(position: Int) {
        listProfucts.removeAt(position)
        notifyDataSetChanged()
    }

    fun getProductsList(): List<Product> {
        return listProfucts
    }

}


