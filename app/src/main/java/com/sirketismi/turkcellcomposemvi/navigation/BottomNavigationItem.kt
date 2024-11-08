package com.sirketismi.turkcellcomposemvi.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.ui.graphics.vector.ImageVector

sealed class  BottomNavItem(
    val route : String,
    var icon : ImageVector,
    var label : String) {

    data object Home : BottomNavItem(Route.HOME, Icons.Filled.Home, "Home")
    data object Cart : BottomNavItem(Route.CART, Icons.Filled.AddCircle, "Cart")
    data object Account : BottomNavItem(Route.ACCOUNT, Icons.Filled.Build, "Account")
    data object Favorite : BottomNavItem(Route.FAVORITE, Icons.Filled.LocationOn, "Favorite")
}