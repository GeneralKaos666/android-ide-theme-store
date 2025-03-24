plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.compose.compiler)
    id("com.google.dagger.hilt.android")
    kotlin("kapt")
    kotlin("plugin.serialization")
}

android {
    namespace = "moe.smoothie.androidide.themestore"
    compileSdk = 34

    defaultConfig {
        applicationId = "moe.smoothie.androidide.themestore"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables { useSupportLibrary = true }
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures {
        compose = true
    }
    
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.compiler.get()
    }
    
    packaging {
        resources { excludes += listOf("/META-INF/{AL2.0,LGPL2.1}", "/META-INF/NOTICE") } // Expanded exclusions 
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui) 
    implementation(libs.androidx.ui.graphics) 
    implementation(libs.androidx.ui.tooling.preview) 
    implementation(libs.androidx.material3)

    implementation(libs.hilt.android)

    // OkHttp for network operations
    implementation(libs.okhttp)
    implementation(libs.okhttp.coroutines)

    // Serialization support
    implementation(libs.kotlinx.serialization.json)

    // ViewModel support for Compose
    implementation(libs.androidx.lifecycle.viewmodel.compose)

    // Coil for image loading
    implementation(libs.coil.compose)
    
    // Navigation and Hilt support for Jetpack Compose
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.hilt.navigation.compose)

    kapt(libs.hilt.android.compiler)

   // Changed to remove duplication and better organized test dependencies.
   testImplementation(libs.junit)
   androidTestImplementation(platform(libs.junit.platform))
   androidTestImplementation(libs.hilt.android.testing)

   androidTestImplementation(platform(libs.androidx.compose.bom))
   androidTestImplementation(libs.androidx.ui.test.junit4)
   androidTestImplementation(libs.hilt.android.testing) // Maintain single source for testing imports
   androidTestImplementation(libs.androidx.navigation.testing)

   kaptAndroidTest(libs.hilt.compiler)

   debugImplementation(libs.androidx.ui.tooling) 
   debugImplementation(libs.androidx.ui.test.manifest) 
}

kapt {
   correctErrorTypes = true
}