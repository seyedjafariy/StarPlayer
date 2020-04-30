package com.worldsnas.starplayer.view.online_music_list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.worldsnas.starplayer.core.BaseViewHolder
import com.worldsnas.starplayer.databinding.ItemOnlineMusicBinding
import com.worldsnas.starplayer.model.LocalMusic
import com.worldsnas.starplayer.model.Music

class OnlineMusicListAdapter(private val onItemClick: (LocalMusic) -> Unit) :
    ListAdapter<Music, OnlineMusicListItemViewHolder>(DiffCallback) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): OnlineMusicListItemViewHolder {
        val onlineMusicViewBinding =
            ItemOnlineMusicBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OnlineMusicListItemViewHolder(onlineMusicViewBinding, onItemClick)
    }

    override fun onBindViewHolder(holder: OnlineMusicListItemViewHolder, position: Int) {

        holder.onBind(getItem(position))
    }


}

class OnlineMusicListItemViewHolder(
    private val mBinding: ItemOnlineMusicBinding,
    private val onItemClick: ((LocalMusic) -> Unit)
) : BaseViewHolder<Music>(mBinding.root) {
    override fun onBind(obj: Music) {
        mBinding.txtMusicArtist.text = obj.artist
        mBinding.txtMusicTitle.text = obj.title

        sendToPlayer(obj)
    }


    private fun sendToPlayer(obj: Music) {
        mBinding.root.setOnClickListener {

            val localMusic = LocalMusic(
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

object DiffCallback : DiffUtil.ItemCallback<Music>() {
    override fun areItemsTheSame(oldItem: Music, newItem: Music): Boolean {

        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Music, newItem: Music): Boolean {
        return oldItem == newItem
    }

}