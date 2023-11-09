package com.jummania.jummaniaslider

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.jummania.j_slider.JSlider
import com.jummania.j_slider.animations.AnimationTypes
import com.squareup.picasso.Picasso

class MainActivity : AppCompatActivity() {
    private var arrayList = arrayListOf<DataModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val jSlider: JSlider = findViewById(R.id.jSlider)
        createList()

        //  jSlider.setSlideAnimation(AnimationTypes.ZOOM_OUT)
        jSlider.setSlideAnimation(AnimationTypes.CUBE_OUT)
        // jSlider.setSlideAnimation(AnimationTypes.DEPTH_SLIDE2)
        // jSlider.setSlideAnimation(AnimationTypes.DEPTH_SLIDE)
        jSlider.setSlider(Slider())

    }

    private inner class Slider : JSlider.Slide() {
        override fun getView(layoutInflater: LayoutInflater, parent: ViewGroup): View {
            return layoutInflater.inflate(R.layout.item_slider2, parent, false)
        }

        override fun onSliderCreate(view: View, position: Int) {

            val list = arrayList[position]

            val textView: TextView = view.findViewById(R.id.text_view)
            val imageView: ImageView = view.findViewById(R.id.image_view)

            Picasso.get()
                .load("https://jummania.com/App/BanglaNatokSamahar/Images/Cover%20Photo.jpg")
                .error(R.drawable.default_error).placeholder(R.drawable.default_loading)
                .into(imageView)
            textView.text = list.title

            view.setOnClickListener {
                Toast.makeText(this@MainActivity, list.title, Toast.LENGTH_SHORT).show()
            }


        }

        override fun getCount(): Int {
            return 2
        }

    }

    private fun createList() {
        add("0GVExpdmoDs", "Maroon 5 - Animals (Lyrics)")
        add("4IenX7OHumk", "Win or Die (Full Episode)")
        add("AhP5Tg_BLIk", "Amazon Wildlife")
    }


    private fun add(id: String, title: String) {
        arrayList.add(DataModel(id, title))
    }
}