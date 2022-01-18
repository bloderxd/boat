package com.bloder.boat

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.bloder.boat.ui.theme.BoatTheme
import com.bloder.boatcore.Boat
import com.bloder.boatcore.effects.middleware.BoatMiddlewareEffect
import com.bloder.boatcore.effects.middleware.boatMiddleware
import com.bloder.boatcore.effects.navigation.BoatNavigationEffect
import com.bloder.boatcore.effects.navigation.effect
import com.bloder.boatcore.effects.plus
import com.bloder.boatcore.effects.predictable.contracts.effect
import com.bloder.boatcore.effects.predictable.contracts.dsl.RouteContract

val navigation: BoatNavigationEffect = Boat {
    compose("A") { ActivityA::class }
    compose("B") { ActivityB::class }
}.effect()

val navigation2: BoatNavigationEffect = Boat {
    compose("C") { Activity::class }
}.effect()

val routeContract = RouteContract {
    compose("A")
}.effect { "Route $this should be composed in navigation" }

val logMiddlewareEffect: BoatMiddlewareEffect = boatMiddleware { route, _, _, _, navigate ->
    Log.d("BoatLog", "Navigating to $route...")
    navigate()
    Log.d("BoatLog", "Navigated to $route!")
}

val trackMiddlewareEffect: BoatMiddlewareEffect = boatMiddleware { route, _, _, _, navigate ->
    Log.d("Tracker", "Tracking $route...")
    navigate()
    Log.d("Tracker", "$route tracked!")
}

val appNavigation: BoatNavigationEffect = navigation + navigation2 + routeContract + logMiddlewareEffect + trackMiddlewareEffect

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BoatTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Greeting("Android")
                }
            }
        }
        MainViewModel().navigateTo(this, appNavigation, "A")
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    BoatTheme {
        Greeting("Android")
    }
}