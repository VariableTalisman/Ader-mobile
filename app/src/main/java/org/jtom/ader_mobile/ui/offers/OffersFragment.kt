package org.jtom.ader_mobile.ui.offers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.android.synthetic.main.offers_fragment.*
import org.jtom.ader_mobile.R
import org.jtom.ader_mobile.ui.offers.adapter.OfferRecyclerViewAdapter
import org.jtom.ader_mobile.ui.offers.model.OffersModel
import org.jtom.ader_mobile.ui.offers.model.OffersViewModelAction
import org.jtom.ader_mobile.ui.offers.viewmodel.OffersViewModel

class OffersFragment : Fragment() {

    private lateinit var viewModel: OffersViewModel
    private lateinit var recyclerViewAdapter: OfferRecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.offers_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(OffersViewModel::class.java)
        viewModel.model.observe(viewLifecycleOwner, Observer {
            dispatchUIUpdate(it)
        })

        setupRecyclerView()

        viewModel.send(OffersViewModelAction.FetchOffers)
    }

    private fun setupRecyclerView() {
        recyclerViewAdapter = OfferRecyclerViewAdapter {
            // TODO: Navigate to offer view
        }

        recyclerView.apply {
            adapter = recyclerViewAdapter
            itemAnimator = DefaultItemAnimator()
        }
    }

    private fun dispatchUIUpdate(model: OffersModel) {
        offersProgressBar.visibility = View.GONE

        when (model) {
            is OffersModel.Loading -> {
                offersProgressBar.visibility = View.VISIBLE
            }

            is OffersModel.Success -> {
                recyclerViewAdapter.items = model.offers
            }

            is OffersModel.Error -> {
                MaterialAlertDialogBuilder(requireActivity())
                    .setTitle("Error")
                    .setMessage(model.message)
                    .setNegativeButton("Ok", null)
                    .show()
            }
        }
    }
}
