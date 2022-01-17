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

- All identities are immutable and composition doesn't break it.
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
}.effect()

fun main(context: Context) {
  appNavigation.navigate(context, "/second") // Navigating to SecondActivity
}
```

Since we work with effects composition with all immutable and scoped laws preserved, we are free to play as we want:

```kotlin
val appNavigation: BoatNavigationEffect = Boat {
  compose("/first") { FirstActivity::class }
  compose("/second") { SecondActivity::class }
}.effect()

fun main() {
  myExternalModule(appNavigation)
}

...

private val myModuleNavigation: BoatNavigationEffect = Boat {
  compose("/my_module_1") { MyModuleFirstActivity::class }
  compose("/my_module_2") { MyModuleSecondActivity::class }
}.effect()

fun myExternalModule(navigation: BoatNavigationEffect) {
  registerInMyDI(navigation + myModuleNavigation)
}

fun registerInMyDI(navigation: BoatNavigationEffect) {
  // work with a composed navigation
}
```

To compose effects in Boat we use `+` operator, in this example we just created an internal navigation effect for a external module and composed with an injected navigation effect, by that my external module can navigate to `/my_module_1`, `/my_module_2`, `/first` and `/second` routes with no impact to `appNavigation` that never knows about `/my_module_1` and `/my_module_2` routes.

### Route contract effect
`BoatRouteContractEffect` validates if `N` routes are composed in boat navigation identity during the composition:

```kotlin
val navigation: BoatNavigationEffect = Boat {
  compose("/first") { FirstActivity::class }
  compose("/second") { SecondActivity::class }
}.effect()

val appRouteContracts: BoatRouteContractEffect = RouteContract {
  compose("/first")
  compose("/second")
  compose("/third")
  compose("/fourth")
}.effect { "Routes $this should be composed in navigation" }

val appNavigation = navigation + appRouteContracts // java.lang.IllegalArgumentException: Routes /third, /fourth should be composed in navigation
```

In this example we've created a navigation with two routes and a contract with 4 routes, by creating this contract effect what we want is to make sure that composed navigation effect identity compose all these routes that we've declared in contract. In this case we receive a throw of a exception with our custom message saying that composed navigation effect doesn't satisfies our contract. Once we compose the other two missing routes, we're good:

```kotlin
val navigation: BoatNavigationEffect = Boat {
  ...
  compose("/third") { FirstActivity::class }
  compose("/fourth") { SecondActivity::class }
}.effect()

...

val appNavigation = navigation + appRouteContracts // OK!
```
