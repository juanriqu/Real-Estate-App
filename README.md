# Real-Estate-App

Real Estate App using Kotlin, MVVM, Coroutines, Hilt, Room, Retrofit, Google Maps and more.

## Introduction

This app was created as a challenge to show my skills as a mobile developer.

## The Challenge

The challenge was to create an app that shows a list of houses and their details, learn how to use the repository
pattern, api calls, room database, google maps, and more.

## Requirements

Minimum SDK 21 (Android 5.0 Lollipop).

## Guidelines

This app has been tested in a Pixel 3XL, to take in consideration the screen size and the density.

To use the app with location services, you need to have a Google Maps API key.
You can get
one [here](https://developers.google.com/maps/documentation/android-sdk/get-api-key) and add it to
your `gradle.properties` file like this the example below:

``
GOOGLE_MAPS_API_KEY= "YOUR_API_KEY"
``

This can be used without location services, but the distance shown will be N/A.

To use it with location You have to simulate the location of the device in the emulator.
To do that you go to google maps first and click in locate me button, then you can open the app and the distance will be
calculated correctly, else it will show N/A.

##Instructions

If you want to know the difference usage of code in the app, you can check the comments in the code.

## Architecture

App is based on MVVM architecture and a repository pattern.

The app has a single activity with multiple fragments.

The app uses the Navigation Component to handle navigation between fragments.

## Installation

Clone this repository and open it in Android Studio, then build and run the app.

## Screenshots

![List-Of-Houses.png](screenshots/List-Of-Houses.png)
![Detailed-House.png](screenshots/Detailed-House.png)
![About.png](screenshots/About.png)
![Splash-Screen.png](screenshots/Splash-Screen.png)

## Usage

The app has the following screens:

* Splash Screen
* List of Houses (with search)
* Detailed House
* About

(See screenshots below)

## Contributing

Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change or any
suggestions you have.

## License

[MIT](https://choosealicense.com/licenses/mit/)

## Author

[Juan Riquelme](https://github.com/juanriqu)

## Libraries

All the libraries used in this project are listed in the build.gradle file, indicating it usage in the app and the
version.

* [Kotlin](https://kotlinlang.org/)
* [Dagger Hilt](https://dagger.dev/hilt/)
* [Coroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html)
* [Retrofit](https://square.github.io/retrofit/)
* [OkkHttp](https://square.github.io/okhttp/)
* [Room](https://developer.android.com/topic/libraries/architecture/room)
* [LiveData](https://developer.android.com/topic/libraries/architecture/livedata)
* [Data Binding](https://developer.android.com/topic/libraries/data-binding)
* [Navigation Component](https://developer.android.com/guide/navigation/navigation-getting-started)
* [Material Design](https://material.io/develop/android/docs/getting-started/)
* [Coil](https://github.com/coil-kt/coil)
* [Splash Screen](https://developer.android.com/guide/topics/ui/look-and-feel/splash-screen)
* [Google Maps](https://developers.google.com/maps/documentation/android-sdk/overview)
* [MapView](https://developers.google.com/maps/documentation/android-sdk/map-with-marker)
