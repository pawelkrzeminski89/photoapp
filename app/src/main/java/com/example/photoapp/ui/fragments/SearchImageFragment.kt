package com.example.photoapp.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.photoapp.adapters.ImageAdapterSelectInterface
import com.example.photoapp.databinding.FragmentSearchImageBinding
import com.example.photoapp.remote.NetworkResult
import com.example.photoapp.remote.PixabayApiResponse
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import androidx.navigation.fragment.findNavController
import com.example.photoapp.R
import com.example.photoapp.adapters.ImagesAdapter
import com.example.photoapp.model.AppImage
import com.example.photoapp.viewmodels.*
import com.google.android.material.dialog.MaterialAlertDialogBuilder

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
@AndroidEntryPoint
class SearchImageFragment : Fragment(), ImageAdapterSelectInterface {

    private var _binding: FragmentSearchImageBinding? = null
    private val binding get() = _binding!!
    private val mainViewModel by viewModels<PixabayViewModel>()
    private lateinit var imageAdapter: ImagesAdapter
    private lateinit var questionImageDetailDialog:AlertDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSearchImageBinding.inflate(inflater, container, false)
        binding.pixabaySearchArgument = mainViewModel.pixabaySearchArguments
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUI()
        initObserver()

    }

    private fun initObserver() {
        mainViewModel.pixabayNetworkResultState.onEach {
            UIUpdate(it)
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        mainViewModel.actionState.onEach {
            UIActionState(it)
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        mainViewModel.uiEffectReceived.onEach {
            UIEffectAction(it)
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun UIEffectAction(uiEffect: UIEffect) {
        when(uiEffect){
            is UIEffectPixabayViewModel.ShowToast ->
                Toast.makeText(context, uiEffect.message, Toast.LENGTH_LONG).show()
        }

    }

    private fun UIActionState(actionState:UIAction) {
        when (actionState) {
            is UIActionPixabayViewModel.DecisionDialog<*> -> {
                questionImageDetailDialog = MaterialAlertDialogBuilder(requireContext())
                    .setTitle(resources.getString(R.string.see_image_detail_dialog_title))
                    .setMessage(resources.getString(R.string.see_image_detail_dialog_message))
                    .setOnCancelListener {
                        mainViewModel.clearActionState()
                    }
                    .setNegativeButton(resources.getString(R.string.see_image_detail_dialog_decline)) { dialog, which ->
                        mainViewModel.clearActionState()
                    }
                    .setPositiveButton(resources.getString(R.string.see_image_detail_dialog_confirm)) { dialog, which ->
                        mainViewModel.clearActionState()
                        navigateToFragmentImageDetail(actionState.data as AppImage)
                    }.show()
            }
        }
    }

    private fun UIUpdate(response: NetworkResult<PixabayApiResponse>) {
        when (response) {

            is NetworkResult.Loading -> {
                binding.progressBar.visibility = View.VISIBLE
                binding.fragmentSearchObjectPhotoButton.visibility = View.INVISIBLE
            }
            is NetworkResult.Success -> {
                response.data?.let {
                    binding.progressBar.visibility = View.GONE
                    binding.fragmentSearchObjectPhotoButton.visibility = View.VISIBLE
                    imageAdapter.update(it.hits.map { pixabayHit -> AppImage.FromPixabayHitResponse(pixabayHit) })
                }
            }
            is NetworkResult.Error -> {
                binding.fragmentSearchObjectPhotoButton.visibility = View.VISIBLE
                binding.progressBar.visibility = View.GONE
            }
            is NetworkResult.EmptyData -> {
               //donothink
            }
        }
    }

    private fun initUI(){
        imageAdapter = ImagesAdapter(this)
        binding.fragmentSearchObjectPhotoRecyclerviewResult.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = imageAdapter
        }
        binding.fragmentSearchObjectPhotoButton.setOnClickListener {
            mainViewModel.fetchPixabaySearchApi()
        }
    }

    override fun onImagAdapterClick(appImage: AppImage) {
        mainViewModel.questionAboutShowDetail(appImage)
    }


    private fun navigateToFragmentImageDetail(appImage: AppImage) {
        val action =
            SearchImageFragmentDirections.actionSearchFragmentToDetailFragment()
        action.appImage = appImage
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        if(this::questionImageDetailDialog.isInitialized && questionImageDetailDialog.isShowing){
            questionImageDetailDialog.dismiss()
        }
        super.onDestroyView()
        _binding = null


    }


}