package com.bloder.boatcore.effects.predictable.contracts.dsl

import com.bloder.boatcore.effects.predictable.contracts.BoatRouteContract

interface RouteContractContext {

    fun compose(id: String): BoatRouteContract

    companion object : RouteContractContext {
        private val mutableList: MutableList<String> = mutableListOf()

        override fun compose(id: String): BoatRouteContract {
            return mutableList
        }
    }
}