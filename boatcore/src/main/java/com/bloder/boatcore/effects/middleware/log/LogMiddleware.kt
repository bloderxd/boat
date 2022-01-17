package com.bloder.boatcore.effects.middleware.log

import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import com.bloder.boatcore.effects.middleware.BoatMiddlewareEffect
import com.bloder.boatcore.effects.middleware.boatMiddleware

fun logMiddleware(log: (route: String, data: Map<String, Parcelable>?, additionalFlags: Int?, options: Bundle?) -> String): BoatMiddlewareEffect = boatMiddleware { route, data, additionalFlags, options, navigate ->
    Log.d("BOAT_LOG_MIDDLEWARE", log(route, data, additionalFlags, options))
    navigate()
}