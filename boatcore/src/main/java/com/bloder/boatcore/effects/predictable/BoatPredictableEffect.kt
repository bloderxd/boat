package com.bloder.boatcore.effects.predictable

import arrow.core.Either
import com.bloder.boatcore.effects.navigation.BoatNavigationEffect

interface BoatPredictableEffect<A, B> : BoatNavigationEffect {
    fun predicate(constraints: A): Either<B, BoatNavigationEffect>
}