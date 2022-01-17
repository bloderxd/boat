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

# Concept
Boat is build on top of a simple concept: *It's all about effects composition*. Boat provides some effects that are built on top of its compositions, all effects must respect some laws:

- All identities are immutable and composition doesn't break this.
- Every composition create a new effect with correct configuration.
- During the composition none of composed effects are affected. 
