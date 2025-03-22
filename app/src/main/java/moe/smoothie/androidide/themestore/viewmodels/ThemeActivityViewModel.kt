package moe.smoothie.androidide.themestore.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import moe.smoothie.androidide.themestore.ThemeState
import okhttp3.OkHttpClient
import javax.inject.Inject

@AssistedFactory
interface ThemeActivityViewModelFactory {
    fun create(url: String): ThemeActivityViewModel
}

@HiltViewModel
class ThemeActivityViewModel @Inject constructor(
    private val httpClient: OkHttpClient,
    @Assisted private val url: String
) : ViewModel() {
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    private val _themeState = MutableStateFlow<ThemeState?>(null)
    val themeState: StateFlow<ThemeState?> get() = _themeState

    fun loadInfo() {
        _isLoading.value = true

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val themeResponse = fetchTheme(url)
                _themeState.value = themeResponse
                
            } catch (e: Exception) {
                // Update with more meaningful error handling or logging here.
                // This could also set an error state in UI.
                e.printStackTrace()
                
            } finally {
                _isLoading.value = false
            }
        }
    }

    private suspend fun fetchTheme(url: String): ThemeState {
        // Actual implementation for fetching theme should be done here.
        // Placeholder logic can be replaced with real network call.
        return ThemeState() // Replace this with actual data retrieval logic.
    }
}