package com.bloder.boatcore.effects.predictable.contracts.dsl

import com.bloder.boatcore.effects.predictable.contracts.BoatRouteContract

interface RouteContractContext {

    fun compose(id: String): BoatRouteContract

    class Config : RouteContractContext {
        private val routes: MutableList<String> = mutableListOf()

        override fun compose(id: String): BoatRouteContract {
            routes.add(id)
            return routes
        }
    }
}