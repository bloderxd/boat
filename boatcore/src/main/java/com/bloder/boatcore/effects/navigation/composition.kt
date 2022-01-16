package com.bloder.boatcore.effects.navigation

import android.content.Context
import android.os.Bundle
import android.os.Parcelable
import com.bloder.boatcore.Boat
import com.bloder.boatcore.dsl.BoatContext
import com.bloder.boatcore.routes.BoatRoutes

fun Boat.asEffect(): BoatNavigationEffect = object : BoatNavigationEffect {

    override fun identity(): BoatRoutes = with(this@asEffect) { BoatContext.config() }

    override fun navigate(
        context: Context,
        route: String,
        data: Map<String, Parcelable>?,
        additionalFlags: Int?,
        options: Bundle?
    ) {
        identity()[route]?.navigate(
            context = context,
            data = data,
            additionalFlags = additionalFlags,
            options = options
        )
    }
}

fun BoatNavigationEffect.composeWith(navigationEffect: BoatNavigationEffect): BoatNavigationEffect = object : BoatNavigationEffect {
    override fun identity(): BoatRoutes = this@composeWith.identity().combine(navigationEffect.identity())
    override fun navigate(context: Context, route: String, data: Map<String, Parcelable>?, additionalFlags: Int?, options: Bundle?) { this@composeWith.navigate(context, route, data, additionalFlags, options) }
}

infix operator fun BoatNavigationEffect.plus(navigationEffect: BoatNavigationEffect) = composeWith(navigationEffect)