package com.worldsnas.starplayer.view.musics_list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.worldsnas.starplayer.core.BaseViewHolder
import com.worldsnas.starplayer.databinding.ItemMusicBinding
import com.worldsnas.starplayer.model.Music
import com.worldsnas.starplayer.model.LocalMusic

class MusicsListAdapter(private var onItemClick: (Music) -> Unit) :
    ListAdapter<LocalMusic, MusicListItemViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicListItemViewHolder {
        val musicViewBinding: ItemMusicBinding =
            ItemMusicBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MusicListItemViewHolder(musicViewBinding, onItemClick)
    }

    override fun onBindViewHolder(holder: MusicListItemViewHolder, position: Int) {
        val musicItem = getItem(position)
        holder.onBind(musicItem)
    }
}

class MusicListItemViewHolder(
    private val mBinding: ItemMusicBinding,
    private val onItemClick: ((Music) -> Unit)
) :
    BaseViewHolder<LocalMusic>(mBinding.root) {

    override fun onBind(obj: LocalMusic) {
        mBinding.txtMusicTitle.text = obj.title
        mBinding.txtArtist.text = obj.artist

        sendToPlay(obj)
    }

    private fun sendToPlay(obj: LocalMusic) {
        mBinding.root.setOnClickListener {
            val localMusic = Music(
                obj.id,
                obj.title,
                obj.artist,
                obj.album,
                obj.genre,
                obj.address
            )

            onItemClick(localMusic)
        }
    }
}

object DiffCallback : DiffUtil.ItemCallback<LocalMusic>() {
    override fun areItemsTheSame(oldItem: LocalMusic, newItem: LocalMusic): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: LocalMusic, newItem: LocalMusic): Boolean {
        return oldItem == newItem
    }
}