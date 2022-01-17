package com.bloder.boatcore

import android.app.Activity
import com.bloder.boatcore.dsl.BoatContext
import com.bloder.boatcore.effects.navigation.BoatNavigationEffect
import com.bloder.boatcore.effects.navigation.effect
import com.bloder.boatcore.effects.navigation.plus
import com.bloder.boatcore.routes.BoatRoutes
import org.junit.Test

class BoatNavigationTest {

    @Test
    fun `validate all routes are created with boat dsl`() {
        val boat: Boat = Boat {
            compose("A") { Activity::class }
            compose("B") { Activity::class }
        }
        val config: BoatRoutes = with(boat) { BoatContext.Config().config() }
        assert(config.containsKey("A"))
        assert(config.containsKey("B"))
    }

    @Test
    fun `validate boat effect is created with boat identity`() {
        val boat: BoatNavigationEffect = Boat {
            compose("A") { Activity::class }
            compose("B") { Activity::class }
        }.effect()
        assert(boat.identity().containsKey("A"))
        assert(boat.identity().containsKey("B"))
    }

    @Test
    fun `validate boat effect composition combine multiple boat identities`() {
        val boat1: BoatNavigationEffect = Boat {
            compose("A") { Activity::class }
            compose("B") { Activity::class }
        }.effect()
        val boat2: BoatNavigationEffect = Boat {
            compose("C") { Activity::class }
            compose("D") { Activity::class }
        }.effect()
        val boat3: BoatNavigationEffect = boat1 + boat2
        assert(boat3.identity().containsKey("A"))
        assert(boat3.identity().containsKey("B"))
        assert(boat3.identity().containsKey("C"))
        assert(boat3.identity().containsKey("D"))
    }

    @Test
    fun `validate boat effect composition respect immutability law`() {
        val boat1: BoatNavigationEffect = Boat { compose("A") { Activity::class } }.effect()
        val boat2: BoatNavigationEffect = Boat { compose("B") { Activity::class } }.effect()
        val boat3: BoatNavigationEffect = boat1 + boat2
        assert(boat3.identity().containsKey("A"))
        assert(boat3.identity().containsKey("B"))
        assert(!boat1.identity().containsKey("B"))
        assert(!boat2.identity().containsKey("A"))
    }

    @Test
    fun `assert boat compose with more than 2 effects`() {
        val boat1: BoatNavigationEffect = Boat { compose("A") { Activity::class } }.effect()
        val boat2: BoatNavigationEffect = Boat { compose("B") { Activity::class } }.effect()
        val boat3: BoatNavigationEffect = Boat { compose("C") { Activity::class } }.effect()
        val boat4: BoatNavigationEffect = Boat { compose("D") { Activity::class } }.effect()
        val boat5: BoatNavigationEffect = boat1 + boat2 + boat3 + boat4
        assert(boat5.identity().containsKey("A"))
        assert(boat5.identity().containsKey("B"))
        assert(boat5.identity().containsKey("C"))
        assert(boat5.identity().containsKey("D"))
    }
}