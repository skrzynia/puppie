package org.wit.puppie.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.Placeholder
import timber.log.Timber
import timber.log.Timber.Forest.i
import org.wit.pappie.databinding.ActivityListAllPlacemarksBinding
import org.wit.pappie.databinding.CardFragmentGalleryBinding
import org.wit.puppie.adapters.PlacemarkAdapter
import org.wit.puppie.helpers.Placeholders
import org.wit.puppie.models.PlacemarkModel


class AllFragment : Fragment() {

    private lateinit var binding:CardFragmentGalleryBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = CardFragmentGalleryBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val pos = arguments?.getInt(POSITION_ARG)
        val viewPager = binding?.rvid
        pos?.let {
            when(pos){
                0 -> viewPager?.adapter = setAdapter()
                1 -> viewPager?.adapter = setAdapter()
                2 -> viewPager?.adapter = setAdapter()
                else -> {}
            }
        }
    }
    fun setAdapter(list: List<PlacemarkModel>): PlacemarkAdapter
    {
        val placemarkAdapter = PlacemarkAdapter(list)
        i("${placemarkAdapter.itemCount}")
        return placemarkAdapter
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