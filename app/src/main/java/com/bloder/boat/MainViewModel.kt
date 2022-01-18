package com.bloder.boat

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bloder.boatcore.effects.navigation.BoatNavigationEffect
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    fun navigateTo(context: Context, navigationEffect: BoatNavigationEffect, route: String) = viewModelScope.launch {
        navigationEffect.navigateTo(context, route)
    }

    private suspend fun BoatNavigationEffect.navigateTo(context: Context, route: String) {
        navigate(context, route)
    }
}