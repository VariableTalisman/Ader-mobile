package org.jtom.ader_mobile.ui.offers.model

import org.jtom.ader_mobile.datamodel.OfferDto

sealed class OffersModel {
    object Loading : OffersModel()
    data class Success(val offers: List<OfferDto>) : OffersModel()
    data class Error(val message: String) : OffersModel()
}
