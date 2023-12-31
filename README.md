<h1 align="center">Jummania View Slider for Android!</h1>
<h3 align="center"><b><i>Just inflate your view and start sliding!</i></b></h3>

<a href="https://github.com/Jumman04/Jummania-Slider">  
<img align="left" src="https://github-production-user-asset-6210df.s3.amazonaws.com/113237846/284076921-b31614c3-e3cb-40a6-ab4b-31c9b9517dda.gif"  />  </a>  

<li>Easy to use.</li>
<li>Smooth sliding transitions.</li>
<li>Customizable indicator size, colors, and shapes.</li>
<li>Auto-sliding functionality.</li>
<li>Slide with a variety of 38+ different animations.</li>
<li>Ability to add your own custom animation.</li>
<li>Indicator alignment and gravity customization.</li>
<li>Use with Java or Kotlin.</li>
<br>
<p align="left">
	<img src="https://img.shields.io/badge/API-17%2B-brightgreen.svg?style=flat"/>
	    <!-- <img src="https://img.shields.io/github/v/release/Jumman04/Jummania-Slider?include_prereleases&amp;label=latest%20release" alt="Latest release"/> 
	<a href="https://github.com/Jumman04/Jummania-Slider/issues">
    <img src="https://img.shields.io/github/issues/Jumman04/Jummania-Slider"/>
  </a>
	<a href="https://github.com/Jumman04/Jummania-Slider/graphs/contributors" >
        <img src="https://img.shields.io/github/contributors/Jumman04/Jummania-Slider" /></a>
    <a href="https://github.com/Jumman04/Jummania-Slider/pulse" >
        <img src="https://img.shields.io/github/commit-activity/m/Jumman04/Jummania-Slider" /></a>
	-->
	 <a href="https://jitpack.io/#Jumman04/Jummania-Slider">
    <img src="https://jitpack.io/v/Jumman04/Jummania-Slider.svg"/> </a>

  
 
  <a href="https://github.com/Jumman04/Jummania-Slider/network/members">
    <img src="https://img.shields.io/github/forks/Jumman04/Jummania-Slider"/>
  </a>
  <a href="https://github.com/Jumman04/Jummania-Slider/stargazers">
    <img src="https://img.shields.io/github/stars/Jumman04/Jummania-Slider"/>
  </a>
    <a href="https://github.com/Jumman04/Jummania-Slider/LICENSE">
    <img src="https://img.shields.io/github/license/Jumman04/Jummania-Slider"/></a>
</p>
<br>


---

<details>
  <summary><h1>Setup</h1></summary>
  <br>

To integrate Jummania-Slider into your Android project, follow these steps:

### Step 1: Add JitPack Repository

Add the JitPack repository to your project's root `settings.gradle` file:

```groovy
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        maven { url = uri("https://jitpack.io") } //Add this line
    }
}
```

## Step 2: Add the Dependency

Add the Jummania-Slider dependency to your app module's `build.gradle` file:

 ```gradle
dependencies {
    implementation("com.github.Jumman04:Jummania-Slider:4.4")
}
```

 </details>

 ---
 <details>
  <summary><h1>Usage</h1></summary>

---

<details>
  <summary>XML</summary>
  <br>

```xml

    <com.jummania.JSlider 
        android:id="@+id/jSlider" 
        android:layout_width="match_parent"
        android:layout_height="wrap_content" />
```
</details>

---

<details>
  <summary>For Kotlin</summary>
  <br>

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
            .error(R.drawable.default_error)
            .placeholder(R.drawable.default_loading)
            .into(imageView)

        textView.text = getString(R.string.Developer_Name)

        view.setOnClickListener {
            //Apply your click Listener
        }


    }

    override fun getCount(): Int {
        return 3
    }

}
```
</details>

---

<details>
  <summary>For Java</summary>
  <br>

 ```Java

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    JSlider jSlider = findViewById(R.id.jSlider);
    jSlider.setSlider(new DefaultSlider());
  }

//Out of onCreate, Create a Class for Slider
  private class DefaultSlider extends JSlider.DefaultSlider {
    @Override
    public View getView(LayoutInflater layoutInflater, ViewGroup parent) {
      return layoutInflater.inflate(R.layout.item_slider2, parent, false);
    }

    @Override
    public void onSliderCreate(View view, int position) {

      TextView textView = view.findViewById(R.id.text_view);
      ImageView imageView = view.findViewById(R.id.image_view);

      Picasso.get()
              .load("https://jummania.com/App/BanglaNatokSamahar/Images/Cover%20Photo.jpg")
              .error(R.drawable.default_error)
              .placeholder(R.drawable.default_loading)
              .into(imageView);

      textView.setText(getString(R.string.Developer_Name));

      view.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          // Apply your click listener logic
        }
      });
    }

    @Override
    public int getCount() {
      return 3;
    }
  }
