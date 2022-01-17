package com.bloder.boatcore.effects.predictable

import arrow.core.Either
import com.bloder.boatcore.effects.navigation.BoatNavigationEffect
import com.bloder.boatcore.routes.BoatRoutes

fun <A, B> predictableEffect(pred: BoatNavigationEffect.(A) -> Either<B, BoatNavigationEffect>): BoatPredictableEffect<A, B> = object : BoatPredictableEffect<A, B> {
    override fun predicate(constraints: A): Either<B, BoatNavigationEffect> = pred(constraints)
    override fun identity(): BoatRoutes = mapOf()
}