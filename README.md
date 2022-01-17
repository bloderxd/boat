# Boat
Boat is an implementation of a scoped, simple and composable way to navigate

```kotlin
val moduleFirst: BoatNavigationEffect = Boat {
  compose("/first") { FirstActivity::class }
}.effect()

val moduleSecond: BoatNavigationEffect = Boat {
  compose("/second") { SecondActivity::class }
}.effect()

val moduleThird: BoatNavigationEffect = Boat {
  compose("/third") { ThirdActivity::class }
}.effect()

val appNavigation: BoatNavigationEffect = moduleFirst + moduleSecond + moduleThird

fun main() {
  navigate(context, appNavigation)
}

fun navigate(context: Context, navigation: BoatNavigationEffect) {
  navigation.navigate(context, "/first") // Navigating to FirstActivity
  navigation.navigate(context, "/second") // Navigating to SecondActivity
  navigation.navigate(context, "/third") // Navigating to ThirdActivity
}
```
