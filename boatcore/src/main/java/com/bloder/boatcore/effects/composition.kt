package com.bloder.boatcore.effects

import com.bloder.boatcore.effects.middleware.BoatMiddlewareEffect
import com.bloder.boatcore.effects.middleware.composeWith
import com.bloder.boatcore.effects.navigation.BoatNavigationEffect
import com.bloder.boatcore.effects.navigation.composeWith
import com.bloder.boatcore.effects.predictable.BoatPredictableEffect
import com.bloder.boatcore.effects.predictable.contracts.composeWith
import com.bloder.boatcore.effects.test.BoatTestEffect
import com.bloder.boatcore.effects.test.composeWith
import com.bloder.boatcore.routes.BoatRoutes

infix operator fun BoatNavigationEffect.plus(middlewareEffect: BoatMiddlewareEffect): BoatMiddlewareEffect = composeWith(middlewareEffect)
infix operator fun BoatMiddlewareEffect.plus(middlewareEffect: BoatMiddlewareEffect): BoatMiddlewareEffect = composeWith(middlewareEffect)
infix operator fun BoatTestEffect.plus(middlewareEffect: BoatMiddlewareEffect): BoatMiddlewareEffect = composeWith(middlewareEffect)
infix operator fun BoatNavigationEffect.plus(navigationEffect: BoatNavigationEffect) = composeWith(navigationEffect)
infix operator fun BoatNavigationEffect.plus(contractEffect: BoatPredictableEffect<BoatRoutes, String>): BoatNavigationEffect = composeWith(contractEffect)
infix operator fun BoatTestEffect.plus(boatTestEffect: BoatTestEffect): BoatTestEffect = composeWith(boatTestEffect)