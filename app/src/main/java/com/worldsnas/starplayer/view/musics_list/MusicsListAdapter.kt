package com.worldsnas.starplayer.view.musics_list

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.worldsnas.starplayer.core.BaseViewHolder
import com.worldsnas.starplayer.databinding.ItemMusicBinding
import com.worldsnas.starplayer.model.Music

class MusicsListAdapter(private val context: Context, private val musics: ArrayList<Music>) :
        RecyclerView.Adapter<BaseViewHolder>() {

    inner class MusicListItemViewHolder(private val mBinding: ItemMusicBinding) : BaseViewHolder(mBinding.root), MusicItemViewModel.Listener {
        private var mMusicViewModel: MusicItemViewModel? = null
        override fun onBind(position: Int) {
            mMusicViewModel = MusicItemViewModel(musics[position], this)
            mBinding.viewModel = mMusicViewModel
            mBinding.executePendingBindings()
        }

        override fun onItemClick() {

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicListItemViewHolder {
        val musicViewBinding: ItemMusicBinding =
                ItemMusicBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent, false
                )
        return MusicListItemViewHolder(musicViewBinding)
    }

    override fun getItemCount(): Int {
        return musics.size
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.onBind(position)
    }
}