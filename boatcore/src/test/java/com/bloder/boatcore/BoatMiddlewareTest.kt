package com.bloder.boatcore

import android.app.Activity
import com.bloder.boatcore.effects.middleware.BoatMiddlewareEffect
import com.bloder.boatcore.effects.middleware.boatMiddleware
import com.bloder.boatcore.effects.middleware.plus
import com.bloder.boatcore.effects.test.BoatTestEffect
import com.bloder.boatcore.effects.test.testEffect
import io.mockk.mockk
import org.junit.Test

class BoatMiddlewareTest {

    @Test
    fun `validate middleware log message`() {
        var navigated = false
        var printed = false
        val test: BoatTestEffect = Boat { compose("A") { Activity::class } }.testEffect { _, _, _, _->
            println("navigating")
            navigated = true
        }
        val middlewareEffect: BoatMiddlewareEffect = boatMiddleware { route, _, _, _, navigate ->
            println("Navigating to $route")
            navigate()
            println("Navigated to $route")
            printed = true
        }
        val boat = test + middlewareEffect
        boat.navigate(mockk(), "A")
        assert(navigated)
        assert(printed)
    }
}