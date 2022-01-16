package com.bloder.boatcore.effects.predictable

import android.content.Context
import android.os.Bundle
import android.os.Parcelable
import arrow.core.Either
import com.bloder.boatcore.effects.navigation.BoatNavigationEffect
import com.bloder.boatcore.routes.BoatRoutes

fun <B, C> predictableEffect(pred: BoatNavigationEffect.(B, C.() -> String) -> Either<C, BoatNavigationEffect>): BoatPredictableEffect<B, C> = object : BoatPredictableEffect<B, C> {
    override fun predicate(
        constraints: B,
        failed: C.() -> String
    ): Either<C, BoatNavigationEffect> {
        return pred(constraints, failed)
    }

    override fun navigate(
        context: Context,
        route: String,
        data: Map<String, Parcelable>?,
        additionalFlags: Int?,
        options: Bundle?
    ) {}

    override fun identity(): BoatRoutes = mapOf()
}