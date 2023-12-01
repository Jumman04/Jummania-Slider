package com.jummania.jummaniaslider

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.jummania.JSlider

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val jSlider: JSlider = findViewById(R.id.jSlider)  //find
        //  val jSlider:JSlider = JSlider(this)           // or bind the slider

        //  jSlider.setSlideAnimation(AnimationTypes.ANTI_CLOCK_SPIN)
        //  jSlider.setIndicatorShapeTypes(ShapeTypes.HEART)
        //  jSlider.setIndicatorUpdateMode(UpdateTypes.ANIMATED)

        /** You can add animation like that, 16 Animations added. check below the list of animation:
         *     ANTI_CLOCK_SPIN,
         *     BACKGROUND_TO_FOREGROUND,
         *     CARD_STACK,
         *     CLOCK_SPIN,
         *     CUBE_IN_DEPTH,
         *     CUBE_IN_ROTATION,
         *     CUBE_IN_SCALING,
         *     CUBE_OUT_DEPTH,
         *     CUBE_OUT_ROTATION,
         *     CUBE_OUT_SCALING,
         *     CUBE_IN,
         *     CUBE_OUT,
         *     DEPTH_PAGE,
         *     DEPTH_SLIDE,
         *     DEPTH_SLIDE2,
         *     DEPTH_TRANSFORMATION,
         *     DEPTH_ZOOM_OUT,
         *     FADEOUT,
         *     FADE_PAGE,
         *     FAN_TRANSFORMATION,
         *     FIDGET_SPINNER,
         *     FLIP_HORIZONTAL,
         *     FLIP_VERTICAL,
         *     FOLD_PAGE,
         *     FOREGROUND_TO_BACKGROUND,
         *     GATE,
         *     HINGE,
         *     POP,
         *     ROTATE_DOWN,
         *     ROTATE_UP,
         *     SPINNER,
         *     SPINNER_TRANSFORMATION,
         *     TABLET_SLIDE,
         *     TOSS,
         *     VERTICAL_FLIP,
         *     VERTICAL_SHUT,
         *     ZOOM_FADE,
         *     ZOOM_IN,
         *     ZOOM_OUT
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
            jSlider.startAutoSliding() // To start autoSliding manually
            Toast.makeText(this@MainActivity, "Slider is now in motion", Toast.LENGTH_SHORT).show()
        }

        stop.setOnClickListener {
            jSlider.stopAutoSliding() // To stop autoSliding manually
            Toast.makeText(this@MainActivity, "Slider has come to a halt", Toast.LENGTH_SHORT)
                .show()
        }

    }


    private inner class DefaultSlider : JSlider.DefaultSlider() {
        override fun getView(layoutInflater: LayoutInflater, parent: ViewGroup): View {
            return layoutInflater.inflate(R.layout.item_slider2, parent, false) //Inflate you layout
        }

        override fun onSliderCreate(view: View, position: Int) {

            /*
            val textView: TextView = view.findViewById(R.id.text_view) //find your child
            val imageView: ImageView = view.findViewById(R.id.image_view)
            Picasso.get()
                .load("https://jummania.com/App/BanglaNatokSamahar/Images/Cover%20Photo.jpg")
                .error(R.drawable.default_error).placeholder(R.drawable.default_loading)
                .into(imageView)

                textView.text = getString(R.string.Developer_Name)

             */

            view.setOnClickListener {
                Toast.makeText(
                    this@MainActivity,
                    "Developer Name: ${getString(R.string.Developer_Name)}\nItem Position: $position",
                    Toast.LENGTH_SHORT
                ).show()
            }


        }

        override fun getCount(): Int {
            return 3
        }

    }

    /**If you want to slide reverseLess
     * Use InfinitySlider instead DefaultSlider
     *//*
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
                    this@MainActivity,
                    getString(R.string.Developer_Name) + "\nItem Position: $position",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    }
     */

}