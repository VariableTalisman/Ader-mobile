package org.jtom.ader_mobile.ui.offers.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

internal class OffersViewModelFactory(private val context: Context) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass == OffersViewModel::class.java) {
            OffersViewModel(context) as T
        } else super.create(modelClass)
    }
}
