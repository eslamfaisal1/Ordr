package com.android.order.adapters

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.android.order.R
import com.android.order.models.Image
import com.balysv.materialripple.MaterialRippleLayout
import com.facebook.drawee.view.SimpleDraweeView
import com.stfalcon.frescoimageviewer.ImageViewer


class AdapterImageSlider // constructor
constructor(
    /**
     * context used for inflate item view
     */
    private val context: Activity,

    /**
     * Images list displayed in the adapter
     */
    private var items: List<Image>
) : PagerAdapter() {

    /**
     *  Build in function to know items number
     *
     * @return items number in adapter
     */
    override fun getCount(): Int {
        return items.size
    }

    /**
     * set items in the adapter and validate view to display items
     *
     * @param items images wanted to display i the adapter
     */
    fun setItems(items: List<Image>) {
        this.items = items
        notifyDataSetChanged()
    }

    /**
     * TODO Build in function Determines whether a page View is associated with a specific key object
     *
     * @param view
     * @param object
     * @return
     */
    override fun isViewFromObject(
        view: View,
        `object`: Any
    ): Boolean {
        return view === `object` as RelativeLayout
    }

    /**
     * TODO Create view form object to display it
     *
     * @param container
     * @param position
     * @return
     */
    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val o = items[position]
        val inflater =
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val v = inflater.inflate(R.layout.item_slider_image, container, false)
        val image = v.findViewById<View>(R.id.image) as SimpleDraweeView
        val lyt_parent = v.findViewById<View>(R.id.lyt_parent) as MaterialRippleLayout
        lyt_parent.setOnClickListener {
            val images: List<Image> = items
            ImageViewer.Builder(context, images)
                .setFormatter { customImage -> customImage.url }
                .setStartPosition(position)
                .show()
        }
        o.url?.let {
            image.setImageURI(it)
        }

        (container as ViewPager).addView(v)
        return v
    }

    /**
     * TODO Build in function to destroy item and release it
     *
     * @param container
     * @param position
     * @param object
     */
    override fun destroyItem(
        container: ViewGroup,
        position: Int,
        `object`: Any
    ) {
        (container as ViewPager).removeView(`object` as RelativeLayout)
    }

}