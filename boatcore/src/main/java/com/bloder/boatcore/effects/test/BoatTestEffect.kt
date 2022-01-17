package com.bloder.boatcore.effects.test

import android.content.Context
import android.os.Bundle
import android.os.Parcelable
import com.bloder.boatcore.effects.navigation.BoatNavigationEffect

interface BoatTestEffect : BoatNavigationEffect {

    fun mockNavigate(
        context: Context,
        route: String,
        data: Map<String, Parcelable>?,
        additionalFlags: Int?,
        options: Bundle?
    )

    override fun navigate(
        context: Context,
        route: String,
        data: Map<String, Parcelable>?,
        additionalFlags: Int?,
        options: Bundle?
    ) {
        mockNavigate(context, route, data, additionalFlags, options)
    }
}