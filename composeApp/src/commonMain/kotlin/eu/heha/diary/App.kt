package eu.heha.diary

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.WindowAdaptiveInfo
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteType
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.window.core.layout.WindowHeightSizeClass
import androidx.window.core.layout.WindowWidthSizeClass
import diary.composeapp.generated.resources.Res
import diary.composeapp.generated.resources.compose_multiplatform
import diary.composeapp.generated.resources.nav_dashboard
import diary.composeapp.generated.resources.nav_example
import diary.composeapp.generated.resources.nav_settings
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    MaterialTheme {
        var currentRootDestination by remember { mutableStateOf(RootNavigationDestination.Dashboard) }
        NavigationSuiteScaffold(
            layoutType = navigationLayoutType(currentWindowAdaptiveInfo()),
            navigationSuiteItems = {
                RootNavigationDestination.entries.forEach { destination ->
                    item(
                        icon = { Icon(destination.icon(), contentDescription = null) },
                        label = { Text(stringResource(destination.title)) },
                        selected = currentRootDestination == destination,
                        onClick = { currentRootDestination = destination }
                    )
                }
            }
        ) {
            when (currentRootDestination) {
                RootNavigationDestination.Dashboard -> {}
                RootNavigationDestination.Example -> Example()
                RootNavigationDestination.Settings -> {}
            }
        }
    }
}

private fun navigationLayoutType(
    adaptiveInfo: WindowAdaptiveInfo
): NavigationSuiteType = with(adaptiveInfo) {
    if (windowPosture.isTabletop ||
        windowSizeClass.windowHeightSizeClass == WindowHeightSizeClass.COMPACT
    ) {
        NavigationSuiteType.NavigationBar
    } else if (windowSizeClass.windowWidthSizeClass == WindowWidthSizeClass.EXPANDED) {
        NavigationSuiteType.NavigationDrawer
    } else if (windowSizeClass.windowWidthSizeClass == WindowWidthSizeClass.MEDIUM) {
        NavigationSuiteType.NavigationRail
    } else {
        NavigationSuiteType.NavigationBar
    }
}

enum class RootNavigationDestination(
    val icon: () -> ImageVector,
    val title: StringResource,
) {
    Dashboard(
        icon = { Icons.Filled.Home },
        title = Res.string.nav_dashboard
    ),
    Example(
        icon = { Icons.Filled.Person },
        title = Res.string.nav_example
    ),
    Settings(
        icon = { Icons.Filled.Settings },
        title = Res.string.nav_settings
    );
}

@Composable
private fun Example() {
    var showContent by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.primaryContainer)
            .safeContentPadding()
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Button(onClick = { showContent = !showContent }) {
            Text("Click me!")
        }
        AnimatedVisibility(showContent) {
            val greeting = remember { Greeting().greet() }
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Image(painterResource(Res.drawable.compose_multiplatform), null)
                Text("Compose: $greeting")
            }
        }
    }
}