package com.bloder.boatcore

import android.app.Activity
import com.bloder.boatcore.effects.navigation.BoatNavigationEffect
import com.bloder.boatcore.effects.navigation.effect
import com.bloder.boatcore.effects.plus
import com.bloder.boatcore.effects.predictable.contracts.BoatRouteContractEffect
import com.bloder.boatcore.effects.predictable.contracts.effect
import com.bloder.boatcore.effects.predictable.contracts.dsl.RouteContract
import org.junit.Test

class BoatPredictableTest {

    @Test
    fun `assert N routes are in boat identity`() {
        val boat: BoatNavigationEffect = Boat { compose("A") { Activity::class } }.effect()
        val contractBoat: BoatRouteContractEffect = RouteContract { compose("A") }.effect { "Route $this should be compose in boat" }
        val boat2 = boat + contractBoat
        assert(boat2.identity().containsKey("A"))
    }

    @Test
    fun `assert contract is not satisfiable`() {
        val boat: BoatNavigationEffect = Boat { compose("B") { Activity::class } }.effect()
        val contractBoat: BoatRouteContractEffect = RouteContract { compose("A") }.effect { "Route $this should be composed in boat" }
        try {
            boat + contractBoat
        } catch (e: Throwable) {
            assert(e.message == "Route A should be composed in boat")
        }
    }

    @Test
    fun `assert contract is not satisfiable with multiple effect composition`() {
        val boat: BoatNavigationEffect = Boat { compose("A") { Activity::class } }.effect()
        val boat2: BoatNavigationEffect = Boat { compose("B") { Activity::class } }.effect()
        val boat3: BoatNavigationEffect = Boat { compose("C") { Activity::class } }.effect()
        val contractBoat: BoatRouteContractEffect = RouteContract {
            compose("A")
            compose("B")
            compose("C")
            compose("D")
            compose("E")
        }.effect { "Routes $this should be composed in boat" }
        try {
            boat + boat2 + boat3 + contractBoat
        } catch (e: Throwable) {
            assert(e.message == "Routes D, E should be composed in boat")
        }
    }

    @Test
    fun `assert contract is satisfiable with multiple effect composition`() {
        val boat: BoatNavigationEffect = Boat { compose("A") { Activity::class } }.effect()
        val boat2: BoatNavigationEffect = Boat { compose("B") { Activity::class } }.effect()
        val boat3: BoatNavigationEffect = Boat { compose("C") { Activity::class } }.effect()
        val boat4: BoatNavigationEffect = Boat { compose("D") { Activity::class } }.effect()
        val boat5: BoatNavigationEffect = Boat { compose("E") { Activity::class } }.effect()
        val contractBoat: BoatRouteContractEffect = RouteContract {
            compose("A")
            compose("B")
            compose("C")
            compose("D")
            compose("E")
        }.effect { "Routes $this should be composed in boat" }
        val appBoat = boat + boat2 + boat3 + boat4 + boat5 + contractBoat
        assert(appBoat.identity().isNotEmpty())
    }
}