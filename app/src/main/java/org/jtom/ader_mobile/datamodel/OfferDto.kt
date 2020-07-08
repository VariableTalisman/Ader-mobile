package org.jtom.ader_mobile.datamodel

data class OfferDto (
    var id: Long,
    var name: String,
    var description: String,
    var expireDate: String,
    var authorName: String,
    var authorEmail: String,
    var assigneeNames: List<String>,
    var categories: List<CategoryDto>,
    var files: List<FileDto>,
    var bids: List<BidDto>,
    var advertisementFormats: List<AdvertisementFormatDto>,
    var freeProductSample: Boolean,
    var advertisementReview: Boolean,
    var compensation: String,
    var offerStatus: OfferStatus,
    var status: Status
)
