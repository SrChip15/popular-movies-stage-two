Popular Movies   <img src=https://www.themoviedb.org/static_cache/v4/logos/408x161-powered-by-rectangle-green-bb4301c10ddc749b4e79463811a68afebeae66ef43d17bcfd8ff0e60ded7ce99.png width="150"/>
=======================

This is a movie discovery Android application. It is powered by [themoviedb.org](https://www.themoviedb.org/documentation/api). The splash screen lists movies by presenting the movie's poster, arranged vertically, 2 movies per row in portrait mode and 3 movies per row in landscape mode. Currently, the app is built for screen display size of 5 - 5.5 inches. There are two sort options avaiable from within the options menu - sort movies by popularity and sort movies by ratings. The REST API mentioned above provides hundreds, sometimes thousands of pages of movie information. The app delivers this massive information set via infinite scrolling, which is enabled by hooking up Marko Milos' [paginate library](https://github.com/MarkoMilos/Paginate). 

The second screen or the details screen of the app presents an edge-to-edge display of an even bigger movie backdrop image 
along with the traile, a favorite button, movie synopsis, and a movie reviews section, again all provided by the aforementioned API. The trailer begins to play in the native video player of the Android device after clicking the play button. Lastly, the app also permits an user to mark and save favorites, which is then available in offline state too. This is made possible with the implementation of a SQLite database within the app. Please take a look at the screenshots section for how both the splash screen and details screen are designed.

**This product uses the TMDb API but is not endorsed or certified by TMDb.**

Tools/Libraries
----------------

* Gradle v4.1
* Android Plugin v3.0.0
* Android API v26
* Android Build Tools v26
* SQLite Database
* Retrofit by Jake Wharton
* [Paginate](https://github.com/MarkoMilos/Paginate) by Marko Milos

Screenshots
------------

<img src="https://github.com/SrChip15/popular-movies-stage-2/blob/master/splash_screen.png"
width="300"/>
<img src="https://github.com/SrChip15/popular-movies-stage-2/blob/master/details_screen.png"
width="300"/>

License
--------
```
Copyright 2018 Srinath Chintapalli

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

 http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
