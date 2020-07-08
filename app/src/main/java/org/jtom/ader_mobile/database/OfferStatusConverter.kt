package org.jtom.ader_mobile.database

import androidx.room.TypeConverter
import org.jtom.ader_mobile.datamodel.OfferStatus

class OfferStatusConverter {

    @TypeConverter
    fun fromOfferStatusToString(status: OfferStatus): String {
        return status.offerStatus
    }

    @TypeConverter
    fun fromStringToOfferStatus(status: String): OfferStatus {
        return OfferStatus.valueOf(status)
    }
}
