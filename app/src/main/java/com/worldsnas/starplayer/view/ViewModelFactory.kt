package com.worldsnas.starplayer.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject
import javax.inject.Provider


class ViewModelFactory @Inject constructor(private val viewModels: MutableMap<Class<out ViewModel>, Provider<ViewModel>>) :
	ViewModelProvider.Factory {

	override fun <T : ViewModel> create(modelClass: Class<T>): T =
		viewModels[modelClass]?.get() as T
}