package org.jtom.ader_mobile.common

import androidx.lifecycle.LiveData

interface AderViewModel<M, A> {
    val model: LiveData<M>
    fun send(action: A)
}
