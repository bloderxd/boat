package com.bloder.boatcore.routes

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import kotlin.reflect.KClass

fun interface Route {

    fun navigate(
        context: Context,
        data: Map<String, Parcelable>?,
        additionalFlags: Int?,
        options: Bundle?
    )

    companion object {

        fun config(activity: KClass<out Activity>, flags: Int = 0): Route = Route { context, data, additionalFlags, options ->
            Intent(context, activity.java).addFlags(flags).apply {
                data?.onEach { putExtra(it.key, it.value) }
                addFlags(flags)
                additionalFlags?.apply { addFlags(this) }
            }.also {
                context.startActivity(it, options)
            }
        }
    }
}