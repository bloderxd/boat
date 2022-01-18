package com.bloder.boatcore.effects.navigation

import com.bloder.boatcore.Boat
import com.bloder.boatcore.dsl.BoatContext
import com.bloder.boatcore.routes.BoatRoutes

fun Boat.effect(): BoatNavigationEffect = object : BoatNavigationEffect {
    override fun identity(): BoatRoutes = with(this@effect) { BoatContext.Config().config() }
}

fun BoatNavigationEffect.composeWith(navigationEffect: BoatNavigationEffect): BoatNavigationEffect = object : BoatNavigationEffect {
    override fun identity(): BoatRoutes = this@composeWith.identity().combine(navigationEffect.identity())
}