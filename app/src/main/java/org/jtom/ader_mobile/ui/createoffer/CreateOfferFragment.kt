package org.jtom.ader_mobile.ui.createoffer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import org.jtom.ader_mobile.ui.createoffer.viewmodel.CreateOfferViewModel
import org.jtom.ader_mobile.R

class CreateOfferFragment : Fragment() {

    private lateinit var viewModel: CreateOfferViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.create_offer_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(CreateOfferViewModel::class.java)
    }
}
