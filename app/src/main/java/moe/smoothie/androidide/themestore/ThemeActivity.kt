package moe.smoothie.androidide.themestore

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.OkHttpClient
import javax.inject.Inject

enum class StoreType(
    @StringRes val storeName: Int,
    @DrawableRes val storeIcon: Int,
) {
    JETBRAINS(
        storeName = R.string.store_name_jetbrains,
        storeIcon = R.drawable.jetbrains_marketplace_icon,
    ),
    MICROSOFT(
        storeName = R.string.store_name_microsoft,
        storeIcon = R.drawable.microsoft_store_icon,
    ),
}

data class ThemeState(
    val iconUrl: String,
    val name: String,
    val description: String,
    val publisherName: String,
    val publisherDomain: String? = null,
    val publisherVerified: Boolean = false,
)

@AndroidEntryPoint
class ThemeActivity : ComponentActivity() {

    companion object {
        const val EXTRA_STORE_TYPE: String = "STORE_TYPE"
        const val EXTRA_ICON_URL: String = "ICON_URL"
        const val EXTRA_THEME_URL: String = "THEME_URL"
    }

    private val tag = "ThemeActivity"
    private val viewModel: ThemeActivityViewModel by viewModels()

    @Inject lateinit var httpClient: OkHttpClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val store = intent.getSerializableExtra(EXTRA_STORE_TYPE) as? StoreType ?: run {
            Log.e(tag, "No store type passed in the intent")
            finish()
            return // Gracefully return on error instead of abruptly finishing.
        }

        setContent {
            AndroidIDEThemesTheme {
                // Initialize the scroll state here.
                val scrollState = rememberScrollState()
                
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        ThemeActivityTopBar(
                            storeName = stringResource(store.storeName),
                            storeIcon = painterResource(store.storeIcon),
                            scrolled = scrollState.value != 0,
                            backButtonCallback = { finish() }
                        )
                    },
                ) { innerPadding ->
                    // Pass innerPadding and scrollState to ThemeView for proper display.
                    ThemeView(innerPadding, scrollState)
                }
            }
        }
    }
}

@Composable
private fun ThemeView(innerPadding: PaddingValues, scrollState: ScrollState) {
    // Use Column directly without Box unless necessary.
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
            .verticalScroll(scrollState)
    ) {
        repeat(30) { index ->
            Text("Something $index", Modifier.padding(10.dp))
        }
    }
}