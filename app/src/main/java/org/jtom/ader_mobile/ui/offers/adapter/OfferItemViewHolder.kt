package org.jtom.ader_mobile.ui.offers.adapter

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.util.Base64
import android.view.View
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.offer_item.view.*
import org.jtom.ader_mobile.R
import org.jtom.ader_mobile.common.BindableRecyclerViewHolder
import org.jtom.ader_mobile.datamodel.OfferDto
import java.util.*


class OfferItemViewHolder(view: View) : RecyclerView.ViewHolder(view),
    BindableRecyclerViewHolder<OfferDto> {

    @RequiresApi(Build.VERSION_CODES.O)
    @ExperimentalStdlibApi
    override fun bind(model: OfferDto, listener: ((OfferDto) -> Unit)?) = with(itemView) {

        offerNameTextView.text = model.name
        offerAuthorTextView.text = context.getString(R.string.auther_by).plus(model.authorEmail)
        offerCompensationTextView.text = model.compensation
        offerStatusTextView.text = model.status.name

        if (model.files.isNotEmpty()) {
            val fileBytes: ByteArray = java.util.Base64.getDecoder().decode(model.files[0].bytes)
            val bmp = BitmapFactory.decodeByteArray(fileBytes, 0, fileBytes.size)
            imageView.setImageBitmap(bmp)
        } else {
            imageView.setImageResource(R.drawable.placeholder)
        }

        offerItem.setOnClickListener {
            listener?.invoke(model)
        }
    }
}
