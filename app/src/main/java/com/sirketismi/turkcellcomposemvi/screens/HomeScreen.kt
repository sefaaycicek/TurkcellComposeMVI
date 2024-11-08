package com.sirketismi.turkcellcomposemvi.screens

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.navigation.NavBackStackEntry
import com.sirketismi.turkcellcomposemvi.product.Product

@Composable
fun HomeScreen() {
    Text("HomeScreen")
}

@Composable
fun CartScreen() {
    Text("CartScreen")
}

@Composable
fun AccountScreen() {
    Text("AccountScreen")
}

@Composable
fun FavoriteScreen() {
    Text("FavoriteScreen")
}

@Composable
fun ProductDetailScreen(backStackEntry : NavBackStackEntry) {
    backStackEntry.savedStateHandle.getLiveData<Product>("product").observeAsState().value?.let {
        print(it)
    }

    Text("ProductDetailScreen")
}
