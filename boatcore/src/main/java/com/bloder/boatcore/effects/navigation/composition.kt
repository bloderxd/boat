package com.bloder.boatcore.effects.navigation

import android.content.Context
import android.os.Bundle
import android.os.Parcelable
import com.bloder.boatcore.Boat
import com.bloder.boatcore.dsl.BoatContext
import com.bloder.boatcore.routes.BoatRoutes

fun Boat.effect(): BoatNavigationEffect = object : BoatNavigationEffect {
    override fun identity(): BoatRoutes = with(this@effect) { BoatContext.Config().config() }
}

fun BoatNavigationEffect.composeWith(navigationEffect: BoatNavigationEffect): BoatNavigationEffect = object : BoatNavigationEffect {
    override fun identity(): BoatRoutes = this@composeWith.identity().combine(navigationEffect.identity())
    override fun navigate(context: Context, route: String, data: Map<String, Parcelable>?, additionalFlags: Int?, options: Bundle?) = navigationEffect.navigate(context, route, data, additionalFlags, options)
}