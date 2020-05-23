package com.worldsnas.starplayer.model

import com.worldsnas.starplayer.api.MusicResponse
import javax.inject.Inject

class MusicMapper @Inject constructor() : Mapper<MusicResponse, MusicRepoModel> {
    override fun mapToModel(type: MusicResponse): MusicRepoModel {
        return MusicRepoModel(
            type.id.toInt(),
            type.name,
            type.artist,
            "album",
            "genre",
            type.musicLink,
            false
        )
    }

}