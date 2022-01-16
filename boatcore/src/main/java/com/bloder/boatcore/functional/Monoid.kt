package com.bloder.boatcore.functional

interface Monoid<A> : Semigroup<A> {
    fun identity(): A
}