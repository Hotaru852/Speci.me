package com.example.specime.components.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Assessment
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.specime.R

@Composable
fun BottomNavigationBar(navController: NavController) {
    val items = listOf(
        BottomNavItem(
            "DISC",
            iconBitmap = ImageBitmap.imageResource(id = R.drawable.ic_disc),
            route = "disc"
        ),
        BottomNavItem("Kết quả", iconVector = Icons.Filled.Assessment, route = "results"),
        BottomNavItem("Bạn bè", iconVector = Icons.Filled.People, route = "connections"),
        BottomNavItem("Tài khoản", iconVector = Icons.Filled.Person, route = "account")
    )

    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route

    Column {
        HorizontalDivider(
            color = MaterialTheme.colorScheme.primary,
            thickness = 1.dp
        )
        BottomAppBar(
            contentColor = MaterialTheme.colorScheme.onSurface,
            containerColor = MaterialTheme.colorScheme.surface,
            contentPadding = PaddingValues(0.dp)
        ) {
            items.forEach { item ->
                val isSelected = currentRoute == item.route

                NavigationBarItem(
                    icon = {
                        if (item.iconBitmap != null) {
                            Image(
                                bitmap = item.iconBitmap,
                                contentDescription = item.label,
                                modifier = Modifier.size(30.dp),
                                colorFilter = if (isSelected) ColorFilter.tint(
                                    MaterialTheme.colorScheme.surface
                                ) else ColorFilter.tint(MaterialTheme.colorScheme.primary)
                            )
                        } else if (item.iconVector != null) {
                            Icon(
                                imageVector = item.iconVector,
                                contentDescription = item.label,
                                modifier = Modifier.size(30.dp),
                                tint = if (isSelected) MaterialTheme.colorScheme.surface else MaterialTheme.colorScheme.primary
                            )
                        }
                    },
                    label = {
                        Text(
                            text = item.label,
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.Bold
                        )
                    },
                    selected = isSelected,
                    onClick = {
                        if (!isSelected) {
                            navController.navigate(item.route) {
                                popUpTo(navController.graph.startDestinationId) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    },
                    colors = NavigationBarItemDefaults.colors(
                        indicatorColor = Color.Transparent,
                        selectedTextColor = MaterialTheme.colorScheme.surface,
                        unselectedTextColor = MaterialTheme.colorScheme.primary
                    ),
                    modifier = Modifier.background(
                        color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface
                    )
                )
            }
        }
    }
}

data class BottomNavItem(
    val label: String,
    val iconBitmap: ImageBitmap? = null,
    val iconVector: ImageVector? = null,
    val route: String
)