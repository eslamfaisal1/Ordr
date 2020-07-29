package com.android.order.adapters


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.android.order.R
import com.android.order.models.Product
import com.facebook.drawee.view.SimpleDraweeView

class ProductsAdapter(
    private val interaction: Interaction, val context: FragmentActivity
) : RecyclerView.Adapter<ProductsAdapter.BlogPostViewHolder>() {


    private val listProfucts: MutableList<Product> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BlogPostViewHolder {

        return BlogPostViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.product_item,
                parent,
                false
            )
        )

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
        var discount: TextView = itemView.findViewById(R.id.discount)
        var price: TextView = itemView.findViewById(R.id.price)


    }

    interface Interaction {
        fun onItemSelected(position: Int, item: Product)

    }

    fun removeItem(position: Int) {
        listProfucts.removeAt(position)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: BlogPostViewHolder, position: Int) {

        val product: Product = listProfucts[position]
        holder.product_image.setImageURI(product.heroImage)

        holder.name.text = product.name
        holder.discount.text = product.discount.toString()
        holder.price.text = product.price.toString()

        holder.itemView.setOnClickListener {
            interaction.onItemSelected(holder.adapterPosition, product)
        }

    }


}


