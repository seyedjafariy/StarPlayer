package com.worldsnas.starplayer.utils

import com.worldsnas.starplayer.api.MusicResponse
import com.worldsnas.starplayer.model.MusicRepoModel

/**
 * This class is used for converting network music response to local one
 */
object NetworkMusicMapper : DataMapper<MusicResponse, MusicRepoModel> {


    override fun mapToLocal(data: MusicResponse): MusicRepoModel {
        return MusicRepoModel(
            data.id.toInt(),
            data.name,
            data.artist,
            "album",
            "genre",
            data.musicLink
        )
    }

    override fun mapFromLocal(data: MusicRepoModel): MusicResponse {
        return MusicResponse(
            data.id.toLong(),
            null,
            data.address,
            data.artist,
            data.genre,
            data.title
        )
    }

    fun mapToLocalList(list: List<MusicResponse>): List<MusicRepoModel> {
        return list.map {
            mapToLocal(it)
        }
    }
}