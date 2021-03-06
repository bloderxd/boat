package com.bloder.boatcore.effects.middleware

import android.content.Context
import android.os.Bundle
import android.os.Parcelable
import com.bloder.boatcore.effects.navigation.BoatNavigationEffect
import com.bloder.boatcore.effects.test.BoatTestEffect
import com.bloder.boatcore.routes.BoatRoutes

fun boatMiddleware(middle: suspend (route: String, data: Map<String, Parcelable>?, additionalFlags: Int?, options: Bundle?, navigate: suspend () -> Unit) -> Unit): BoatMiddlewareEffect = object : BoatMiddlewareEffect {
    override suspend fun middle(route: String, data: Map<String, Parcelable>?, additionalFlags: Int?, options: Bundle?, navigate: suspend () -> Unit) { middle(route, data, additionalFlags, options, navigate) }
    override fun identity(): BoatRoutes = mapOf()
}

fun BoatNavigationEffect.composeWith(middlewareEffect: BoatMiddlewareEffect): BoatMiddlewareEffect = object : BoatMiddlewareEffect {
    override suspend fun middle(route: String, data: Map<String, Parcelable>?, additionalFlags: Int?, options: Bundle?, navigate: suspend () -> Unit) = middlewareEffect.middle(route, data, additionalFlags, options, navigate)
    override fun identity(): BoatRoutes = this@composeWith.identity()
    override suspend fun navigate(context: Context, route: String, data: Map<String, Parcelable>?, additionalFlags: Int?, options: Bundle?) {
        middle(route, data, additionalFlags, options) { this@composeWith.navigate(context, route, data, additionalFlags, options) }
    }
}

fun BoatMiddlewareEffect.composeWith(middlewareEffect: BoatMiddlewareEffect): BoatMiddlewareEffect = object : BoatMiddlewareEffect {
    override suspend fun middle(route: String, data: Map<String, Parcelable>?, additionalFlags: Int?, options: Bundle?, navigate: suspend () -> Unit) {
        this@composeWith.middle(route, data, additionalFlags, options) { middlewareEffect.middle(route, data, additionalFlags, options) { navigate() } }
    }
    override fun identity(): BoatRoutes = this@composeWith.identity()
    override suspend fun navigateIdentity(context: Context, route: String, data: Map<String, Parcelable>?, additionalFlags: Int?, options: Bundle?) { this@composeWith.navigateIdentity(context, route, data, additionalFlags, options) }
}

fun BoatTestEffect.composeWith(middlewareEffect: BoatMiddlewareEffect): BoatMiddlewareEffect = object : BoatMiddlewareEffect {
    override suspend fun middle(route: String, data: Map<String, Parcelable>?, additionalFlags: Int?, options: Bundle?, navigate: suspend () -> Unit) = middlewareEffect.middle(route, data, additionalFlags, options, navigate)
    override suspend fun navigateIdentity(context: Context, route: String, data: Map<String, Parcelable>?, additionalFlags: Int?, options: Bundle?) { this@composeWith.navigate(context, route, data, additionalFlags, options) }
    override fun identity(): BoatRoutes = this@composeWith.identity()
    override suspend fun navigate(context: Context, route: String, data: Map<String, Parcelable>?, additionalFlags: Int?, options: Bundle?) {
        middle(route, data, additionalFlags, options) { navigateIdentity(context, route, data, additionalFlags, options) }
    }
}