package sample.onursaygili.assettracking.ui.triplist

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingComponent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import sample.onursaygili.assettracking.R
import sample.onursaygili.assettracking.binding.FragmentDataBindingComponent
import sample.onursaygili.assettracking.data.local.Trip
import sample.onursaygili.assettracking.databinding.TripListFragmentBinding
import sample.onursaygili.assettracking.di.Injectable
import javax.inject.Inject

open class TripListFragment : Fragment(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: TripListViewModel
    private lateinit var binding: TripListFragmentBinding
    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)
    private val adapter = TripAdapter(dataBindingComponent)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(TripListViewModel::class.java)

        adapter.itemClickListener = object : TripAdapter.ItemClickListener {
            override fun onItemClick(trip: Trip) {
                this@TripListFragment.onTripListItemClick(trip)
            }
        }

        binding.tripListRecyler.adapter = adapter
//        binding.tripListRecyler.layoutManager = GridLayoutManager(context, 2, GridLayoutManager.DEFAULT_SPAN_COUNT, false)

        viewModel.trips.observe(this, Observer {
            adapter.run {
                items.clear()
                items.addAll(it!!)
                notifyDataSetChanged()
            }
        })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? = DataBindingUtil.inflate<TripListFragmentBinding>(inflater, R.layout.trip_list_fragment, container, false
    ).also {
        binding = it
    }.root

    fun onTripListItemClick(trip: Trip) {
        Toast.makeText(context, trip.toString(), Toast.LENGTH_SHORT).show()
        Log.d("STATIS MAP URL", "${trip.imageUrl}")
    }

    companion object {
        fun newInstance() = TripListFragment()
    }
}