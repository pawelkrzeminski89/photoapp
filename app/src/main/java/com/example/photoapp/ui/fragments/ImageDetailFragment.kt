package com.example.photoapp.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.example.photoapp.databinding.FragmentDetailImageBinding

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class ImageDetailFragment : Fragment() {

    private var _binding: FragmentDetailImageBinding? = null

    private val arguments by navArgs<ImageDetailFragmentArgs>()

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailImageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.appImage = arguments.appImage
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}