package com.worldsnas.starplayer.view.musics_list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.worldsnas.starplayer.R
import com.worldsnas.starplayer.core.BaseViewHolder
import com.worldsnas.starplayer.databinding.ItemMusicBinding
import com.worldsnas.starplayer.model.Music

class MusicsListAdapter :
    ListAdapter<Music, MusicListItemViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicListItemViewHolder {
        val musicViewBinding: ItemMusicBinding =
            ItemMusicBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MusicListItemViewHolder(musicViewBinding)
    }

    override fun onBindViewHolder(holder: MusicListItemViewHolder, position: Int) {
        val musicItem = getItem(position)
        holder.onBind(musicItem)
    }
}

class MusicListItemViewHolder(private val mBinding: ItemMusicBinding) :
    BaseViewHolder<Music>(mBinding.root) {

    override fun onBind(obj: Music) {
        mBinding.txtMusicTitle.text = obj.title
        mBinding.txtArtist.text = obj.artist

        sendToPlay(obj)
    }

    private fun sendToPlay(obj: Music) {
        mBinding.root.setOnClickListener {
            val bundle = bundleOf(
                "id" to obj.id,
                "title" to obj.title,
                "artist" to obj.artist,
                "album" to obj.album,
                "genre" to obj.genre,
                "address" to obj.address
            )
            itemView.findNavController()
                .navigate(R.id.action_musicsListFragment_to_playerFragment, bundle)
        }
    }

}

object DiffCallback : DiffUtil.ItemCallback<Music>() {
    override fun areItemsTheSame(oldItem: Music, newItem: Music): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Music, newItem: Music): Boolean {
        return oldItem == newItem
    }
}