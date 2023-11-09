# JSlider: The Universal Slider Library for Android

[Image of JSlider logo]

## Enhance Your UI with Seamless Slides

Empower your mobile app's user interface with JSlider, a lightweight and highly customizable slider library for Android. JSlider simplifies the process of integrating seamless, responsive sliders into your app, empowering developers to create engaging and intuitive user experiences.

## Key Features

* **Unmatched Customizability:** JSlider offers a plethora of customization options, allowing you to tailor the slider's appearance and behavior to perfectly match your app's design and functionality.

* **Effortless Integration:** Integrating JSlider into your existing Android project is a breeze. Simply add the dependency to your build.gradle file and follow the concise usage instructions.

* **Smooth and Responsive:** JSlider delivers smooth and responsive slider animations, ensuring a delightful user experience.

## Usage


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



