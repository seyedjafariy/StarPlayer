package com.worldsnas.starplayer.di.scope

import javax.inject.Scope
import kotlin.reflect.KClass

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class ScopeIn(val value: KClass<*>)