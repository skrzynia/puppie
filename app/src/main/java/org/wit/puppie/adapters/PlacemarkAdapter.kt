package org.wit.puppie.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.wit.pappie.databinding.CardPlacemark2Binding
import org.wit.pappie.databinding.CardPlacemarkBinding
import org.wit.puppie.activities.ListAllPlacemarks
import org.wit.puppie.models.PlacemarkModel
import timber.log.Timber
import timber.log.Timber.Forest.i

interface PlacemarkListener {
    fun onPlacemarkClick(placemark: PlacemarkModel, position: Int)
}

class PlacemarkAdapter(private var placemarks: List<PlacemarkModel>) :
    RecyclerView.Adapter<PlacemarkAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {

        val bind = CardPlacemark2Binding.inflate(LayoutInflater.from(parent.context), parent, false)

        return MainHolder(bind)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val placemark = placemarks[holder.adapterPosition]
        holder.bind(placemark)
    }

    override fun getItemCount(): Int = placemarks.size

    class MainHolder(private val bind: CardPlacemark2Binding): RecyclerView.ViewHolder(bind.root){
        fun bind(placemark: PlacemarkModel){
            bind.title.text = placemark.title
            bind.subhead.text = placemark.description
        }
    }
}