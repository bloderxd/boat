package com.bloder.boatcore.effects.middleware

import android.content.Context
import android.os.Bundle
import android.os.Parcelable
import com.bloder.boatcore.effects.navigation.BoatNavigationEffect

interface BoatMiddlewareEffect : BoatNavigationEffect {

    fun middle(route: String, data: Map<String, Parcelable>?, additionalFlags: Int?, options: Bundle?, navigate: () -> Unit)

    override fun navigate(
        context: Context,
        route: String,
        data: Map<String, Parcelable>?,
        additionalFlags: Int?,
        options: Bundle?
    ) {
        middle(route, data, additionalFlags, options) { super.navigate(context, route, data, additionalFlags, options) }
    }
}