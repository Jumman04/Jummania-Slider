# JSlider: The Universal Slider Library for Android

[Image of JSlider logo]

## Enhance Your UI with Seamless Slides

Empower your mobile app's user interface with JSlider, a lightweight and highly customizable slider library for Android. JSlider simplifies the process of integrating seamless, responsive sliders into your app, empowering developers to create engaging and intuitive user experiences.

## Key Features

* **Unmatched Customizability:** JSlider offers a plethora of customization options, allowing you to tailor the slider's appearance and behavior to perfectly match your app's design and functionality.

* **Effortless Integration:** Integrating JSlider into your existing Android project is a breeze. Simply add the dependency to your build.gradle file and follow the concise usage instructions.

* **Smooth and Responsive:** JSlider delivers smooth and responsive slider animations, ensuring a delightful user experience.

# Usage

## XML
```xml
<com.jummania.j_slider.JSlider
    android:id="@+id/jSlider"
    android:layout_width="match_parent"
    android:layout_height="222dp" />
```

-   Change Duration of Slidng:
```xml  
     app:slidingDuration="2222"
```  
-   Change indicator Size.
```xml  
    app:indicatorSize="15dp"
```  
-   Add padding in Slider: 
```xml  
    app:sliderPaddingRight="0dp"
    app:sliderPaddingLeft="0dp"
    app:sliderPaddingTop="0dp"
    app:sliderPaddingBottom="0dp"
```
-   Change Indicator Padding bottom:
```xml  
    app:indicatorPaddingBottom="22dp"
```  
-   You can hide or show Indicator: 
```xml  
    app:enableIndicator="true"
```

-   You can enable or desable auto sliding: 
```xml  
    app:enableAutoSliding="true"
```  
-   Change Default Indicator Color: 
```xml  
    app:defaultIndicatorColor="@color/defaultColor"
```
-   Change Selected Indicator Color: 
```xml  
    app:selectedIndicatorColor="@color/selectedColor"
```

## In Activity
-   Add ImageSlider to your **Activity**
  ```kt
override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val jSlider: JSlider = findViewById(R.id.jSlider)
        jSlider.setSlider(Slider())

    }

    //Out of onCreate, Create a Class for Slider
    private inner class Slider : JSlider.Slide() {
        override fun getView(layoutInflater: LayoutInflater, parent: ViewGroup): View {
            return layoutInflater.inflate(R.layout.item_slider, parent, false)
        }

        override fun onSliderCreate(view: View, position: Int) {

            //Your code here

        }

        override fun getCount(): Int {
            return arrayList.size
        }

    }
```
-   You can add animation like that, <b>14 Animations added</b>. You can check in <a href="https://github.com/Jumman04/Jummania-Slider/blob/master/J-Slider/src/main/java/com/jummania/j_slider/animations/AnimationTypes.kt"> Animation List </a>
```kt  
jSlider.setSlideAnimation(AnimationTypes.ZOOM_OUT)
```
-   You can also set ViewPager.PageTransformer by:
```kt  
jSlider.setPageTransformer(boolean, TransformerClass())
```

## Setup

```xml  ##
allprojects {
	repositories {
		...
		maven { url = uri("https://jitpack.io") }
	}
}
```
```xml
dependencies {
	implementation("com.github.Jumman04:Jummania-Slider:1.1")
}
```

## 📄 License
MIT License

Copyright (c) 2023 Sharif Uddin Jumman

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.

