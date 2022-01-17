package com.bloder.boatcore.effects.predictable.contracts

import com.bloder.boatcore.effects.navigation.BoatNavigationEffect
import com.bloder.boatcore.effects.predictable.BoatPredictableEffect
import com.bloder.boatcore.routes.BoatRoutes

fun BoatNavigationEffect.composeWith(contractEffect: BoatPredictableEffect<BoatRoutes, String>): BoatNavigationEffect {
    val pred = contractEffect.predicate(identity())
    require(pred.isRight()) { pred.fold({ it }, { "" }) }
    return this
}

infix operator fun BoatNavigationEffect.plus(contractEffect: BoatPredictableEffect<BoatRoutes, String>): BoatNavigationEffect = composeWith(contractEffect)