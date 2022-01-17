package com.bloder.boatcore.effects.navigation

import android.content.Context
import android.os.Bundle
import android.os.Parcelable
import com.bloder.boatcore.functional.Monoid
import com.bloder.boatcore.routes.BoatRoutes
import com.bloder.boatcore.routes.Route

interface BoatNavigationEffect : Monoid<BoatRoutes> {

    override fun BoatRoutes.combine(b: BoatRoutes): BoatRoutes {
        return entries.fold<Map.Entry<String, Route>, MutableMap<String, Route>>(mutableMapOf()) { copy, routes ->
            copy[routes.key] = routes.value
            copy
        }.also { it.putAll(b) }
    }

    fun navigate(
        context: Context,
        route: String,
        data: Map<String, Parcelable>? = null,
        additionalFlags: Int? = null,
        options: Bundle? = null
    ) {
        identity()[route]?.navigate(
            context = context,
            data = data,
            additionalFlags = additionalFlags,
            options = options
        )
    }
}