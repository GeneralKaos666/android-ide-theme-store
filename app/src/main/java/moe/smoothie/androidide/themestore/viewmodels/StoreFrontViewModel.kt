package moe.smoothie.androidide.themestore.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.update
import moe.smoothie.androidide.themestore.ui.LoadingStatus

abstract class StoreFrontViewModel<T> : ViewModel() {
    abstract val itemsPerPage: Int

    protected val mutableItems = MutableStateFlow<List<T>>(emptyList())
    val items: StateFlow<List<T>> = mutableItems

    protected val mutableIsLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = mutableIsLoading

    protected val mutableAllItemsLoaded = MutableStateFlow(false)
    val allItemsLoaded: StateFlow<Boolean> = mutableAllItemsLoaded

    protected val mutableLoadingStatus = MutableStateFlow(LoadingStatus.LOADING)
    val loadingStatus: StateFlow<LoadingStatus> = mutableLoadingStatus

    protected val mutableSearchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = mutableSearchQuery

    private var job: Job? = null
    
    abstract fun loadItems(context: Context)

    open fun reload(context: Context) {
        // Using a coroutine to avoid blocking the main thread
        job?.cancel() // Cancel any ongoing load operation
        job = CoroutineScope(Dispatchers.IO).launch {
            mutableIsLoading.emit(true)
            mutableItems.update { emptyList() } // Resetting items
            mutableAllItemsLoaded.update { false } // Resetting loaded status
            
            loadItems(context) // Load new items
            
            mutableIsLoading.emit(false) // Update loading status once done
        }
    }

    open fun setSearchQuery(query: String, context: Context) {
        // Update only if different to prevent unnecessary reloads
        if (mutableSearchQuery.value != query) {
            mutableSearchQuery.update { query }
            reload(context)
        }
    }
    
    override fun onCleared() {
        super.onCleared()
        job?.cancel() // Cancel any ongoing jobs when ViewModel is cleared
    }
}package moe.smoothie.androidide.themestore.viewmodels

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import moe.smoothie.androidide.themestore.ui.LoadingStatus

abstract class StoreFrontViewModel<T> : ViewModel() {
    abstract val itemsPerPage: Int

    protected val mutableItems = MutableStateFlow(emptyList<T>())
    val items: StateFlow<List<T>> = mutableItems

    protected val mutableIsLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = mutableIsLoading

    protected val mutableAllItemsLoaded = MutableStateFlow(false)
    val allItemsLoaded: StateFlow<Boolean> = mutableAllItemsLoaded

    protected val mutableLoadingStatus = MutableStateFlow(LoadingStatus.LOADING)
    val loadingStatus: StateFlow<LoadingStatus> = mutableLoadingStatus

    protected val mutableSearchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = mutableSearchQuery

    abstract fun loadItems(context: Context)

    open fun reload(context: Context) {
        mutableItems.update { emptyList() }
        mutableAllItemsLoaded.update { false }
        loadItems(context)
    }

    open fun setSearchQuery(query: String, context: Context) {
        mutableSearchQuery.update { query }
        reload(context)
    }
}
