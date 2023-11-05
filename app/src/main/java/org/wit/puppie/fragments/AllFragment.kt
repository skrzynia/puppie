package org.wit.puppie.fragments

import android.app.Activity
import android.app.Application
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity

import timber.log.Timber.Forest.i
import org.wit.pappie.databinding.CardFragmentGalleryBinding
import org.wit.placemark.main.MainApp
import org.wit.puppie.activities.PlacemarkDetails
import org.wit.puppie.adapters.PlacemarkAdapter
import org.wit.puppie.adapters.PlacemarkListener
import org.wit.puppie.models.PlacemarkJSONStore
import org.wit.puppie.models.PlacemarkModel


class AllFragment : Fragment(), PlacemarkListener {

    private lateinit var binding:CardFragmentGalleryBinding
    lateinit var app: MainApp
    private var position = 0

    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = CardFragmentGalleryBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        app = requireActivity().application as MainApp
        val pos = arguments?.getInt(POSITION_ARG)
        val viewPager = binding?.rvid
        pos?.let {
            when(pos){
                0 -> viewPager?.adapter = setAdapter(app.placemarks.findAll())
                1 -> viewPager?.adapter = setAdapter(app.placemarks.getPopular())
                2 -> viewPager?.adapter = setAdapter(app.placemarks.getRecomended())
                else -> {}
            }
        }
    }

    override fun onPlacemarkClick(placemark: PlacemarkModel, position: Int) {
        val launcherIntent = Intent(context, PlacemarkDetails::class.java)
        launcherIntent.putExtra("placemark_details", placemark)
        this.position = position
        getClickResult.launch(launcherIntent)
    }
    fun setAdapter(list: List<PlacemarkModel>): PlacemarkAdapter
    {
        val placemarkAdapter = PlacemarkAdapter(list, this)
        i("${placemarkAdapter.itemCount}")
        return placemarkAdapter
    }

    private val getClickResult =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            if (it.resultCode == Activity.RESULT_OK) {
                (binding.rvid.adapter)?.
                notifyItemRangeChanged(0,3)
            }
            else // Deleting
                if (it.resultCode == 99)
                    (binding.rvid.adapter)?.notifyItemRemoved(position)
        }

    companion object{
        var POSITION_ARG = "position_arg"
        @JvmStatic
        fun newInstance(position: Int) = AllFragment().apply {
            arguments = Bundle().apply {
                putInt(POSITION_ARG,position)
            }
        }
    }




}