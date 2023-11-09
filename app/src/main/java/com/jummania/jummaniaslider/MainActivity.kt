package com.jummania.jummaniaslider

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.jummania.j_slider.JSlider

class MainActivity : AppCompatActivity() {
    private var arrayList = arrayListOf<DataModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val jSlider: JSlider = findViewById(R.id.jSlider)

        createList()
        jSlider.setSlider(Slider())

    }

    private inner class Slider : JSlider.Slide() {
        override fun getView(layoutInflater: LayoutInflater, parent: ViewGroup): View {
            return layoutInflater.inflate(R.layout.activity_main, parent, false)
        }

        override fun onSliderCreate(view: View, position: Int) {

        }

        override fun getCount(): Int {
            return 9
        }

    }

    private fun createList() {
        add(
            "Sharif Uddin Jumman",
            "https://jummania.com/App/BanglaNatokSamahar/Images/Cover%20Photo.jpg"
        )
        add("Anindya Das", "https://jummania.com/App/BanglaNatokSamahar/Images/Aninda das.jpg")
        add("MD Abdullah", "https://avatars.githubusercontent.com/u/110069678?v=4")
        add(
            "MD Sarowar Hosain Shuvo",
            "https://scontent.fdac142-1.fna.fbcdn.net/v/t1.6435-9/120902465_785023465615624_7635398377734730401_n.jpg?_nc_cat=109&ccb=1-7&_nc_sid=be3454&_nc_ohc=azvmlXXjIDEAX8eil0Z&_nc_ht=scontent.fdac142-1.fna&oh=00_AfCRLl3dlz8aqhEmHWqEUtaUcvq65g2gdx67Nlr0IsTttA&oe=6550B2AD"
        )
        add(
            "Sharif Uddin Jumman",
            "https://jummania.com/App/BanglaNatokSamahar/Images/Profile%20Photo.jpg"
        )
    }

    private fun add(name: String, photo: String) {
        arrayList.add(DataModel(name, photo))
    }
}