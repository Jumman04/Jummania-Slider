<a href="https://github.com/Jumman04/Jummania-Slider">  

<img align="left" src="https://github-production-user-asset-6210df.s3.amazonaws.com/113237846/282801213-6919ff05-9a68-497f-b9e6-ae937ed55e09.gif" width="50%"/>  
</a>

<h1 align="center">J-Slider for Android</h1>  
<p align="center">Just inflate the layout you want to slide.</p>  
<br>
&nbsp; &nbsp; &nbsp; &nbsp; &#8226; &nbsp; Easy to use. <br>
&nbsp; &nbsp; &nbsp; &nbsp; &#8226; &nbsp; Automatic scrolling for the time you set. <br>
&nbsp; &nbsp; &nbsp; &nbsp; &#8226; &nbsp; Inflate any layout or view.<br>
&nbsp; &nbsp; &nbsp; &nbsp; &#8226; &nbsp; 17 different slide animations.<br>
&nbsp; &nbsp; &nbsp; &nbsp; &#8226; &nbsp; Use with Java or Kotlin.
<br><br>

<p align="center"><br>
	<img src="https://img.shields.io/badge/API-17%2B-brightgreen.svg?style=flat"/>
	<img src="https://img.shields.io/github/v/release/Jumman04/Jummania-Slider?include_prereleases&amp;label=latest%20release" alt="Latest release"/>
	 <a href="https://jitpack.io/#Jumman04/Jummania-Slider">
    <img src="https://jitpack.io/v/Jumman04/Jummania-Slider.svg"/> </a>

  <a href="https://github.com/Jumman04/Jummania-Slider/issues">
    <img src="https://img.shields.io/github/issues/Jumman04/Jummania-Slider"/>
  </a>
 <a href="https://github.com/Jumman04/Jummania-Slider/graphs/contributors" >
        <img src="https://img.shields.io/github/contributors/Jumman04/Jummania-Slider" /></a>
    <a href="https://github.com/Jumman04/Jummania-Slider/pulse" >
        <img src="https://img.shields.io/github/commit-activity/m/Jumman04/Jummania-Slider" /></a>

  <a href="https://github.com/Jumman04/Jummania-Slider/network/members">
    <img src="https://img.shields.io/github/forks/Jumman04/Jummania-Slider"/>
  </a>
  <a href="https://github.com/Jumman04/Jummania-Slider/stargazers">
    <img src="https://img.shields.io/github/stars/Jumman04/Jummania-Slider"/>
  </a>
    <a href="https://github.com/Jumman04/Jummania-Slider/LICENSE">
    <img src="https://img.shields.io/github/license/Jumman04/Jummania-Slider"/>
  </a>
</p>

## Usage

### XML

```xml

<com.jummania.JSlider android:id="@+id/jSlider" android:layout_width="match_parent"
    android:layout_height="222dp" />
```

- Change Duration of Slidng:

```xml
     app:slidingDuration="2222"
```

- Change indicator Size.

```xml
    app:indicatorSize="15dp"
```

- Add padding in Slider:

```xml
    app:sliderPaddingRight="0dp"app:sliderPaddingLeft="0dp"app:sliderPaddingTop="0dp"app:sliderPaddingBottom="0dp"
```

- Change Indicator Padding bottom:

```xml
    app:indicatorPaddingBottom="22dp"
```

- Change Indicator MarginHorizontal:

```xml
    app:indicatorMarginHorizontal="3dp"
```

- You can hide or show Indicator:

```xml
    app:enableIndicator="true"
```

- You can enable or desable auto sliding:

```xml
    app:enableAutoSliding="true"
```

- Change Default Indicator Color:

```xml
    app:defaultIndicatorColor="@color/defaultColor"
```

- Change Selected Indicator Color:

```xml
    app:selectedIndicatorColor="@color/selectedColor"
```

- Choose how the indicator updates:

```xml
           app:indicatorUpdateMode="ANIMATED"<!-- More Options STATIC | SYNC -->
```

- Set the alignment of the indicator:

```xml
                app:indicatorAlign="ALIGN_BOTTOM"<!-- more option  ALIGN_TOP | ALIGN_START | ALIGN_END | ALIGN_LEFT | ALIGN_RIGHT | CENTER_HORIZONTAL | CENTER_VERTICAL | CENTER_IN_PARENT-->
```

- Specify the gravity of the indicator:

```xml
               app:indicatorGravity="bottom"<!-- More Option's top | left | right | center_vertical | fill_vertical |fill_horizontal | center_horizontal | center | fill | start | clip_horizontal | clip_vertical |end -->

```

#### In Activity

- Add ImageSlider to your **Activity**

```kt
override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    val jSlider: JSlider = findViewById(R.id.jSlider)
    jSlider.setSlider(DefaultSlider())

}

//Out of onCreate, Create a Class for Slider
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
```

- You can add animation like that, <b>16 Animations added</b>. You can check
  in <a href="https://github.com/Jumman04/Jummania-Slider/blob/master/J-Slider/src/main/java/com/jummania/j_slider/animations/AnimationTypes.kt">
  Animation List </a>

```kt
jSlider.setSlideAnimation(AnimationTypes.ZOOM_OUT)
```

- You can also set ViewPager.PageTransformer by:

```kt
jSlider.setPageTransformer(boolean, TransformerClass())
```

- To start or stop autoSliding:

```kt
jSlider.startAutoSliding()
jSlider.stopAutoSliding()
```

- to slide next or previuse:

```kt
 jSlider.slideNext()
jSlider.slidePrevious()
```

- Check if the Slider is currently sliding:

```kt
jSlider.isSliding() // True if sliding, false otherwise.
```

- You can add 'addOnSlideChangeListener' if you really need

```kt
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
```

- If you want to Reverse-less slide:

```kt
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
```

## Setup

```xml ##
allprojects {    repositories {        ...        maven { url = uri("https://jitpack.io") }    }}
```

```xml
dependencies {    implementation("com.github.Jumman04:Jummania-Slider:3.3")}
```

## Feature Requests

If you have a feature request or a suggestion for improving this library, please feel free
to [open an issue](https://github.com/Jumman04/Jummania-Slider/issues/new) and let us know! We
appreciate your feedback and are always looking to make our library better.

#### How to Request a Feature

1. Click on the [Issues tab](https://github.com/Jumman04/Jummania-Slider/issues).
2. Click the green "New Issue" button.
3. Choose the "Feature Request" template.
4. Fill in the requested information and submit the issue.

Thank you for helping us improve the library!

## 📄 License

This project is licensed under the MIT License - see
the [LICENSE.md](https://github.com/Jumman04/Jummania-Slider/blob/master/LICENSE.md) file for
details.
