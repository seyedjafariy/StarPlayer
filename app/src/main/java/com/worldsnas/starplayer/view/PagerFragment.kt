package com.worldsnas.starplayer.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.worldsnas.starplayer.databinding.FragmentMusicsListBinding
import com.worldsnas.starplayer.databinding.FragmentPagerBinding
import kotlinx.android.synthetic.main.fragment_pager.*

class PagerFragment : Fragment() {

    private var _binding: FragmentPagerBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentPagerBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val pagerFragmentAdapter = PagerFragmentAdapter(this)
        viewpager.adapter = pagerFragmentAdapter

    }
}