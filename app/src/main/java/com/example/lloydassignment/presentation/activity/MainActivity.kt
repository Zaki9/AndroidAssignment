package com.example.lloydassignment.presentation.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.lloydassignment.R
import com.example.lloydassignment.presentation.navigation.NewsAppRoutes
import com.example.lloydassignment.presentation.navigation.AppNavHost
import com.example.lloydassignment.presentation.theme.LloydAssignmentTheme
import com.example.lloydassignment.presentation.viewmodel.NewsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {
    private val viewModel: NewsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LloydAssignmentTheme {
                NewsHomeComposable()
            }
        }
    }

    @Composable
    private fun NewsHomeComposable() {
        val navController = rememberNavController()
        val snackbarHostState = remember { SnackbarHostState() }
        val currentBackStackEntry = navController.currentBackStackEntryAsState().value
        val currentRoute = currentBackStackEntry?.destination?.route

        //adding Scaffold for topAppBar and SnackBar
        Scaffold(
            topBar = {
                TopAppBarComposable(currentRoute, navController)
            },
            snackbarHost = { SnackbarHost(snackbarHostState) },
            content = { innerPadding ->
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavHost(
                        navController = navController,
                        viewModel = viewModel,
                        snackbarState = snackbarHostState
                    )
                }
            }
        )
    }

    @Composable
    private fun TopAppBarComposable(
        currentRoute: String?,
        navController: NavHostController
    ) {
        TopAppBar(
            title = {
                Text(
                    getString(R.string.news_app),
                    style = MaterialTheme.typography.titleLarge
                )
            },
            navigationIcon = {
                //Handling navigation icon based on current route
                when (currentRoute) {
                    NewsAppRoutes.NewsHomeScreen.route -> {
                        IconButton(onClick = {
                            finish()
                        }) {
                            Icon(Icons.Default.Close, contentDescription = "close")
                        }
                    }

                    else -> {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(
                                Icons.Default.ArrowBack,
                                contentDescription = "back"
                            )
                        }
                    }
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                titleContentColor = MaterialTheme.colorScheme.primary,
            )
        )
    }
}