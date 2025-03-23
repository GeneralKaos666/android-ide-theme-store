package moe.smoothie.androidide.themestore

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * The [ThemeManagerApplication] class serves as the base application class 
 * for managing global app state and dependency injection setup using Dagger Hilt.
 */
@HiltAndroidApp
class ThemeManagerApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        
        // Initialize any global application components or libraries here.
        // For example:
        // initializeSomeLibrary()
        // setupLogging()
    }

    // Example function you might want to implement:
    /*
    private fun initializeSomeLibrary() {
        // Code to initialize third-party libraries or application state.
    }
    */
}