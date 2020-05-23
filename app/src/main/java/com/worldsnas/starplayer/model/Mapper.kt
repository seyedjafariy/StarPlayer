package com.worldsnas.starplayer.model

interface Mapper<T, M> {

    fun mapToModel(type: T): M
}