package com.architecturetemplate.core.common.network

import javax.inject.Qualifier
import kotlin.annotation.AnnotationRetention.RUNTIME

@Qualifier
@Retention(RUNTIME)
annotation class Dispatcher(val architectureTemplateDispatcher: ArchitectureTemplateDispatcher)

enum class ArchitectureTemplateDispatcher {
    IO, Default, Main, Unconfined
}