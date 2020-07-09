package org.jtom.ader_mobile.ui.offers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.android.synthetic.main.offers_fragment.*
import org.jtom.ader_mobile.R
import org.jtom.ader_mobile.ui.offers.adapter.OfferRecyclerViewAdapter
import org.jtom.ader_mobile.ui.offers.model.OffersModel
import org.jtom.ader_mobile.ui.offers.model.OffersViewModelAction
import org.jtom.ader_mobile.ui.offers.viewmodel.OffersViewModel
import org.jtom.ader_mobile.ui.offers.viewmodel.OffersViewModelFactory
import org.jtom.ader_mobile.util.SessionManager

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
        viewModel = ViewModelProvider(this, OffersViewModelFactory(requireContext())).get(OffersViewModel::class.java)
        viewModel.model.observe(viewLifecycleOwner, Observer {
            dispatchUIUpdate(it)
        })

        addNewOfferButton.setOnClickListener {
            handleNewOfferButtonClick()
        }

        setupRecyclerView()

        viewModel.send(OffersViewModelAction.FetchOffers)
    }

    private fun handleNewOfferButtonClick() {
        if (SessionManager.getInstance(requireContext()).fetchAuthToken().isNullOrEmpty()) {
            MaterialAlertDialogBuilder(requireActivity())
                .setTitle("Error")
                .setMessage("Please login first!")
                .setNegativeButton("Ok", null)
                .show()
        } else {
            findNavController().navigate(R.id.offers_fragment_to_create_offer_fragment)
        }
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
