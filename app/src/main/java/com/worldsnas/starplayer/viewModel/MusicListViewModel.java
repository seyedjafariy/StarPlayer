package com.worldsnas.starplayer.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.worldsnas.starplayer.service.model.Music;
import com.worldsnas.starplayer.service.repository.ProjectRepository;

import java.util.List;

public class MusicListViewModel extends AndroidViewModel {

    private final LiveData<List<Music>> musicListObservable;

    public MusicListViewModel(@NonNull Application application) {
        super(application);

        musicListObservable = ProjectRepository.getInstance().getMusicList();
    }

    public LiveData<List<Music>> getMusicListObservable() {
        return musicListObservable;
    }
}
