package com.bloder.boatcore.effects.middleware

import android.content.Context
import android.os.Bundle
import android.os.Parcelable
import com.bloder.boatcore.effects.navigation.BoatNavigationEffect
import com.bloder.boatcore.effects.test.BoatTestEffect
import com.bloder.boatcore.routes.BoatRoutes

fun boatMiddleware(middle: (route: String, data: Map<String, Parcelable>?, additionalFlags: Int?, options: Bundle?, navigate: () -> Unit) -> Unit): BoatMiddlewareEffect = object : BoatMiddlewareEffect {
    override fun middle(route: String, data: Map<String, Parcelable>?, additionalFlags: Int?, options: Bundle?, navigate: () -> Unit) { middle(route, data, additionalFlags, options, navigate) }
    override fun identity(): BoatRoutes = mapOf()
}

fun BoatNavigationEffect.composeWith(middlewareEffect: BoatMiddlewareEffect): BoatMiddlewareEffect = object : BoatMiddlewareEffect {
    override fun middle(route: String, data: Map<String, Parcelable>?, additionalFlags: Int?, options: Bundle?, navigate: () -> Unit) = middlewareEffect.middle(route, data, additionalFlags, options, navigate)
    override fun identity(): BoatRoutes = this@composeWith.identity()
}

fun BoatMiddlewareEffect.composeWith(middlewareEffect: BoatMiddlewareEffect): BoatMiddlewareEffect = object : BoatMiddlewareEffect {
    override fun middle(route: String, data: Map<String, Parcelable>?, additionalFlags: Int?, options: Bundle?, navigate: () -> Unit) {
        this@composeWith.middle(route, data, additionalFlags, options) { middlewareEffect.middle(route, data, additionalFlags, options, navigate) }
    }
    override fun identity(): BoatRoutes = identity()
}

fun BoatTestEffect.composeWith(middlewareEffect: BoatMiddlewareEffect): BoatMiddlewareEffect = object : BoatMiddlewareEffect {
    override fun middle(route: String, data: Map<String, Parcelable>?, additionalFlags: Int?, options: Bundle?, navigate: () -> Unit) = middlewareEffect.middle(route, data, additionalFlags, options, navigate)
    override fun identity(): BoatRoutes = this@composeWith.identity()
    override fun navigate(context: Context, route: String, data: Map<String, Parcelable>?, additionalFlags: Int?, options: Bundle?) {
        middle(route, data, additionalFlags, options) { this@composeWith.navigate(context, route, data, additionalFlags, options) }
    }
}

infix operator fun BoatNavigationEffect.plus(middlewareEffect: BoatMiddlewareEffect): BoatMiddlewareEffect = composeWith(middlewareEffect)
infix operator fun BoatMiddlewareEffect.plus(middlewareEffect: BoatMiddlewareEffect): BoatMiddlewareEffect = composeWith(middlewareEffect)
infix operator fun BoatTestEffect.plus(middlewareEffect: BoatMiddlewareEffect): BoatMiddlewareEffect = composeWith(middlewareEffect)
