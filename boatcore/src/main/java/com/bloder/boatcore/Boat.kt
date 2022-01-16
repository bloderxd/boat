package com.bloder.boatcore

import com.bloder.boatcore.dsl.BoatContext
import com.bloder.boatcore.routes.BoatRoutes

fun interface Boat {

    fun BoatContext.config(): BoatRoutes
}