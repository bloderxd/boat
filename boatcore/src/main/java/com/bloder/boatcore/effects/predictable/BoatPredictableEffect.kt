package com.bloder.boatcore.effects.predictable

import android.content.Context
import android.os.Bundle
import android.os.Parcelable
import arrow.core.Either
import com.bloder.boatcore.effects.navigation.BoatNavigationEffect
import com.bloder.boatcore.routes.BoatRoutes

interface BoatPredictableEffect<B, C> : BoatNavigationEffect {

    fun predicate(constraints: B, failed: C.() -> String): Either<C, BoatNavigationEffect>
}