package com.bloder.boatcore

import android.app.Activity
import com.bloder.boatcore.effects.navigation.BoatNavigationEffect
import com.bloder.boatcore.effects.navigation.effect
import com.bloder.boatcore.effects.predictable.contracts.contractEffect
import com.bloder.boatcore.effects.predictable.contracts.dsl.RouteContract
import com.bloder.boatcore.effects.predictable.contracts.plus
import org.junit.Test

class BoatPredictableTest {

    @Test
    fun `assert N routes are in boat identity`() {
        val boat: BoatNavigationEffect = Boat { compose("A") { Activity::class } }.effect()
        val contract = RouteContract { compose("A") }.contractEffect { "Route $this should be compose in boat" }
        val boat2 = boat + contract
        assert(boat2.identity().containsKey("A"))
    }

    @Test
    fun `assert contract is not satisfiable`() {
        val boat: BoatNavigationEffect = Boat { compose("B") { Activity::class } }.effect()
        val contract = RouteContract { compose("A") }.contractEffect { "Route $this should be composed in boat" }
        try {
            boat + contract
        } catch (e: Throwable) {
            assert(e.message == "Route A should be composed in boat")
        }
    }
}