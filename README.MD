# Nagar

A sample Android app using Kotlin, Lifecycle Components, ViewModel, LiveData, Room and Dagger 2

The app downloads a paginated list of cities from an API, caches the results to an SQLite db and displays the results using Android Architecture Components to the UI.

Decisions:
 * Language - Kotlin. https://kotlinlang.org/docs/reference/comparison-to-java.html
 * Architecture - MVVM. This was the go to choice as the Android Architecture components best fit in it.
 * Dependency Injection - Dagger 2. DI makes it easy to reuse our `RoomDatabase`, api interfaces, preferences and other components.
 * Networking - Retrofit. Makes usage of Rest APIs easy and highly extensible.

### Build environment:
* Android Studio - AS 3.0 Beta 1/2
* buildToolsVersion - 26.0.0
* minSdkVersion - 15

### Libraries used:

 * [Android Architecture Components](https://developer.android.com/topic/libraries/architecture/guide.html)
 * [Dagger 2](https://google.github.io/dagger/)
 * [Retrofit](http://square.github.io/retrofit/)
 * [OkHttp](http://square.github.io/okhttp/)
 * [Stetho](https://github.com/facebook/stetho)
