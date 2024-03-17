# ** Toilets App project - 2024 **

An Android Toilets App is an native Android app fetches and displays the list of Public Toilets.

### **Purpose**

To show good practices using Kotlin features and latest Android libraries from Jetpack.

### **Data source**

GET https://data.ratp.fr/api/records/1.0/search/?dataset=sanisettesparis2011&start=0&rows=1000

Documentation: https://data.ratp.fr/explore/dataset/sanisettesparis2011/information/

### **To improve**

* There is UI to improve
* The splashscreen and loader is missing
* Add Document Kotlin code (KDoc)
* Add Units Tests

### **Libraries/concepts used**

* Gradle modularised project by layers ( platform / business / data )
* The Clean Architecture with MVVM pattern in platform layer
* Jetpack Compose with Material3 design for UI layer
* Kotlin Coroutines
* Retrofit for networking
* Hilt for Dependency Injection
* JUnit and MockK for unit tests
* Play services location
* Fused Location Provider API