package com.bloder.boat

import android.os.Bundle
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
import com.bloder.boatcore.effects.navigation.BoatNavigationEffect
import com.bloder.boatcore.effects.navigation.effect
import com.bloder.boatcore.effects.navigation.plus
import com.bloder.boatcore.effects.predictable.contracts.effect
import com.bloder.boatcore.effects.predictable.contracts.dsl.RouteContract

val navigation: BoatNavigationEffect = Boat {
    compose("A") { ActivityA::class }
    compose("B") { ActivityB::class }
}.effect()

val navigation2: BoatNavigationEffect = Boat {
    compose("A") { ActivityA::class }
}.effect()

val routeContract = RouteContract {
    compose("A")
}.effect { "" }

val appNavigation = navigation + routeContract + navigation2

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appNavigation.navigate(this, "A")
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