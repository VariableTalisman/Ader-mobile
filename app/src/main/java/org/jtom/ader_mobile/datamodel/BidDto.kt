package org.jtom.ader_mobile.datamodel

data class BidDto (
    var id: Long,
    var offerId: Long,
    var userEmail: String,
    var persona: PersonaDto,
    var acceptInitialRequirements: Boolean,
    var freeProductSample: Boolean,
    var compensation: String,
    var bidStatus: BidStatus
)
