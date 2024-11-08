package com.sirketismi.turkcellcomposemvi.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sirketismi.turkcellcomposemvi.product.Product
import com.sirketismi.turkcellcomposemvi.product.ProductScreen
import com.sirketismi.turkcellcomposemvi.screens.AccountScreen
import com.sirketismi.turkcellcomposemvi.screens.CartScreen
import com.sirketismi.turkcellcomposemvi.screens.FavoriteScreen
import com.sirketismi.turkcellcomposemvi.screens.HomeScreen
import com.sirketismi.turkcellcomposemvi.screens.ProductDetailScreen

@Composable
fun NavigationHost() {
    var navController = rememberNavController()

    Scaffold(bottomBar = { BottomNavigationBar(navController) }) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Route.HOME,
            modifier = Modifier.padding(innerPadding)) {
            composable(Route.HOME) {
                ProductScreen(it) {
                    navController.navigate(Route.PRODUCTDETAIL)
                    navController.currentBackStackEntry?.savedStateHandle?.set("product", Product("10", "Elma"))
                }
            }

            composable(Route.CART) {
                CartScreen()
            }

            composable(Route.ACCOUNT) {
                AccountScreen()
            }

            composable(Route.FAVORITE) {
                FavoriteScreen()
            }

            composable(Route.PRODUCTDETAIL) {
                ProductDetailScreen(it)
            }

            composable(Route.PRODUCT) {
                HomeScreen()
            }
        }
    }
}