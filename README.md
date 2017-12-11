Weather Vane
========

![Alt text][screenshot]

## What?

This is an Android app based which displays weather forecast data. Ten day forecast data
for a searched location is obtained using the OpenWeatherMap API.

## Design

The focus of this project is the structure of the code while the UI and feature set remain relatively 
simple. The goal being to create an app which is designed to be both highly testable and highly 
modular in order to catch bugs early and reduce friction when adding new features. The principles 
defined by [Clean Architecture][1] have had the largest influence towards these goals. As such the code
is split into three modules:
* **domain module** - this is a pure Java module and contains no outside dependencies. Use Cases
  are defined here. The remaining modules have a dependency to this module. 
* **data module** - this is an Android library module. The app data repository is implemented here.
  REST calls are made from this model and data is persisted here. It has a dependency to the domain 
  module.
* **app module** - this is an Android library module. It contains all of the UI elements for the app.
The **Model-View-Presenter** pattern is used here to separate logic from Activities. It has a 
dependency to domain module. It also has a dependency to the data module due to the use of **Dagger 2** 
for dependency injection.

Towards the goal of being testable the app defines tests which use **JUnit**, **Roboletric**, and 
**Espresso 2**. For better testing the app defines two Product Flavors:
 * **mock** - this flavor is used for hermetic testing. It defines mock data sources so tests can
 run more efficiently. Espresso tests which rely on mocked data are used in this flavor. 
 * **prod** - this flavor is the opposite of mock. It defines data sources which make actual 
 REST calls. 

Other things of note: 

* Along with **Clean Architecture** the **Model-View-Presenter (MVP)** pattern and the 
**Repository Pattern** are used for app architecture.
* **Dagger 2** is used for dependency injection.
* **Retrofit 2** is used to make REST calls.
* **Picasso** is used for image downloads.

## How?

After cloning this repository, here is the suggested environment in order to 
successfully build and install the app.

* Android Studio 3.0
* Android SDK compile version 26
* Android Support Library 26.1.0
* ConstraintLayout 1.0.2

[screenshot]: images/weathervane_screenshot.png?raw=true
[1]: https://8thlight.com/blog/uncle-bob/2012/08/13/the-clean-architecture.html