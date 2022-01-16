package com.bloder.boatcore.effects.predictable.contracts.dsl

import com.bloder.boatcore.effects.predictable.contracts.BoatRouteContract

fun interface RouteContract {

    fun RouteContractContext.config(): BoatRouteContract
}