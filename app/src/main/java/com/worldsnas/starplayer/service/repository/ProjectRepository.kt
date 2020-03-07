package com.worldsnas.starplayer.service.repository

import com.worldsnas.starplayer.service.model.Music

object ProjectRepository {

    suspend fun fetchMusicList(): List<Music>? {
        var data: List<Music> = ArrayList<Music>()
        //TODO fetch music list from device or server
        data = getDummyData()
        return data
    }

    private suspend fun getDummyData(): List<Music> {
        return listOf(
            Music(
                1,
                "https://www.google.com/imgres?imgurl=https%3A%2F%2Fd2uqwoe9jzxxtn.cloudfront.net%2Fimages%2Fmusic%2Fcover%2FReza-Bahram_Divaneh_1542873566.jpg&imgrefurl=https%3A%2F%2Fwww.bia2.com%2Fmusic%2F58424&tbnid=delorUWCaJIxVM&vet=12ahUKEwjGjMmMn_7nAhXMGd8KHQ8sBfYQMygAegUIARDWAQ..i&docid=QbgkPvan3RNPMM&w=640&h=640&q=reza%20bahram&ved=2ahUKEwjGjMmMn_7nAhXMGd8KHQ8sBfYQMygAegUIARDWAQ",
                "Divaneh",
                "reza bahram",
                "album1",
                "genre1",
                152365
            ),
            Music(
                2,
                "https://www.google.com/imgres?imgurl=https%3A%2F%2Fd2uqwoe9jzxxtn.cloudfront.net%2Fimages%2Fmusic%2Fcover%2FReza-Bahram_Divaneh_1542873566.jpg&imgrefurl=https%3A%2F%2Fwww.bia2.com%2Fmusic%2F58424&tbnid=delorUWCaJIxVM&vet=12ahUKEwjGjMmMn_7nAhXMGd8KHQ8sBfYQMygAegUIARDWAQ..i&docid=QbgkPvan3RNPMM&w=640&h=640&q=reza%20bahram&ved=2ahUKEwjGjMmMn_7nAhXMGd8KHQ8sBfYQMygAegUIARDWAQ",
                "Divaneh",
                "reza bahram",
                "album2",
                "genre2",
                152365
            ),
            Music(
                3,
                "https://www.google.com/imgres?imgurl=https%3A%2F%2Fd2uqwoe9jzxxtn.cloudfront.net%2Fimages%2Fmusic%2Fcover%2FReza-Bahram_Divaneh_1542873566.jpg&imgrefurl=https%3A%2F%2Fwww.bia2.com%2Fmusic%2F58424&tbnid=delorUWCaJIxVM&vet=12ahUKEwjGjMmMn_7nAhXMGd8KHQ8sBfYQMygAegUIARDWAQ..i&docid=QbgkPvan3RNPMM&w=640&h=640&q=reza%20bahram&ved=2ahUKEwjGjMmMn_7nAhXMGd8KHQ8sBfYQMygAegUIARDWAQ",
                "Divaneh",
                "reza bahram",
                "album3",
                "genre3",
                152365
            ),
            Music(
                4,
                "https://www.google.com/imgres?imgurl=https%3A%2F%2Fd2uqwoe9jzxxtn.cloudfront.net%2Fimages%2Fmusic%2Fcover%2FReza-Bahram_Divaneh_1542873566.jpg&imgrefurl=https%3A%2F%2Fwww.bia2.com%2Fmusic%2F58424&tbnid=delorUWCaJIxVM&vet=12ahUKEwjGjMmMn_7nAhXMGd8KHQ8sBfYQMygAegUIARDWAQ..i&docid=QbgkPvan3RNPMM&w=640&h=640&q=reza%20bahram&ved=2ahUKEwjGjMmMn_7nAhXMGd8KHQ8sBfYQMygAegUIARDWAQ",
                "Divaneh",
                "reza bahram",
                "album4",
                "genre4",
                152365
            ),
            Music(
                5,
                "https://www.google.com/imgres?imgurl=https%3A%2F%2Fd2uqwoe9jzxxtn.cloudfront.net%2Fimages%2Fmusic%2Fcover%2FReza-Bahram_Divaneh_1542873566.jpg&imgrefurl=https%3A%2F%2Fwww.bia2.com%2Fmusic%2F58424&tbnid=delorUWCaJIxVM&vet=12ahUKEwjGjMmMn_7nAhXMGd8KHQ8sBfYQMygAegUIARDWAQ..i&docid=QbgkPvan3RNPMM&w=640&h=640&q=reza%20bahram&ved=2ahUKEwjGjMmMn_7nAhXMGd8KHQ8sBfYQMygAegUIARDWAQ",
                "Divaneh",
                "reza bahram",
                "album5",
                "genre5",
                152365
            )
        )
    }
}