package com.rijaldev.mynavdrawer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rijaldev.mynavdrawer.model.MenuItem
import com.rijaldev.mynavdrawer.ui.theme.MyNavDrawerTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyNavDrawerTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MyNavDrawerApp()
                }
            }
        }
    }
}

@Composable
fun MyNavDrawerApp() {
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            MyTopBar(
                onMenuClick = {
                    scope.launch {
                        scaffoldState.drawerState.open()
                    }
                }
            )
        },
        drawerContent = {
            MyDrawerContent(
                onItemSelected = { title ->
                    scope.launch {
                        scaffoldState.drawerState.close()
                        scaffoldState.snackbarHostState.showSnackbar(
                            message = stringResource(R.string.coming_soon, title),
                            actionLabel = stringResource(R.string.subscribe_question)
                        )
                    }
                }
            )
        },
        drawerGesturesEnabled = scaffoldState.drawerState.isOpen,
    ) { paddingValues ->
        Box(
            modifier = Modifier.fillMaxSize().padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            Text(stringResource(R.string.hello_world))
        }
    }
}

@Composable
fun MyTopBar(onMenuClick: () -> Unit) {
    TopAppBar(
        navigationIcon = {
            IconButton(
                onClick = { onMenuClick() }
            ) {
               Icon(
                   imageVector = Icons.Default.Menu,
                   contentDescription = stringResource(R.string.menu)
               )
            }
        },
        title = {
            Text(stringResource(R.string.app_name))
        },
    )
}

@Composable
fun MyDrawerContent(
    modifier: Modifier = Modifier,
    onItemSelected: (title: String) -> Unit,
) {
    val items = listOf(
        MenuItem(
            title = stringResource(R.string.home),
            icon = Icons.Default.Home
        ),
        MenuItem(
            title = stringResource(R.string.favourite),
            icon = Icons.Default.Favorite
        ),
        MenuItem(
            title = stringResource(R.string.profile),
            icon = Icons.Default.AccountCircle
        ),
    )
    Column(modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .height(190.dp)
                .fillMaxWidth()
                .background(MaterialTheme.colors.primary)
        )
        for (item in items) {
            Row(
                modifier = Modifier
                    .clickable { onItemSelected(item.title) }
                    .padding(vertical = 12.dp, horizontal = 16.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = item.icon,
                    contentDescription = item.title,
                    tint = Color.DarkGray
                )
                Spacer(modifier = Modifier.width(32.dp))
                Text(text = item.title, style = MaterialTheme.typography.subtitle2)
            }
        }
        Divider()
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MyNavDrawerTheme {
        MyNavDrawerApp()
    }
}