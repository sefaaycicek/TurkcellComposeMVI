package com.sirketismi.turkcellcomposemvi.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun BottomNavigationBar(navController: NavController) {
    var items = listOf(BottomNavItem.Home, BottomNavItem.Cart, BottomNavItem.Account, BottomNavItem.Favorite)


    BottomNavigation(Modifier.padding(vertical = 56.dp)) {
        val backStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = backStackEntry?.destination

        items.forEach { item->
            BottomNavigationItem(
                selected = currentDestination?.route == item.route,
                selectedContentColor = Color.Red,
                unselectedContentColor = Color.Yellow,
                onClick = {
                    navController.navigate(item.route)
                },
                icon = {
                    Icon(imageVector = item.icon, contentDescription = item.label)
                },
                label = {
                    Text(item.label)
                }

            )
        }
    }
}

