package org.jtom.ader_mobile.common

interface BindableRecyclerViewHolder<V> {
    fun bind(model: V, listener: ((V) -> Unit)? = null)
}