```
</details>

</details>

---

<details>
  <summary><h1>Customization</h1></summary>
	
### The JSlider library provides a set of attributes that can be configured either in XML layout files or programmatically.

<table>
   <tr>
      <th>XML Attribute</th>
      <th>Programmatic Attribute</th>
      <th>Description</th>
   </tr>
   <!-- Sliding Duration -->
   <tr>
      <td>app:slidingDuration="integer"</td>
      <td><code>setSlidingDuration(int)</code></td>
      <td>Duration for transitioning between slides, default: 2222</td>
   </tr>
   <!-- Indicator Size -->
   <tr>
      <td>app:indicatorSize="dimension"</td>
      <td><code>setIndicatorSize(int)</code></td>
      <td>Size of the indicator shape, default: 15</td>
   </tr>
   <tr>
      <td>app:manualSlidable="true"</td>
      <td><code>setManualSlidable(boolean)</code></td>
      <td>Enables or disables manual swipe. default: true</td>
   </tr>
   <tr>
      <td></td>
      <td><code>setSliderPadding(int, int, int, int)</code></td>
      <td>Padding for the JSlider component - Top, default: 0, 0, 0, 0</td>
   </tr>
   <!-- Slider Padding Top -->
   <tr>
      <td>app:sliderPaddingTop="dimension"</td>
      <td><code></code></td>
      <td>Padding for the JSlider component - Top, default: 0</td>
   </tr>
   <!-- Slider Padding Left -->
   <tr>
      <td>app:sliderPaddingLeft="dimension"</td>
      <td></td>
      <td>Padding for the JSlider component - Left, default: 0</td>
   </tr>
   <!-- Slider Padding Right -->
   <tr>
      <td>app:sliderPaddingRight="dimension"</td>
      <td></td>
      <td>Padding for the JSlider component - Right, default: 0</td>
   </tr>
   <!-- Slider Padding Bottom -->
   <tr>
      <td>app:sliderPaddingBottom="dimension"</td>
      <td></td>
      <td>Padding for the JSlider component - Bottom, default: 0</td>
   </tr>
   <tr>
      <td></td>
      <td><code>setIndicatorPadding(int, int, int, int)</code></td>
      <td>Padding for the indicator shape, default: 0, 0, 0, 55</td>
   </tr>
   <!-- Indicator Padding Top -->
   <tr>
      <td>app:indicatorPaddingTop="dimension"</td>
      <td><code></code></td>
      <td>Padding for the indicator shape - Top, default: 0</td>
   </tr>
   <!-- Indicator Padding Left -->
   <tr>
      <td>app:indicatorPaddingLeft="dimension"</td>
      <td></td>
      <td>Padding for the indicator shape - Left, default: 0</td>
   </tr>
   <!-- Indicator Padding Right -->
   <tr>
      <td>app:indicatorPaddingRight="dimension"</td>
      <td></td>
      <td>Padding for the indicator shape - Right, default: 0</td>
   </tr>
   <!-- Indicator Padding Bottom -->
   <tr>
      <td>app:indicatorPaddingBottom="dimension"</td>
      <td></td>
      <td>Padding for the indicator shape - Bottom, default: 25dp</td>
   </tr>
   <!-- Indicator Margin Horizontal -->
   <tr>
      <td>app:indicatorMarginHorizontal="dimension"</td>
      <td><code>setIndicatorMarginHorizontal(int)</code></td>
      <td>Horizontal margin between indicator shapes, default: 3</td>
   </tr>
   <!-- Enable Indicator -->
   <tr>
      <td>app:enableIndicator="boolean"</td>
      <td><code>enableIndicator(boolean)</code></td>
      <td>Enable or disable the indicator display, default: true</td>
   </tr>
   <!-- Enable Auto Sliding -->
   <tr>
      <td>app:enableAutoSliding="boolean"</td>
      <td><code>enableAutoSliding(boolean)</code></td>
      <td>Enable or disable auto-sliding functionality, default: true</td>
   </tr>
   <!-- Default Indicator Color -->
   <tr>
      <td>app:defaultIndicatorColor="color"</td>
      <td><code>setDefaultIndicatorColor(int)</code></td>
      <td>Color for the Default indicator shape, default: "#80ffffff"</td>
   </tr>
   <!-- Selected Indicator Color -->
   <tr>
      <td>app:selectedIndicatorColor="color"</td>
      <td><code>setSelectedIndicatorColor(int)</code></td>
      <td>Color for the selected indicator shape, default: Color.WHITE</td>
   </tr>
   <!-- Indicator Update Mode -->
   <tr>
      <td>app:indicatorUpdateMode="SYNC"</td>
      <td><code>setIndicatorUpdateMode(updateType)</code></td>
      <td>Indicator Update Modes: SYNC, STATIC, ANIMATED, default: UpdateTypes.SYNC</td>
   </tr>
   <!-- Indicator Shape Types -->
   <tr>
      <td>app:indicatorShapeTypes="CIRCLE"</td>
      <td><code>setIndicatorShapeTypes(shapeType)</code></td>
      <td>Indicator Shape Types: CIRCLE, HEART, SQUARE, STAR, ShapeTypes.CIRCLE</td>
   </tr>
   <!-- Indicator Alignment -->
   <tr>
      <td>app:indicatorAlignment="ALIGN_BOTTOM"</td>
      <td><code>setIndicatorAlignment(alignment)</code></td>
      <td>Indicator Alignment Options: ALIGN_LEFT, ALIGN_TOP, ALIGN_RIGHT, ALIGN_BOTTOM, ALIGN_CENTER, CENTER_HORIZONTAL, CENTER_VERTICAL, ALIGN_START, ALIGN_END, Alignment.BOTTOM</td>
   </tr>
   <!-- Indicator Gravity -->
   <tr>
      <td>app:indicatorGravity="center"</td>
      <td><code>setIndicatorGravity(gravity)</code></td>
      <td>Indicator Gravity Options, default: Gravity.CENTER</td>
   </tr>
   <!-- Slide Animation -->
   <tr>
      <td>app:slideAnimation="DEFAULT"</td>
      <td><code>jSlider.setSlideAnimation(animationType)</code></td>
      <td>Slide Animation Types: ANTI_CLOCK_SPIN, BACKGROUND_TO_FOREGROUND, CARD_STACK, CLOCK_SPIN, CUBE_IN_DEPTH, CUBE_IN_ROTATION, CUBE_IN_SCALING, CUBE_OUT_DEPTH, CUBE_OUT_ROTATION, CUBE_OUT_SCALING, CUBE_IN, CUBE_OUT, DEPTH_SLIDE, DEPTH_SLIDE2, DEPTH_TRANSFORMATION, DEPTH_ZOOM_OUT, FADEOUT, FADE_PAGE, FAN_TRANSFORMATION, FIDGET_SPINNER, FLIP_HORIZONTAL, FLIP_VERTICAL, FOREGROUND_TO_BACKGROUND, GATE, HINGE, POP, ROTATE_DOWN, ROTATE_UP, SPINNER, SPINNER_TRANSFORMATION, TABLET_SLIDE, TOSS, VERTICAL_FLIP, VERTICAL_SHUT, ZOOM_FADE, ZOOM_IN, ZOOM_OUT, DEFAULT, default: AnimationTypes.DEFAULT</td>
   </tr>
</table>

</details>

---

<details>
  <summary><h1>Event Listeners</h1></summary>

  ### On Slide Change Listener

You can add an `OnSlideChangeListener` to listen for slide change events. This listener provides callbacks for different slide events.

```kt
jSlider.addOnSlideChangeListener(object : JSlider.OnSlideChangeListener {
    override fun onSliderScrolled(
        position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        
    }

    override fun onSliderSelected(position: Int) {
       
    }

    override fun onSliderScrollStateChanged(state: Int) {
        
    }
})
```

  </details>
  
---
	

<details>
  <summary><h1>Feature Requests</h1></summary>
  <br>


	
If you have a feature request or a suggestion for improving this library, please feel free
to [open an issue](https://github.com/Jumman04/Jummania-Slider/issues/new) and let us know! We
appreciate your feedback and are always looking to make our library better.

#### How to Request a Feature

1. Click on the [Issues tab](https://github.com/Jumman04/Jummania-Slider/issues).
2. Click the green "New Issue" button.
3. Fill in the requested information and submit the issue.

Thank you for helping us improve the library!
</details>

---


<details>
  <summary><h1>📄 License</h1></summary>
  <br>

	
This project is licensed under the MIT License - see
the [LICENSE.md](https://github.com/Jumman04/Jummania-Slider/blob/master/LICENSE.md) file for
details.
</details>

---

