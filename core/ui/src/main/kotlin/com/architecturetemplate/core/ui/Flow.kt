package com.architecturetemplate.core.ui

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

/**
 * Standard 5-second timeout to keep upstream alive during configuration changes.
 */
const val DEFAULT_SUBSCRIBE_TIMEOUT_MS = 5_000L

/**
 * Converts a cold [Flow] into a hot [StateFlow] optimized for the Android UI layer.
 * Uses [SharingStarted.WhileSubscribed] strategy by default to save resources in the background.
 */
fun <T> Flow<T>.stateInUi(
    scope: CoroutineScope,
    initialValue: T,
    stopTimeoutMillis: Long = DEFAULT_SUBSCRIBE_TIMEOUT_MS,
): StateFlow<T> = stateIn(
    scope = scope,
    started = SharingStarted.WhileSubscribed(stopTimeoutMillis = stopTimeoutMillis),
    initialValue = initialValue
)
