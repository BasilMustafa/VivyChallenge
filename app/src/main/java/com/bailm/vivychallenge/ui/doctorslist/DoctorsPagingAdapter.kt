package com.bailm.vivychallenge.ui.doctorslist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bailm.vivychallenge.R
import com.bailm.vivychallenge.data.NetworkState
import com.bailm.vivychallenge.data.api.model.search.Doctor
import com.bailm.vivychallenge.databinding.ItemDoctorsListBinding
import com.bailm.vivychallenge.util.ImageLoader

class DoctorsPagingAdapter : PagedListAdapter<Doctor, RecyclerView.ViewHolder>(diffCallBack) {
    var networkState: NetworkState? = null
    var onDoctorClickedListener: DoctorClickedListener? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            R.layout.item_doctors_list -> DoctorViewHolder.create(parent)
            R.layout.item_netwok_loading -> LoadingViewHolder.create(parent)
            else -> throw IllegalArgumentException("Unknown Type")
        }
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
//        getItem(position)?.let {
//            holder.bind(it)
//            onDoctorClickedListener?.let { listener ->
//                holder.onDoctorClickedListener = listener
//            }
//        }
        when (getItemViewType(position)) {
            R.layout.item_doctors_list -> {
                (holder as DoctorViewHolder).bind(getItem(position)!!)
                if (onDoctorClickedListener != null) {
                    holder.onDoctorClickedListener = onDoctorClickedListener
                }

            }
            R.layout.item_netwok_loading -> {
            }
        }
    }


    override fun getItemViewType(position: Int): Int {
        return if (hasLoadingRow() && position == itemCount - 1) {
            R.layout.item_netwok_loading
        } else {
            R.layout.item_doctors_list
        }
    }


    override fun getItemCount(): Int = super.getItemCount() + if (hasLoadingRow()) 1 else 0

    private fun hasLoadingRow(): Boolean = networkState != null && networkState == NetworkState.LOADING

    class DoctorViewHolder constructor(var binding: ItemDoctorsListBinding) : RecyclerView.ViewHolder(binding.root) {
        var onDoctorClickedListener: DoctorClickedListener? = null

        companion object {
            fun create(parent: ViewGroup): DoctorViewHolder {
                val layoutInflator = LayoutInflater.from(parent.context)
                val itemDoctorsListBinding: ItemDoctorsListBinding =
                    DataBindingUtil.inflate(layoutInflator, R.layout.item_doctors_list, parent, false)
                return DoctorViewHolder(itemDoctorsListBinding)
            }
        }

        fun bind(doctor: Doctor) {
            binding.doctor = doctor
            binding.root.setOnClickListener {
                onDoctorClickedListener?.onDoctorClicked(doctor)
            }
        }
    }

    class LoadingViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        companion object {
            fun create(parent: ViewGroup): LoadingViewHolder {
                val layoutInflator = LayoutInflater.from(parent.context)
                return LoadingViewHolder(layoutInflator.inflate(R.layout.item_netwok_loading, parent, false))
            }
        }
    }

    override fun onViewRecycled(holder: RecyclerView.ViewHolder) {
        super.onViewRecycled(holder)
        if (holder is DoctorViewHolder) {
            ImageLoader.clear(holder.binding.avatar)
            holder.binding.doctorName.text = null
            holder.binding.address.text = null
        }
    }

    companion object {

        private val diffCallBack = object : DiffUtil.ItemCallback<Doctor>() {
            override fun areItemsTheSame(oldItem: Doctor, newItem: Doctor): Boolean = oldItem.id == newItem.id
            override fun areContentsTheSame(oldItem: Doctor, newItem: Doctor): Boolean = oldItem.id == newItem.id
        }
    }

    interface DoctorClickedListener {
        fun onDoctorClicked(doctor: Doctor)
        fun onDialDoctorClicked(phoneNumber:String)
    }


    fun updateNetwokState(newNetworkState: NetworkState) {
        val oldNetworkState = networkState;
        val hadLoadingRow = hasLoadingRow()
        this.networkState = newNetworkState;
        val hasLoadingRow = hasLoadingRow();
        if (hadLoadingRow != hasLoadingRow) {
            if (hadLoadingRow) {
                notifyItemRemoved(super.getItemCount())
            } else {
                notifyItemInserted(super.getItemCount())
            }
        } else if (hasLoadingRow && oldNetworkState != newNetworkState) {
            notifyItemChanged(itemCount - 1)
        }
    }
}