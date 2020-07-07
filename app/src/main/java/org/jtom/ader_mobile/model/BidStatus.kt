package org.jtom.ader_mobile.model

enum class BidStatus(val bidStatus: String) {
    NEW("NEW"),
    ACCEPTED("ACCEPTED"),
    DECLINED("DECLINED"),
    APPROVED("APPROVED"),
    CANCELED("CANCELED");
}
