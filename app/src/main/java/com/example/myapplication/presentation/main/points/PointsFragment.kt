package com.example.myapplication.presentation.main.points

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.myapplication.databinding.FragmentMainBinding
import dagger.hilt.android.AndroidEntryPoint
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import javax.inject.Inject
import javax.inject.Provider

@AndroidEntryPoint
class PointsFragment : MvpAppCompatFragment(), PointsView {

    companion object {

        fun newInstance(): PointsFragment {
            return PointsFragment()
        }
    }

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding ?: throw IllegalStateException("Binding is null")

    @Inject
    lateinit var presenterProvider: Provider<PointsPresenter>

    @InjectPresenter
    lateinit var presenter: PointsPresenter

    @ProvidePresenter
    fun providePresenter(): PointsPresenter {
        return presenterProvider.get()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupClickListeners()
    }

    private fun setupClickListeners() {
        binding.fetchButton.setOnClickListener {
            val pointsCount = binding.pointsInput.text.toString().toIntOrNull()
            binding.pointsInputLayout.error = null
            presenter.onFetchPointsClick(pointsCount)
        }
    }

    override fun showLoading() {
        binding.progressBar.visibility = View.VISIBLE
        binding.fetchButton.isEnabled = false
        binding.pointsInput.isEnabled = false
    }

    override fun hideLoading() {
        binding.progressBar.visibility = View.GONE
        binding.fetchButton.isEnabled = true
        binding.pointsInput.isEnabled = true
    }

    override fun showInputError(message: String) {
        binding.pointsInputLayout.error = message
    }

    override fun showError(message: String) {
        Toast.makeText(
            requireContext(),
            message,
            Toast.LENGTH_SHORT
        ).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
} 