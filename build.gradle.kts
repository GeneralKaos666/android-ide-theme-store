// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    val kotlinVersion = "2.0.20"
    
    // Applying plugins using the Kotlin DSL
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
    alias(libs.plugins.compose.compiler) apply false
    
    // Applying Kotlin Kapt and serialization plugins
    kotlin("kapt") version kotlinVersion apply false
    kotlin("plugin.serialization") version kotlinVersion apply false
    
    // Applying Dagger Hilt plugin
    id("com.google.dagger.hilt.android") version "2.52" apply false
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}

// Task creation for cleaning build directories.
tasks.register<Delete>("clean") {
    delete(rootProject.buildDir)
}