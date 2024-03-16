package com.example.a6_dogapp.view

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import com.example.a6_dogapp.R
import com.example.a6_dogapp.databinding.FragmentDetailsBinding
import com.example.a6_dogapp.model.DogBreed
import com.squareup.picasso.Picasso
import java.io.Serializable

class DetailsFragment : Fragment() {
    private lateinit var dogBreed: DogBreed
    private lateinit var binding: FragmentDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { args ->
            dogBreed = args.getSerializable("dogBreed") as? DogBreed ?: DogBreed(0, "", "", "", "")
        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_details, null, false)
        val viewRoot: View = binding.root
        binding.dog = dogBreed
        Picasso.get().load(dogBreed.url).into(binding.imgAvatar)
        return viewRoot
    }
}

