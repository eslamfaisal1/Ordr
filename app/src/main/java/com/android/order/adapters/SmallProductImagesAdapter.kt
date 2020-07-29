package com.android.order.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.order.R
import com.facebook.drawee.view.SimpleDraweeView

class SmallProductImagesAdapter(private val productImages: List<String>) :
    RecyclerView.Adapter<SmallProductImagesAdapter.Holder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): Holder {
        return Holder(
            LayoutInflater.from(
                parent.context
            ).inflate(R.layout.small_product_image_item_layout, parent, false)
        )
    }

    override fun onBindViewHolder(
        holder: Holder,
        position: Int
    ) {
        holder.image.setImageURI(productImages[position])
    }

    override fun getItemCount(): Int {
        return productImages.size
    }

    inner class Holder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var image: SimpleDraweeView = itemView.findViewById(R.id.product_image)

    }

}