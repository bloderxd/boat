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
  compose("/fourth") { FourthActivity::class }
}.effect()

val appNavigation: BoatNavigationEffect = moduleFirst + moduleSecond + moduleThird

fun main(context: Context) {
  navigate(context, appNavigation)
}

fun navigate(context: Context, navigation: BoatNavigationEffect) {
  navigation.navigate(context, "/first") // Navigating to FirstActivity
  navigation.navigate(context, "/second") // Navigating to SecondActivity
  navigation.navigate(context, "/third") // Navigating to ThirdActivity
  navigation.navigate(context, "/fourth") // Navigating to FourthActivity
}
```

# Concept
Boat is build on top of a simple concept: *It's all about effects composition*. Boat provides some effects that are built on top of its compositions, all effects must respect some laws:

- All identities are immutable and composition doesn't break this.
- Every composition create a new effect with correct configuration.
- During the composition none of composed effects are affected.

# Effects
As seeing before, effects are totally composables and Boat provides some of them:

### Navigation effect
`BoatNavigationEffect` is an effect that navigates to a specific route, this effect is built on top of `Boat` type with a `effect()` function, and we can simple call `navigate` function to start the navigation:

```kotlin
val appNavigation: BoatNavigationEffect = Boat {
  compose("/first") { FirstActivity::class }
  compose("/second") { SecondActivity::class }
}

fun main(context: Context) {
  appNavigation.navigate(context, "/second") // Navigating to SecondActivity
}
```

Since we work with effects composition with all immutable and scoped laws preserved, we are free to play as we want:

```kotlin
val appNavigation: BoatNavigationEffect = Boat {
  compose("/first") { FirstActivity::class }
  compose("/second") { SecondActivity::class }
}

fun main() {
  myExternalModule(appNavigation)
}

...

private val myModuleNavigation: BoatNavigationEffect = Boat {
  compose("/my_module_1") { MyModuleFirstActivity::class }
  compose("/my_module_2") { MyModuleSecondActivity::class }
}

fun myExternalModule(navigation: BoatNavigationEffect) {
  registerInMyDI(navigation + myModuleNavigation)
}

fun registerInMyDI(navigation: BoatNavigationEffect) {
  // work with a composed navigation
}
```

To compose effects in Boat we use `+` operator, in this example we just created an internal navigation effect for a external module and composed with an injected navigation effect, by that my external module can navigate to `/my_module_1`, `/my_module_2`, `/first` and `/second` routes with no impact to `appNavigation` that never knows about `/my_module_1` and `/my_module_2` routes

### Predictable effecs
Effect that validates predicates during the composition, Boat provides an implementation of a predictable effect called `RouteContract`. This effect validates during the composition if `N` routes are composed in 
