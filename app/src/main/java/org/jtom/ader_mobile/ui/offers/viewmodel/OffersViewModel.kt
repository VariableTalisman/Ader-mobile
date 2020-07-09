package org.jtom.ader_mobile.ui.offers.viewmodel

import android.content.Context
import androidx.lifecycle.*
import kotlinx.coroutines.launch
import org.jtom.ader_mobile.common.AderViewModel
import org.jtom.ader_mobile.datamodel.OfferDto
import org.jtom.ader_mobile.service.api.ApiClient
import org.jtom.ader_mobile.ui.offers.model.OffersModel
import org.jtom.ader_mobile.ui.offers.model.OffersViewModelAction
import org.jtom.ader_mobile.util.SessionManager
import retrofit2.Call
import retrofit2.Response

class OffersViewModel(private val context: Context) : ViewModel(), AderViewModel<OffersModel, OffersViewModelAction> {

    override val model = MutableLiveData<OffersModel>()
    private val mOffers = MediatorLiveData<OffersModel.Success>()
    val offers : LiveData<OffersModel.Success> = mOffers

    override fun send(action: OffersViewModelAction) {
        when (action) {
            is OffersViewModelAction.FetchOffers -> performFetchOffers()
        }
    }

    private fun performFetchOffers() = viewModelScope.launch {
        val token = SessionManager.getInstance(context).fetchAuthToken()

        if (token.isNullOrEmpty()) {
            model.postValue(OffersModel.Error("Please login first!"))
        } else {
            ApiClient(context).getApiService().getOffers(token)
                .enqueue(object : retrofit2.Callback<List<OfferDto>> {
                    override fun onFailure(call: Call<List<OfferDto>>, t: Throwable) {
                        model.postValue(OffersModel.Error(t.message!!))
                    }

                    override fun onResponse(
                        call: Call<List<OfferDto>>,
                        offersResponse: Response<List<OfferDto>>
                    ) {
//                    Executors.newSingleThreadExecutor().submit(Callable {
//                    val db = Room.databaseBuilder(
//                        context,
//                        AppDatabase::class.java, "OfferDto"
//                    ).build()

//                        db.userDao().insertAll(offersResponse.body()!!)
//                        viewModelScope.launch {
//                            mOffers.postValue(OffersModel.Success(db.userDao().get(10, 10)))
//                        }
//                    })
                        model.postValue(OffersModel.Success(offersResponse.body()!!))
                    }
                })
        }
    }
}
