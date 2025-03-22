package moe.smoothie.androidide.themestore.viewmodels

import androidx.lifecycle.ViewModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import moe.smoothie.androidide.themestore.ThemeState
import okhttp3.OkHttpClient
import javax.inject.Inject

@AssistedFactory
interface ThemeActivityViewModelFactory {
    fun create(url: String): ThemeActivityViewModel // Added return type
}

@HiltViewModel
class ThemeActivityViewModel @Inject constructor(
    private val httpClient: OkHttpClient,
    @Assisted private val url: String
) : ViewModel() {
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading // Expose only read-only access

    private val _themeState = MutableStateFlow<ThemeState?>(null)
    val themeState: StateFlow<ThemeState?> get() = _themeState // Expose only read-only access

    fun loadInfo() {
        // Start loading theme information asynchronously
        _isLoading.value = true

        CoroutineScope(Dispatchers.IO).launch {
            try {
                // Simulate network call and update state (this is a placeholder)
                // Replace with actual networking logic.
                val themeResponse = fetchTheme(url)
                _themeState.value = themeResponse
                
            } catch (e: Exception) {
                // Handle the error (e.g., log it or update UI state accordingly)
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }

    // Placeholder for fetch logic (this needs actual implementation)
    private suspend fun fetchTheme(url: String): ThemeState {
        // Implementation goes here; this currently simulates a theme loading.
        return ThemeState() // Replace with actual data retrieval logic as needed.
    }
}