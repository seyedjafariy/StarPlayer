package com.worldsnas.starplayer.service.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.worldsnas.starplayer.service.model.Music;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProjectRepository {

    private static ProjectRepository instance;

    public static ProjectRepository getInstance() {
        if (instance == null) {
            instance = new ProjectRepository();
        }
        return instance;
    }

    public LiveData<List<Music>> getMusicList() {
        final MutableLiveData<List<Music>> data = new MutableLiveData<>();

        //TODO fetch music list from device or server
        //create dummy data
        List<Music> dummyData = Arrays.asList(
                new Music(1, "https://www.google.com/imgres?imgurl=https%3A%2F%2Fd2uqwoe9jzxxtn.cloudfront.net%2Fimages%2Fmusic%2Fcover%2FReza-Bahram_Divaneh_1542873566.jpg&imgrefurl=https%3A%2F%2Fwww.bia2.com%2Fmusic%2F58424&tbnid=delorUWCaJIxVM&vet=12ahUKEwjGjMmMn_7nAhXMGd8KHQ8sBfYQMygAegUIARDWAQ..i&docid=QbgkPvan3RNPMM&w=640&h=640&q=reza%20bahram&ved=2ahUKEwjGjMmMn_7nAhXMGd8KHQ8sBfYQMygAegUIARDWAQ", "Divaneh", "reza bahram", "album1", "genre1"),
                new Music(2, "https://www.google.com/imgres?imgurl=https%3A%2F%2Fd2uqwoe9jzxxtn.cloudfront.net%2Fimages%2Fmusic%2Fcover%2FReza-Bahram_Divaneh_1542873566.jpg&imgrefurl=https%3A%2F%2Fwww.bia2.com%2Fmusic%2F58424&tbnid=delorUWCaJIxVM&vet=12ahUKEwjGjMmMn_7nAhXMGd8KHQ8sBfYQMygAegUIARDWAQ..i&docid=QbgkPvan3RNPMM&w=640&h=640&q=reza%20bahram&ved=2ahUKEwjGjMmMn_7nAhXMGd8KHQ8sBfYQMygAegUIARDWAQ", "Divaneh", "reza bahram", "album2", "genre2"),
                new Music(3, "https://www.google.com/imgres?imgurl=https%3A%2F%2Fd2uqwoe9jzxxtn.cloudfront.net%2Fimages%2Fmusic%2Fcover%2FReza-Bahram_Divaneh_1542873566.jpg&imgrefurl=https%3A%2F%2Fwww.bia2.com%2Fmusic%2F58424&tbnid=delorUWCaJIxVM&vet=12ahUKEwjGjMmMn_7nAhXMGd8KHQ8sBfYQMygAegUIARDWAQ..i&docid=QbgkPvan3RNPMM&w=640&h=640&q=reza%20bahram&ved=2ahUKEwjGjMmMn_7nAhXMGd8KHQ8sBfYQMygAegUIARDWAQ", "Divaneh", "reza bahram", "album3", "genre3"),
                new Music(4, "https://www.google.com/imgres?imgurl=https%3A%2F%2Fd2uqwoe9jzxxtn.cloudfront.net%2Fimages%2Fmusic%2Fcover%2FReza-Bahram_Divaneh_1542873566.jpg&imgrefurl=https%3A%2F%2Fwww.bia2.com%2Fmusic%2F58424&tbnid=delorUWCaJIxVM&vet=12ahUKEwjGjMmMn_7nAhXMGd8KHQ8sBfYQMygAegUIARDWAQ..i&docid=QbgkPvan3RNPMM&w=640&h=640&q=reza%20bahram&ved=2ahUKEwjGjMmMn_7nAhXMGd8KHQ8sBfYQMygAegUIARDWAQ", "Divaneh", "reza bahram", "album4", "genre4"),
                new Music(5, "https://www.google.com/imgres?imgurl=https%3A%2F%2Fd2uqwoe9jzxxtn.cloudfront.net%2Fimages%2Fmusic%2Fcover%2FReza-Bahram_Divaneh_1542873566.jpg&imgrefurl=https%3A%2F%2Fwww.bia2.com%2Fmusic%2F58424&tbnid=delorUWCaJIxVM&vet=12ahUKEwjGjMmMn_7nAhXMGd8KHQ8sBfYQMygAegUIARDWAQ..i&docid=QbgkPvan3RNPMM&w=640&h=640&q=reza%20bahram&ved=2ahUKEwjGjMmMn_7nAhXMGd8KHQ8sBfYQMygAegUIARDWAQ", "Divaneh", "reza bahram", "album5", "genre5"));

        data.setValue(dummyData);

        return data;
    }
}
