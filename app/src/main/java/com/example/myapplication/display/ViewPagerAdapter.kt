package com.example.myapplication.display

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.viewpager.widget.PagerAdapter
import kotlinx.android.synthetic.main.row_slider_images.view.*
import java.util.*


class ViewPagerAdapter(val context: Context, val images: IntArray): PagerAdapter() {

//    val mLayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    override fun getCount(): Int {
        return images.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view==`object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int):View  {
//        val itemView: View = mLayoutInflater.inflate(R.layout.row, container, false)
//        val itemView:View=LayoutInflater.from(context).inflate(R.layout.row_slider_images,container,false)
//
//        itemView.ivRow.setImageResource(images[position])
//        Objects.requireNonNull(container).addView(itemView)
//        return itemView

        val view:View=LayoutInflater.from(context).inflate(com.example.myapplication.R.layout.row_slider_images, container, false)
        view.ivRow.setImageResource(images[position])
        Objects.requireNonNull(container).addView(view)
        return view
    }

    override fun destroyItem(container: View, position: Int, `object`: Any) {
        //container.removeView()
    }

}
