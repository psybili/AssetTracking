package sample.onursaygili.assettracking.ui.triplist

import android.databinding.DataBindingComponent
import android.databinding.DataBindingUtil.inflate
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import sample.onursaygili.assettracking.R
import sample.onursaygili.assettracking.data.local.Trip
import sample.onursaygili.assettracking.databinding.TripListItemBinding

class TripAdapter(private val dataBindingComponent: DataBindingComponent) : RecyclerView.Adapter<TripAdapter.ViewHolder>() {
    val items = ArrayList<Trip>()

    lateinit var itemClickListener: ItemClickListener

    interface ItemClickListener {
        fun onItemClick(trip: Trip)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(inflate(
                LayoutInflater.from(parent.context),
                R.layout.trip_list_item,
                parent,
                false,
                dataBindingComponent
        ))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = items[position]
        holder.bind(currentItem) {
            itemClickListener.onItemClick(it)
        }

    }

    override fun getItemCount() = items.size

    inner class ViewHolder(private val binding: TripListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(currentItem: Trip, listener: (Trip) -> Unit) = with(binding) {
            binding.setVariable(com.android.databinding.library.baseAdapters.BR.trip, currentItem)
            binding.executePendingBindings()
            binding.root.setOnClickListener { listener(currentItem) }

        }
    }
}