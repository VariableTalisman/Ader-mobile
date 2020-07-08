package org.jtom.ader_mobile.datamodel

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "offers")
class OfferDto {
    @PrimaryKey var id: Int? = null
    var name: String? = null
    var description: String? = null
    var expireDate: String? = null
    var authorName: String? = null
    var authorEmail: String? = null
    @Ignore var assigneeNames: List<String>? = null
    @Ignore var categories: List<CategoryDto>? = null
    @Ignore var files: List<FileDto>? = null
    @Ignore var bids: List<BidDto>? = null
    @Ignore var advertisementFormats: List<AdvertisementFormatDto>? = null
    var freeProductSample: Boolean? = null
    var advertisementReview: Boolean? = null
    var compensation: String? = null
    var offerStatus: OfferStatus? = null
    var status: Status? = null
}
