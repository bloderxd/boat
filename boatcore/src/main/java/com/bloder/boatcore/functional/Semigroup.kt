package com.bloder.boatcore.functional

fun interface Semigroup<A> {
    fun A.combine(b: A): A
}