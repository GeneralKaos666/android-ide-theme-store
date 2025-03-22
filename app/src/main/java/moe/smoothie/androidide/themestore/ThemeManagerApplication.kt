package moe.smoothie.androidide.themestore

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ThemeManagerApplication : Application()
package moe.smoothie.androidide.themestore

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ThemeManagerApplication : Application() {
    
    override fun onCreate() {
        super.onCreate()
        // Initial setup code can be added here if needed
    }
    
    // Additional methods or properties can be added as necessary
}