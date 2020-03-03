package com.worldsnas.starplayer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.worldsnas.starplayer.databinding.ActivityMainBinding
import com.worldsnas.starplayer.view.musics_list.MusicsListFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        replaceFragment(R.id.framelayout, MusicsListFragment())
    }

    fun replaceFragment(@IdRes containerId: Int, fragment: Fragment, tag: String? = null) {
        val ft: FragmentTransaction = supportFragmentManager.beginTransaction()
        if (tag != null)
            ft.replace(containerId, fragment, tag)
        else
            ft.replace(containerId, fragment)
        ft.commit()
    }
}
