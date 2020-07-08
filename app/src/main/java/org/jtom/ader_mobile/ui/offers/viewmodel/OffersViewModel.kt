package org.jtom.ader_mobile.ui.offers.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jtom.ader_mobile.common.AderViewModel
import org.jtom.ader_mobile.service.api.ApiClient
import org.jtom.ader_mobile.ui.offers.model.OffersModel
import org.jtom.ader_mobile.ui.offers.model.OffersViewModelAction
import java.net.HttpURLConnection

class OffersViewModel : ViewModel(), AderViewModel<OffersModel, OffersViewModelAction> {

    override val model = MutableLiveData<OffersModel>()

    override fun send(action: OffersViewModelAction) {
        when (action) {
            is OffersViewModelAction.FetchOffers -> performFetchOffers()
        }
    }

    private fun performFetchOffers() = viewModelScope.launch {
        val offersResponse = ApiClient().getApiService().getOffers()

        withContext(Dispatchers.Main) {
            if (offersResponse.isSuccessful && offersResponse.code() == HttpURLConnection.HTTP_OK) {
                model.postValue(OffersModel.Success(offersResponse.body()!!))
            } else {
                val errorMessage = offersResponse.code()
                    .toString() + ": " + offersResponse.message() + ". " + offersResponse.body()
                    .toString()
                model.postValue(OffersModel.Error(errorMessage))
            }
        }
    }
}
