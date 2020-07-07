package org.jtom.ader_mobile.model

import java.util.*

data class FileDto (
    var uuid: UUID,
    var name: String,
    var userEmail: String,
    var type: String,
    var bytes: String
)
