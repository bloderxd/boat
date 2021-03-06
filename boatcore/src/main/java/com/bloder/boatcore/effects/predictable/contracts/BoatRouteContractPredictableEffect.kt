package com.bloder.boatcore.effects.predictable.contracts

import arrow.core.Either
import com.bloder.boatcore.effects.predictable.BoatPredictableEffect
import com.bloder.boatcore.effects.predictable.contracts.dsl.RouteContract
import com.bloder.boatcore.effects.predictable.contracts.dsl.RouteContractContext
import com.bloder.boatcore.effects.predictable.predictableEffect
import com.bloder.boatcore.routes.BoatRoutes

typealias BoatRouteContract = List<String>
typealias BoatRouteContractEffect = BoatPredictableEffect<BoatRoutes, String>

fun RouteContract.effect(failed: String.() -> String): BoatRouteContractEffect = predictableEffect { routes ->
    val failedConstraints: MutableList<String> = mutableListOf()
    with(RouteContractContext.Config()) {
        config().fold(routes.keys) { ids, route ->
            if (!ids.contains(route)) failedConstraints.add(route)
            ids
        }
    }
    if (failedConstraints.isEmpty()) Either.Right(this@predictableEffect) else Either.Left(failedConstraints.reduce { acc, s -> "$acc, $s" }.failed())
}