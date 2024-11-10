plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)

    alias(libs.plugins.ksp)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.kotlin.parcelize)
    alias(libs.plugins.kotlin.serialize)
    alias(libs.plugins.navigation.safeargs)
    alias(libs.plugins.hilt.android)
    alias(libs.plugins.firebase.crashlytics)
    alias(libs.plugins.play.publisher)

}

android {
    namespace = "com.example.appdemo"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.appdemo"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        ksp {
            arg("room.schemaLocation", "$projectDir/schemas")
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    viewBinding {
        enable=true
    }
    dataBinding {
        enable=true
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)


    with (libs) {



        // Kotlin
        implementation(kotlin.stdlib)
        implementation(core.ktx)
        implementation(kotlin.coroutines)
        implementation(kotlin.json.serialization)

//         Hilt
        implementation(bundles.hilt)
        ksp(hilt.compiler)
        ksp(hilt.compiler.kapt)
        ksp(hilt.android.compiler)

        // Jetpack
        ksp(room.kapt)
        implementation(paging)
        implementation(bundles.room)
        implementation(platform(compose.bom))
        implementation(bundles.compose)
        implementation(work.manager.ktx)
        implementation(bundles.lifecycle)
        implementation(bundles.navigation)

        // Image
//        implementation(lottie)
        implementation(bundles.coil)

        // Player
//    implementation(bundles.exoplayer)
        implementation(bundles.media3.player)
        implementation(bundles.cast)
//        implementation(bundles.ads)

        // Security
//    implementation(bundles.security)

        // Network
        implementation(bundles.retrofit)

        // Google Services
        implementation(google.api.client) {
            exclude(group = "org.apache.httpcomponents", module = "httpclient")
            exclude(group = "com.google.code.findbugs")
            exclude(module = "support-annotations")
            exclude(group = "com.google.guava")
        }
        implementation(google.http.client) {
            exclude(group = "org.apache.httpcomponents", module = "httpclient")
            exclude(group = "com.google.code.findbugs")
            exclude(module = "support-annotations")
            exclude(group = "com.google.guava")
        }

        // Play Services
        implementation(bundles.play.services)

        // Firebase
        implementation(bundles.firebase)
        implementation ("com.microsoft.clarity:clarity:2.+")
    }
}