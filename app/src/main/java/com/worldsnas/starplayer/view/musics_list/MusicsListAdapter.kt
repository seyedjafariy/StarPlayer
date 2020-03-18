package com.worldsnas.starplayer.view.musics_list

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.worldsnas.starplayer.core.BaseViewHolder
import com.worldsnas.starplayer.databinding.ItemMusicBinding
import com.worldsnas.starplayer.model.Music

class MusicsListAdapter(private val musics: ArrayList<Music>) :
    RecyclerView.Adapter<BaseViewHolder<Music>>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicListItemViewHolder {
        val musicViewBinding: ItemMusicBinding =
            ItemMusicBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MusicListItemViewHolder(musicViewBinding)
    }

    override fun getItemCount(): Int {
        return musics.size
    }

    override fun onBindViewHolder(holder: BaseViewHolder<Music>, position: Int) {
        holder.onBind(musics[position])
    }
}

class MusicListItemViewHolder(private val mBinding: ItemMusicBinding) :
    BaseViewHolder<Music>(mBinding.root) {

    override fun onBind(obj: Music) {
        mBinding.txtMusicTitle.text = obj.title
        mBinding.txtArtist.text = obj.artist
    }
}