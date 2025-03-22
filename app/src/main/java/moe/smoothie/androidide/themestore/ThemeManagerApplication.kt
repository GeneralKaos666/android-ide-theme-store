package moe.smoothie.androidide.themestore

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * The [ThemeManagerApplication] is the base class for maintaining global application state. 
 * It integrates Dagger Hilt for dependency injection throughout the app.
 */
@HiltAndroidApp
class ThemeManagerApplication : Application() {
    
    override fun onCreate() {
        super.onCreate()
        // Initialize necessary components of your application here
    }
}