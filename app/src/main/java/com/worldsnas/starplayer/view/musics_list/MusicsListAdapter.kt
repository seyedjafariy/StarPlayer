package com.worldsnas.starplayer.view.musics_list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.worldsnas.starplayer.R
import com.worldsnas.starplayer.core.BaseViewHolder
import com.worldsnas.starplayer.databinding.ItemMusicBinding
import com.worldsnas.starplayer.model.Music
import com.worldsnas.starplayer.model.MusicRepoModel
import com.worldsnas.starplayer.model.persistent.FavoriteMusic

class MusicsListAdapter(
    private var onItemClickListener: (Music) -> Unit,
    private var onFavoriteClickListener: (FavoriteMusic) -> Unit
) :
    ListAdapter<MusicRepoModel, MusicListItemViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicListItemViewHolder {
        val musicViewBinding: ItemMusicBinding =
            ItemMusicBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MusicListItemViewHolder(
            musicViewBinding,
            onItemClickListener,
            onFavoriteClickListener
        )
    }

    override fun onBindViewHolder(holder: MusicListItemViewHolder, position: Int) {
        val musicItem = getItem(position)
        holder.onBind(musicItem)
    }
}

class MusicListItemViewHolder(
    private val mBinding: ItemMusicBinding,
    private val onItemClickListener: (Music) -> Unit,
    private val onFavoriteClickListener: (FavoriteMusic) -> Unit
) :
    BaseViewHolder<MusicRepoModel>(mBinding.root) {

    override fun onBind(obj: MusicRepoModel) {
        mBinding.txtMusicTitle.text = obj.title
        mBinding.txtArtist.text = obj.artist
        favoriteStateHandler(obj)
        sendToPlay(obj)
    }

    private fun favoriteStateHandler(obj: MusicRepoModel) {
        if (obj.isFavorite) mBinding.imgMusicFavorite.setImageResource(R.color.colorAccent)

        mBinding.imgMusicFavorite.setOnClickListener {

            val favoriteMusic = FavoriteMusic(
                obj.id,
                obj.title,
                obj.artist,
                obj.album,
                obj.genre,
                obj.address,
                obj.isFavorite.not()
            )
            onFavoriteClickListener(favoriteMusic)
        }
    }

    private fun sendToPlay(obj: MusicRepoModel) {
        mBinding.root.setOnClickListener {
            val localMusic = Music(
                obj.id,
                obj.title,
                obj.artist,
                obj.album,
                obj.genre,
                obj.address,
                obj.isFavorite
            )

            onItemClickListener(localMusic)
        }
    }
}

object DiffCallback : DiffUtil.ItemCallback<MusicRepoModel>() {
    override fun areItemsTheSame(oldItem: MusicRepoModel, newItem: MusicRepoModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: MusicRepoModel, newItem: MusicRepoModel): Boolean {
        return oldItem == newItem
    }
}