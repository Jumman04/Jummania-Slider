package com.jummania.jummaniaslider

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.jummania.JSlider
import com.jummania.animations.AnimationTypes
import com.squareup.picasso.Picasso

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val jSlider: JSlider = findViewById(R.id.jSlider)  //find
        //  val jSlider:JSlider = JSlider(this)           // or bind the slider

        jSlider.setSlideAnimation(AnimationTypes.CUBE_OUT)

        /** You can add animation like that, 16 Animations added. check below the list of animation:
         *     ZOOM_IN,
         *     ZOOM_OUT,
         *     DEPTH_SLIDE,
         *     CUBE_IN,
         *     CUBE_OUT,
         *     FLIP_HORIZONTAL,
         *     FLIP_VERTICAL,
         *     FOREGROUND_TO_BACKGROUND,
         *     BACKGROUND_TO_FOREGROUND,
         *     ROTATE_UP,
         *     ROTATE_DOWN,
         *     GATE,
         *     TOSS,
         *     FIDGET_SPINNER,
         *     DEPTH_SLIDE2,
         *     TABLET_SLIDE
         */

        /** You can also set ViewPager.PageTransformer by:
         * jSlider.setPageTransformer(boolean, TransformerClass())
         */

        /** add 'addOnPageChangeListener' if you really need
         *//*
        jSlider.addOnSlideChangeListener(object : JSlider.OnSlideChangeListener {
            override fun onSliderScrolled(
                position: Int, positionOffset: Float, positionOffsetPixels: Int
            ) {
                Log.d(
                    "JSlider",
                    "position: $position, positionOffset: $positionOffset, positionOffsetPixels: $positionOffsetPixels"
                )
            }

            override fun onSliderSelected(position: Int) {
                Log.d("JSlider", "position: $position")
            }

            override fun onSliderScrollStateChanged(state: Int) {
                Log.d("JSlider", "state: $state")
            }

        })
        */


        jSlider.setSlider(DefaultSlider()) // DefaultSlider
        // jSlider.setSlider(InfinitySlider()) // InfinitySlider

        val start: Button = findViewById(R.id.start)
        val stop: Button = findViewById(R.id.stop)

        start.setOnClickListener {
            jSlider.startAutoSliding()
            Toast.makeText(this@MainActivity, "Slider is now in motion", Toast.LENGTH_SHORT).show()
        }

        stop.setOnClickListener {
            jSlider.stopAutoSliding()
            Toast.makeText(this@MainActivity, "Slider has come to a halt", Toast.LENGTH_SHORT)
                .show()
        }

    }

    private inner class DefaultSlider : JSlider.DefaultSlider() {
        override fun getView(layoutInflater: LayoutInflater, parent: ViewGroup): View {
            return layoutInflater.inflate(R.layout.item_slider2, parent, false) //Inflate you layout
        }

        override fun onSliderCreate(view: View, position: Int) {

            val textView: TextView = view.findViewById(R.id.text_view) //find your child
            val imageView: ImageView = view.findViewById(R.id.image_view)

            Picasso.get()
                .load("https://jummania.com/App/BanglaNatokSamahar/Images/Cover%20Photo.jpg")
                .error(R.drawable.default_error).placeholder(R.drawable.default_loading)
                .into(imageView)

            textView.text = getString(R.string.Developer_Name)

            view.setOnClickListener {

                Toast.makeText(
                    this@MainActivity, getString(R.string.Developer_Name), Toast.LENGTH_SHORT
                ).show()
            }


        }

        override fun getCount(): Int {
            return 3
        }

    }

    private inner class InfinitySlider : JSlider.InfinitySlider() {
        override fun itemCount(): Int {
            return 3
        }

        override fun getView(layoutInflater: LayoutInflater, parent: ViewGroup): View {
            return layoutInflater.inflate(R.layout.item_slider2, parent, false) //Inflate you layout
        }

        override fun onSliderCreate(view: View, position: Int) {
            val textView: TextView = view.findViewById(R.id.text_view) //find your child
            val imageView: ImageView = view.findViewById(R.id.image_view)

            Picasso.get()
                .load("https://jummania.com/App/BanglaNatokSamahar/Images/Cover%20Photo.jpg")
                .error(R.drawable.default_error).placeholder(R.drawable.default_loading)
                .into(imageView)

            textView.text = getString(R.string.Developer_Name)

            view.setOnClickListener {

                Toast.makeText(
                    this@MainActivity, getString(R.string.Developer_Name), Toast.LENGTH_SHORT
                ).show()
            }
        }

    }
}