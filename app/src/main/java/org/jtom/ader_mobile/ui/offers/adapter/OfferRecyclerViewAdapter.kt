package org.jtom.ader_mobile.ui.offers.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.jtom.ader_mobile.R
import org.jtom.ader_mobile.common.inflate
import org.jtom.ader_mobile.datamodel.OfferDto

class OfferRecyclerViewAdapter(offers: List<OfferDto> = emptyList(),
                               private val listener: (OfferDto) -> Unit):
    RecyclerView.Adapter<OfferItemViewHolder>() {

    var items: List<OfferDto> = offers
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): OfferItemViewHolder {
        return OfferItemViewHolder(parent.inflate(R.layout.offer_item))
    }

    @ExperimentalStdlibApi
    override fun onBindViewHolder(holder: OfferItemViewHolder, position: Int) {
        holder.bind(items[position], listener)
    }

    override fun getItemCount(): Int = items.size
}
