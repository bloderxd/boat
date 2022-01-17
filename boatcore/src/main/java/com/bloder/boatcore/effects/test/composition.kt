package com.bloder.boatcore.effects.test

import android.content.Context
import android.os.Bundle
import android.os.Parcelable
import com.bloder.boatcore.Boat
import com.bloder.boatcore.dsl.BoatContext
import com.bloder.boatcore.routes.BoatRoutes

fun Boat.testEffect(navigate: (String, Map<String, Parcelable>?, additionalFlags: Int?, options: Bundle?) -> Unit): BoatTestEffect = object : BoatTestEffect {
    override fun mockNavigate(
        context: Context,
        route: String,
        data: Map<String, Parcelable>?,
        additionalFlags: Int?,
        options: Bundle?
    ) {
        navigate(route, data, additionalFlags, options)
    }
    override fun identity(): BoatRoutes = BoatContext.Config().config()
}

fun BoatTestEffect.composeWith(boatTestEffect: BoatTestEffect): BoatTestEffect = object : BoatTestEffect {
    override fun mockNavigate(context: Context, route: String, data: Map<String, Parcelable>?, additionalFlags: Int?, options: Bundle?) {
        boatTestEffect.mockNavigate(context, route, data, additionalFlags, options)
    }

    override fun identity(): BoatRoutes = this@composeWith.identity().combine(boatTestEffect.identity())
}