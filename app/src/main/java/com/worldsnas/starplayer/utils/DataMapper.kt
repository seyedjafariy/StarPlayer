package com.worldsnas.starplayer.utils

interface DataMapper<T, L> {

    fun mapToLocal(data: T): L

    fun mapFromLocal(data: L): T
}