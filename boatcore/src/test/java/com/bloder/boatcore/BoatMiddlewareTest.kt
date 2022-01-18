package com.bloder.boatcore

import android.app.Activity
import com.bloder.boatcore.effects.middleware.BoatMiddlewareEffect
import com.bloder.boatcore.effects.middleware.boatMiddleware
import com.bloder.boatcore.effects.plus
import com.bloder.boatcore.effects.predictable.contracts.BoatRouteContractEffect
import com.bloder.boatcore.effects.predictable.contracts.dsl.RouteContract
import com.bloder.boatcore.effects.predictable.contracts.effect
import com.bloder.boatcore.effects.test.BoatTestEffect
import com.bloder.boatcore.effects.test.testEffect
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test

@ExperimentalCoroutinesApi
class BoatMiddlewareTest {

    @Test
    fun `validate middleware log message`() = runTest {
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

    @Test
    fun `assert middleware composition respect the correct sequence`() = runTest {
        val flow: MutableList<String> = mutableListOf()
        val test: BoatTestEffect = Boat { compose("A") { Activity::class } }.testEffect { _, _, _, _-> println("navigating") }
        val printMiddleware1: BoatMiddlewareEffect = boatMiddleware { route, _, _, _, navigate ->
            println("Navigating 1 to $route")
            flow.add("N1")
            navigate()
            println("Finished 1 to $route")
            flow.add("F1")
        }
        val printMiddleware2: BoatMiddlewareEffect = boatMiddleware { route, _, _, _, navigate ->
            println("Navigating 2 to $route")
            flow.add("N2")
            navigate()
            println("Finished 2 to $route")
            flow.add("F2")
        }
        val boat = test + printMiddleware1 + printMiddleware2
        boat.navigate(mockk(), "A")
        assert(flow[0] == "N1")
        assert(flow[1] == "N2")
        assert(flow[2] == "F2")
        assert(flow[3] == "F1")
    }

    @Test
    fun `validate middleware composition with more than 2 middlewares and with other constraints`() = runTest {
        val flow: MutableList<String> = mutableListOf()
        val test: BoatTestEffect = Boat { compose("A") { Activity::class } }.testEffect { _, _, _, _-> println("navigating") }
        val test2: BoatTestEffect = Boat { compose("B") { Activity::class } }.testEffect { _, _, _, _-> println("navigating2") }
        val test3: BoatTestEffect = Boat { compose("C") { Activity::class } }.testEffect { _, _, _, _-> println("navigating3") }
        val printMiddleware1: BoatMiddlewareEffect = boatMiddleware { route, _, _, _, navigate ->
            println("Navigating 1 to $route")
            flow.add("N1")
            navigate()
            println("Finished 1 to $route")
            flow.add("F1")
        }
        val printMiddleware2: BoatMiddlewareEffect = boatMiddleware { route, _, _, _, navigate ->
            println("Navigating 2 to $route")
            flow.add("N2")
            navigate()
            println("Finished 2 to $route")
            flow.add("F2")
        }
        val printMiddleware3: BoatMiddlewareEffect = boatMiddleware { route, _, _, _, navigate ->
            println("Navigating 3 to $route")
            flow.add("N3")
            navigate()
            println("Finished 3 to $route")
            flow.add("F3")
        }
        val printMiddleware4: BoatMiddlewareEffect = boatMiddleware { route, _, _, _, navigate ->
            println("Navigating 4 to $route")
            flow.add("N4")
            navigate()
            println("Finished 4 to $route")
            flow.add("F4")
        }
        val contractBoat: BoatRouteContractEffect = RouteContract { compose("B") }.effect { "route $this should be composed in boat" }
        val boat = test + test2 + test3 + printMiddleware1 + printMiddleware2 + printMiddleware3 + printMiddleware4 + contractBoat
        boat.navigate(mockk(), "A")
        assert(flow[0] == "N1")
        assert(flow[1] == "N2")
        assert(flow[2] == "N3")
        assert(flow[3] == "N4")
        assert(flow[4] == "F4")
        assert(flow[5] == "F3")
        assert(flow[6] == "F2")
        assert(flow[7] == "F1")
    }
}