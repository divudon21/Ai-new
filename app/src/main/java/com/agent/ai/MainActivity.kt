package com.agent.ai

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.SmartToy
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.agent.ai.ui.screens.ChatScreen
import com.agent.ai.ui.screens.AgentsScreen
import com.agent.ai.ui.screens.HistoryScreen
import com.agent.ai.ui.screens.SettingsScreen
import com.agent.ai.ui.theme.GhostAgentTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            GhostAgentTheme {
                MainApp()
            }
        }
    }
}

@Composable
fun MainApp() {
    val navController = rememberNavController()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = { BottomNav(navController) },
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "chat",
            modifier = Modifier.padding(innerPadding),
        ) {
            composable("chat") { ChatScreen() }
            composable("agents") { AgentsScreen() }
            composable("history") { HistoryScreen() }
            composable("settings") { SettingsScreen() }
        }
    }
}

@Composable
fun BottomNav(navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar {
        NavigationBarItem(
            icon = { Icon(Icons.Default.Chat, contentDescription = "Chat") },
            label = { Text("Chat") },
            selected = currentRoute == "chat",
            onClick = {
                navController.navigate("chat") {
                    popUpTo("chat") { inclusive = true }
                }
            },
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.SmartToy, contentDescription = "Agents") },
            label = { Text("Agents") },
            selected = currentRoute == "agents",
            onClick = {
                navController.navigate("agents") {
                    popUpTo("chat")
                }
            },
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.History, contentDescription = "History") },
            label = { Text("History") },
            selected = currentRoute == "history",
            onClick = {
                navController.navigate("history") {
                    popUpTo("chat")
                }
            },
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Settings, contentDescription = "Settings") },
            label = { Text("Settings") },
            selected = currentRoute == "settings",
            onClick = {
                navController.navigate("settings") {
                    popUpTo("chat")
                }
            },
        )
    }
}
