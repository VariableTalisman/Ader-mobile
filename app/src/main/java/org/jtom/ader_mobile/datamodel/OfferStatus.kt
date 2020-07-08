package org.jtom.ader_mobile.datamodel

enum class OfferStatus(val offerStatus: String) {
    OPEN("OPEN"),
    EXPIRED("EXPIRED"),
    ASSIGNED("ASSIGNED"),
    IN_PROGRESS("IN PROGRESS"),
    COMPLETED("COMPLETED"),
    CLOSED("CLOSED");
}
