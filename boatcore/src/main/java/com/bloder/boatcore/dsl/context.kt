package com.bloder.boatcore.dsl

import android.app.Activity
import com.bloder.boatcore.routes.BoatRoutes
import com.bloder.boatcore.routes.Route
import kotlin.reflect.KClass

interface BoatContext {

    fun compose(id: String, route: () -> KClass<out Activity>): BoatRoutes
    fun compose(id: String, route: Route): BoatRoutes

    companion object : BoatContext {

        private val boatRoutes: MutableMap<String, Route> = mutableMapOf()

        override fun compose(id: String, route: () -> KClass<out Activity>): BoatRoutes {
            return boatRoutes.apply { put(id, Route.config(route())) }
        }

        override fun compose(id: String, route: Route): BoatRoutes {
            return boatRoutes.apply { put(id, route) }
        }
    }
}