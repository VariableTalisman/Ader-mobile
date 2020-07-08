package org.jtom.ader_mobile.ui.offers.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import kotlinx.coroutines.launch
import org.jtom.ader_mobile.common.AderViewModel
import org.jtom.ader_mobile.database.AppDatabase
import org.jtom.ader_mobile.datamodel.OfferDto
import org.jtom.ader_mobile.service.api.ApiClient
import org.jtom.ader_mobile.ui.offers.model.OffersModel
import org.jtom.ader_mobile.ui.offers.model.OffersViewModelAction
import retrofit2.Call
import retrofit2.Response
import java.util.concurrent.Callable
import java.util.concurrent.Executors

class OffersViewModel(private val context: Context) : ViewModel(), AderViewModel<OffersModel, OffersViewModelAction> {

    override val model = MutableLiveData<OffersModel>()

    override fun send(action: OffersViewModelAction) {
        when (action) {
            is OffersViewModelAction.FetchOffers -> performFetchOffers()
        }
    }

    @Suppress("DeferredResultUnused")
    private fun performFetchOffers() = viewModelScope.launch {
        val db = Room.databaseBuilder(
            context,
            AppDatabase::class.java, "OfferDto"
        ).build()

        ApiClient(context).getApiService().getOffers()
            .enqueue(object : retrofit2.Callback<List<OfferDto>> {
                override fun onFailure(call: Call<List<OfferDto>>, t: Throwable) {
                    model.postValue(OffersModel.Error(t.message!!))
                }

                override fun onResponse(
                    call: Call<List<OfferDto>>,
                    offersResponse: Response<List<OfferDto>>
                ) {
                    Executors.newSingleThreadExecutor().submit(Callable {
                        db.userDao().insertAll(offersResponse.body())
                        db.userDao().get(0, 1)
                    })
                    model.postValue(OffersModel.Success(offersResponse.body()!!))
                }
            })
    }
}
