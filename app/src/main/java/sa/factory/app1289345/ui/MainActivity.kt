package sa.factory.app1289345.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import sa.factory.app1289345.data.InMemoryOrderRequestRepository
import sa.factory.app1289345.model.CatalogItem

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val repository = InMemoryOrderRequestRepository()
        val viewModel = OrderRequestViewModel(repository)

        setContent {
            var currentScreen by remember { mutableStateOf("home") }
            var selectedItem by remember { mutableStateOf<CatalogItem?>(null) }

            when (currentScreen) {
                "details" -> {
                    OrderRequestDetailsScreen(
                        item = selectedItem,
                        onBack = { currentScreen = "home" },
                        onProceedCheckout = { currentScreen = "checkout" }
                    )
                }
                "checkout" -> {
                    OrderRequestCheckoutScreen(
                        onBack = { currentScreen = "details" },
                        onComplete = { currentScreen = "home" }
                    )
                }
                else -> {
                    OrderRequestHomeScreen(
                        viewModel = viewModel,
                        onSelectItem = { item ->
                            selectedItem = item
                            currentScreen = "details"
                        },
                        onQuickCheckout = {
                            currentScreen = "checkout"
                        }
                    )
                }
            }
        }
    }
}
