package com.bloder.boatcore.effects.middleware

import android.content.Context
import android.os.Bundle
import android.os.Parcelable
import com.bloder.boatcore.effects.navigation.BoatNavigationEffect

interface BoatMiddlewareEffect : BoatNavigationEffect {

    suspend fun middle(route: String, data: Map<String, Parcelable>?, additionalFlags: Int?, options: Bundle?, navigate: suspend () -> Unit)

    suspend fun navigateIdentity(context: Context, route: String, data: Map<String, Parcelable>?, additionalFlags: Int?, options: Bundle?) {
        super.navigate(context, route, data, additionalFlags, options)
    }

    override suspend fun navigate(
        context: Context,
        route: String,
        data: Map<String, Parcelable>?,
        additionalFlags: Int?,
        options: Bundle?
    ) {
        middle(route, data, additionalFlags, options) { navigateIdentity(context, route, data, additionalFlags, options) }
    }
}