package com.worldsnas.starplayer

import com.worldsnas.starplayer.api.MusicResponse

fun fakeGetMusicsApi(): List<MusicResponse> {
    val musics = ArrayList<MusicResponse>()
    for (i: Int in 1 until 11) {
        musics.add(MusicResponse(i.toLong(), i.toLong(),
                "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-${i}.mp3",
                "Artist$i", "", "Arts$i"
        ))
    }
    return musics
}

