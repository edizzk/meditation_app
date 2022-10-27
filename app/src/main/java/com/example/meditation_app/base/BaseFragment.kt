package com.example.meditation_app.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<VB: ViewBinding, VM: BaseViewModel>: Fragment() {

    abstract fun getInflater(): (inflater: LayoutInflater) -> VB
    private lateinit var _binding: VB
    protected val baseBinding by lazy { _binding }

    abstract fun getViewModel(): VM
    private lateinit var _viewModel: VM
    protected val baseViewModel by lazy { _viewModel }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?)
    : View? {
        _binding = getInflater()(layoutInflater)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _viewModel = getViewModel()
        setup()
    }

    abstract fun setup()

}